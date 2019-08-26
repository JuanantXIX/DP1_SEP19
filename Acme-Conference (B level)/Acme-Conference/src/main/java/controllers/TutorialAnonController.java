
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TutorialService;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialAnonController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		final ModelAndView result;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		result = new ModelAndView("tutorial/show");
		result.addObject("tutorial", tutorial);
		return result;
	}

}
