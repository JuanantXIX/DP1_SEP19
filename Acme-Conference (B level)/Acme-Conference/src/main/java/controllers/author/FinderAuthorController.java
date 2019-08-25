
package controllers.author;

import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.CategoryService;
import services.FinderService;
import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Finder;

@Controller
@RequestMapping("/finder/author")
public class FinderAuthorController extends AbstractController {

	@Autowired
	private AuthorService	authorService;

	@Autowired
	private FinderService	finderService;

	@Autowired
	private CategoryService	categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Author a = this.authorService.findByPrincipal();
		final Finder finder = a.getFinder();

		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Finder finder, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(finder, "finder.dates.error");

				return res;
			}
			final List<Conference> conferencesToSave = (List<Conference>) this.finderService.searchConferences(finder);
			finder.setConferences(conferencesToSave);
			final Finder redirectWithThisFinder = this.finderService.save(finder);
			this.finderService.flush();
			res = this.createEditModelAndView(redirectWithThisFinder); //POSIBILIDAD: REDIRECCIONAR A UN LIST DE LAS CONFERENCIAS
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(finder, "finder.commit.error");

		} catch (final Throwable oops) {
			res = this.createEditModelAndView(finder, "finder.commit.error");

		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;
		result = new ModelAndView("finder/dataform");
		result.addObject("message", message);
		result.addObject("requestURI", "/finder/author/list.do");
		result.addObject("finder", finder);
		result.addObject("conferences", finder.getConferences());
		result.addObject("categories", this.categoryService.findAll());

		return result;
	}

}
