
package controllers.author;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConferenceService;
import services.PaperService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Submission;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private PaperService		paperService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Author a = this.authorService.findByPrincipal();
		final Collection<Submission> submissions = this.submissionService.findAllByAuthorId(a.getId());
		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("requestURI", "/submission/author/list.do");

		return result;
		//TODO: AÑADIR UNA LISTA QUE TENGA LAS SUBMISSION A LAS QUE PUEDO APLICAR, Y COMPROBAR EN LA VISTA QUE LA COLECCION CONTIENE LA SUBMISSION DE LA COLUMNA.
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView res;

		try {
			final Author author = this.authorService.findByPrincipal();
			final Conference con = this.conferenceService.findOne(conferenceId);
			Assert.isTrue(con.isFinalMode());
			Assert.isTrue(con.getSubmissionDeadline().after(new Date()), "submission elapsed");

			final Collection<Submission> submissions = this.submissionService.findAllByConferenceId(conferenceId);
			for (final Submission s : submissions)
				if (s.getAuthor().getId() == author.getId())
					throw new IllegalArgumentException("duplicated submission");
			final Submission submission = this.submissionService.create();
			submission.setAuthor(author);
			submission.setConference(con);

			final String ticker = this.submissionService.generateTicker(author);
			submission.setTicker(ticker);
			res = this.createEditModelAndView(submission);

		} catch (final IllegalArgumentException oops) {
			final ModelAndView result;
			final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAllForthcomingConferences());
			result = new ModelAndView("conference/list");
			result.addObject("conferences", conferences);
			result.addObject("requestURI", "/conference/listForthcoming.do");
			result.addObject("actualDate", new Date());
			if (oops.getMessage().equals("duplicated submission"))
				result.addObject("message", "submission.duplicated");
			else if (oops.getMessage().equals("submission elapsed"))
				result.addObject("message", "submission.elapsed");

			return result;
		}

		catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		}
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Submission submission, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(submission, "submission.commit.error");
				return res;
			}
			TimeUnit.SECONDS.sleep(1);
			final Paper newPaper = this.paperService.save(submission.getPaper());
			this.paperService.flush();
			submission.setPaper(newPaper);
			this.submissionService.save(submission);
			res = new ModelAndView("redirect:/submission/author/list.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(submission);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(submission, "submission.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/paper", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@ModelAttribute @Valid final Submission submission, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(submission, "submission.commit.error");
				return res;
			}
			TimeUnit.SECONDS.sleep(1);
			final Paper newPaper = this.paperService.save(submission.getCameraReadyPaper());
			this.paperService.flush();
			submission.setCameraReadyPaper(newPaper);
			this.submissionService.save(submission);
			res = new ModelAndView("redirect:/submission/author/list.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(submission);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(submission, "submission.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		final ModelAndView result;
		final Submission submission = this.submissionService.findOne(submissionId);
		final Author actual = this.authorService.findByPrincipal();
		try {
			Assert.isTrue(actual.getId() == submission.getAuthor().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}

		result = new ModelAndView("submission/show");
		result.addObject("submission", submission);
		return result;
	}

	@RequestMapping(value = "/paper", method = RequestMethod.GET)
	public ModelAndView cameraReadyPaper(@RequestParam final int submissionId) {
		ModelAndView result;
		final Submission submission = this.submissionService.findOne(submissionId);
		try {
			Assert.isTrue(submission.getConference().getCameraReadyDeadline().after(new Date()), "deadline elapsed");
			Assert.isTrue(submission.getCameraReadyPaper() == null);
			Assert.isTrue(!submission.getReviewer().isEmpty());
			result = new ModelAndView("submission/paper");
			result.addObject("submission", submission);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("deadline elapsed")) {
				final Author a = this.authorService.findByPrincipal();
				final Collection<Submission> submissions = this.submissionService.findAllByAuthorId(a.getId());
				result = new ModelAndView("submission/list");
				result.addObject("submissions", submissions);
				result.addObject("requestURI", "/submission/author/list.do");
				result.addObject("message", "submission.deadline.elapsed");
			} else
				result = new ModelAndView("redirect:/list.do");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Submission submission) {
		ModelAndView result;

		result = this.createEditModelAndView(submission, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Submission submission, final String message) {
		ModelAndView result;

		result = new ModelAndView("submission/edit");
		result.addObject("submission", submission);
		result.addObject("message", message);

		return result;
	}
}
