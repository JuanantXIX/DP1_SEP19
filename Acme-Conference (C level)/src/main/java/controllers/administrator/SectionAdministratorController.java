
package controllers.administrator;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Section;

@Controller
@RequestMapping("/section/administrator")
public class SectionAdministratorController extends AbstractController {

	@Autowired
	private TutorialService			tutorialService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SectionService			sectionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		final ModelAndView result;
		final List<Section> sections = this.sectionService.findAllByTutorial(tutorialId);
		result = new ModelAndView("section/list");
		result.addObject("sections", sections);
		result.addObject("tutorialId", tutorialId);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Section section = this.sectionService.create(tutorialId);
		result = this.createEditModelAndView(section);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final int sectionId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Section section = this.sectionService.findOne(sectionId);
		result = this.createEditModelAndView(section);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int sectionId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Section section = this.sectionService.findOne(sectionId);

		result = new ModelAndView("section/show");
		result.addObject("section", section);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sectionId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Section section = this.sectionService.findOne(sectionId);
		this.sectionService.delete(section);

		result = new ModelAndView("redirect:/section/administrator/list.do?tutorialId=" + section.getTutorial().getId());
		result.addObject("tutorial", section);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Section section, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(section, "section.commit.error");
				return res;
			}
			this.sectionService.save(section);
			res = new ModelAndView("redirect:/section/administrator/list.do?tutorialId=" + section.getTutorial().getId());
			return res;
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(section, "section.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Section section, final String message) {
		ModelAndView result;

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("message", message);

		return result;
	}

}
