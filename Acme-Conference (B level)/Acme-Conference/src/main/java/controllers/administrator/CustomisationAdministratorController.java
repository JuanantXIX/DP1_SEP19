
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Customisation;

@Controller
@RequestMapping("/customisation/administrator")
public class CustomisationAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomisationService	customisationService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2() {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Customisation customisation = this.customisationService.getCustomisation();
		result = this.createEditModelAndView(customisation);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Customisation customisation, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(customisation, "customisation.commit.error");
				return res;
			}
			this.customisationService.save(customisation);
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(customisation, "customisation.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Customisation customisation) {
		ModelAndView result;

		result = this.createEditModelAndView(customisation, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Customisation customisation, final String message) {
		ModelAndView result;

		result = new ModelAndView("customisation/edit");
		result.addObject("customisation", customisation);
		result.addObject("message", message);

		return result;
	}

}
