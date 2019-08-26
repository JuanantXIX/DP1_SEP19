
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PresentationService;
import domain.Presentation;

@Controller
@RequestMapping("/presentation")
public class PresentationAnonController extends AbstractController {

	@Autowired
	private PresentationService	presentationService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int presentationId) {
		final ModelAndView result;
		final Presentation presentation = this.presentationService.findOne(presentationId);

		result = new ModelAndView("presentation/show");
		result.addObject("presentation", presentation);
		return result;
	}

}
