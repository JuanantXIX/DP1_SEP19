
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

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
import services.PresentationService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Conference;
import domain.Paper;
import domain.Presentation;
import domain.Submission;

@Controller
@RequestMapping("/presentation/administrator")
public class PresentationAdministratorController extends AbstractController {

	@Autowired
	private PresentationService		presentationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubmissionService		submissionService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Presentation presentation = this.presentationService.create(conferenceId);

		final List<Submission> submissions = this.submissionService.findAllByConferenceId(conferenceId);
		final List<Paper> papers = new ArrayList<Paper>();
		for (final Submission x : submissions)
			if (x.getCameraReadyPaper() != null)
				papers.add(x.getCameraReadyPaper());
		result = this.createEditModelAndView(presentation);
		result.addObject("papers", papers);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final int presentationId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Presentation presentation = this.presentationService.findOne(presentationId);
		result = this.createEditModelAndView(presentation);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int presentationId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Presentation presentation = this.presentationService.findOne(presentationId);

		result = new ModelAndView("presentation/show");
		result.addObject("presentation", presentation);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int presentationId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Presentation presentation = this.presentationService.findOne(presentationId);
		this.presentationService.delete(presentation);

		result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + presentation.getConference().getId());
		result.addObject("presentation", presentation);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Presentation presentation, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(presentation, "presentation.commit.error");
				return res;
			}
			final Conference c = presentation.getConference();
			Assert.isTrue(presentation.getStartDate().before(presentation.getEndDate()), "wrong dates");
			Assert.isTrue(!c.getStartDate().after(presentation.getStartDate()), "wrong start");
			Assert.isTrue(!c.getEndDate().before(presentation.getEndDate()), "wrong end");
			Assert.notNull(presentation.getSpeakers(), "invalid speakers");
			Assert.isTrue(!presentation.getSpeakers().isEmpty(), "invalid speakers");
			Assert.notNull(presentation.getPaper());

			this.presentationService.save(presentation);
			res = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + presentation.getConference().getId());
			return res;
		} catch (final Throwable oops) {
			if (oops.getMessage() == "invalid speakers")
				res = this.createEditModelAndView(presentation, "activity.speakers.error");
			else if (oops.getMessage() == "wrong dates")
				res = this.createEditModelAndView(presentation, "activity.dates.error");
			else if (oops.getMessage() == "wrong start")
				res = this.createEditModelAndView(presentation, "activity.start.error");
			else if (oops.getMessage() == "wrong end")
				res = this.createEditModelAndView(presentation, "activity.end.error");
			else
				res = this.createEditModelAndView(presentation, "presentation.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Presentation presentation) {
		ModelAndView result;

		result = this.createEditModelAndView(presentation, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Presentation presentation, final String message) {
		ModelAndView result;

		result = new ModelAndView("presentation/edit");
		result.addObject("presentation", presentation);
		result.addObject("message", message);
		final List<Submission> submissions = this.submissionService.findAllByConferenceId(presentation.getConference().getId());
		final List<Paper> papers = new ArrayList<Paper>();
		for (final Submission x : submissions)
			if (x.getCameraReadyPaper() != null)
				papers.add(x.getCameraReadyPaper());
		result.addObject("papers", papers);

		return result;
	}

}
