
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CategoryService			categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "/category/administrator/list.do");
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit() {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Category category = this.categoryService.create();
		result = this.createEditModelAndView(category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final int categoryId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Category category = this.categoryService.findOne(categoryId);
		try {
			this.categoryService.checkRootCategory(category);
		} catch (final Throwable oops) {
			final String message = oops.getMessage();
			if (message.contains("Editing root category")) {
				final Collection<Category> categories = this.categoryService.findAll();
				result = new ModelAndView("category/list");
				result.addObject("categories", categories);
				result.addObject("message", "category.edit.error.root");
				result.addObject("requestURI", "/category/administrator/list.do");

				return result;
			}
		}
		result = this.createEditModelAndView(category);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int categoryId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Category category = this.categoryService.findOne(categoryId);

		result = new ModelAndView("category/show");
		result.addObject("category", category);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Category category = this.categoryService.findOne(categoryId);
		try {
			this.categoryService.checkRootCategory(category);
			this.categoryService.prepareCategoryForDelete(category);
			this.categoryService.delete(category);

		} catch (final Throwable oops) {
			final String message = oops.getMessage();
			if (message.contains("Editing root category")) {
				final Collection<Category> categories = this.categoryService.findAll();
				result = new ModelAndView("category/list");
				result.addObject("categories", categories);
				result.addObject("message", "category.delete.error.root");
				result.addObject("requestURI", "/category/administrator/list.do");

				return result;
			} else if (message.contains("ConstraintViolationException")) {
				final Collection<Category> categories = this.categoryService.findAll();
				result = new ModelAndView("category/list");
				result.addObject("categories", categories);
				result.addObject("message", "category.delete.error.messages");
				result.addObject("requestURI", "/category/administrator/list.do");

				return result;
			}
		}

		result = new ModelAndView("redirect:/category/administrator/list.do");
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Category category, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			if (category.getChildren() == null)
				category.setChildren(new ArrayList<Category>());
			if (category.getParents() == null)
				category.setParents(new ArrayList<Category>());
			Assert.isTrue(category.getTitle().size() == 2, "Wrong size");

			if (binding.hasErrors()) {
				res = this.createEditModelAndView(category, "category.commit.error");
				return res;
			}
			if (category.getId() != 0) {

				final Category dbCategory = this.categoryService.findOne(category.getId());
				this.categoryService.fixLogicErrorsOnCategories2(category, dbCategory);

			}
			this.categoryService.checkRootCategory(category);

			final Category savedCategory = this.categoryService.save(category);
			if (category.getId() == 0)
				this.categoryService.fixLogicErrorsOnCategories(savedCategory);
			res = new ModelAndView("redirect:/category/administrator/list.do");
			return res;
		} catch (final Throwable oops) {
			if (oops.getMessage().contains("Editing root category")) {
				final Collection<Category> categories = this.categoryService.findAll();
				res = new ModelAndView("category/list");
				res.addObject("categories", categories);
				res.addObject("message", "category.edit.error.root");
				res.addObject("requestURI", "/category/administrator/list.do");

				return res;
			} else if (oops.getMessage() == "Wrong size")
				res = this.createEditModelAndView(category, "category.size.error");
			else
				res = this.createEditModelAndView(category, "category.commit.error");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		final Collection<Category> parents = this.categoryService.findAll();
		final Collection<Category> children = this.categoryService.findAll();

		if (category.getId() != 0) {
			parents.remove(category);
			children.remove(category);
		}
		result.addObject("category", category);
		result.addObject("parents", parents);
		result.addObject("children", children);
		result.addObject("message", message);

		return result;
	}

}
