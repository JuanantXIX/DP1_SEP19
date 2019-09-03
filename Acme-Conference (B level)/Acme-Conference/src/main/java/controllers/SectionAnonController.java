
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionAnonController extends AbstractController {

	@Autowired
	private SectionService	sectionService;

	@Autowired
	private TutorialService	tutorialService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		final ModelAndView result;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		final List<Section> sections = this.sectionService.findAllByTutorial(tutorialId);
		result = new ModelAndView("section/list");
		result.addObject("sections", sections);
		result.addObject("tutorialId", tutorialId);
		result.addObject("tutorial", tutorial);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int sectionId) {
		final ModelAndView result;
		final Section section = this.sectionService.findOne(sectionId);

		result = new ModelAndView("section/show");
		result.addObject("section", section);
		return result;
	}

}
