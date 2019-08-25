
package controllers.administrator;

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
import services.SectionService;
import services.SubmissionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/administrator")
public class TutorialAdministratorController extends AbstractController {

	@Autowired
	private TutorialService			tutorialService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private SectionService			sectionService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Tutorial tutorial = this.tutorialService.create(conferenceId);
		result = this.createEditModelAndView(tutorial);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final int tutorialId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		result = this.createEditModelAndView(tutorial);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		result = new ModelAndView("tutorial/show");
		result.addObject("tutorial", tutorial);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int tutorialId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Collection<Section> sections = this.sectionService.findAllByTutorial(tutorialId);
		for (final Section s : sections)
			this.sectionService.delete(s);
		this.tutorialService.delete(tutorial);

		result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + tutorial.getConference().getId());
		result.addObject("tutorial", tutorial);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(tutorial, "tutorial.commit.error");
				return res;
			}
			final Conference c = tutorial.getConference();
			Assert.isTrue(!c.getStartDate().after(tutorial.getStartDate()), "wrong start");
			Assert.isTrue(!c.getEndDate().before(tutorial.getEndDate()), "wrong end");
			Assert.isTrue(tutorial.getStartDate().before(tutorial.getEndDate()), "wrong dates");
			Assert.notNull(tutorial.getSpeakers(), "invalid speakers");
			Assert.isTrue(!tutorial.getSpeakers().isEmpty(), "invalid speakers");
			this.tutorialService.save(tutorial);
			res = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + tutorial.getConference().getId());
			return res;
		} catch (final Throwable oops) {
			if (oops.getMessage() == "invalid speakers")
				res = this.createEditModelAndView(tutorial, "activity.speakers.error");
			else if (oops.getMessage() == "wrong dates")
				res = this.createEditModelAndView(tutorial, "activity.dates.error");
			else if (oops.getMessage() == "wrong start")
				res = this.createEditModelAndView(tutorial, "activity.start.error");
			else if (oops.getMessage() == "wrong end")
				res = this.createEditModelAndView(tutorial, "activity.end.error");

			else
				res = this.createEditModelAndView(tutorial, "tutorial.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String message) {
		ModelAndView result;

		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", message);

		return result;
	}

}
