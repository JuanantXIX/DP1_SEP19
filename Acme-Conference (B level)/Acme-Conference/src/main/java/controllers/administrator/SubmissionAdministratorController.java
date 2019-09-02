
package controllers.administrator;

import java.util.Collection;
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

import services.AdministratorService;
import services.MessageService;
import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private ReportService			reportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Submission> submissions = this.submissionService.findAllGroupedByStatus();
		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("requestURI", "/submission/administrator/list.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int submissionId) {
		ModelAndView res;

		try {
			final Submission submission = this.submissionService.findOne(submissionId);
			Assert.isTrue(submission.getConference().isFinalMode());
			final String status = submission.getStatus();
			Assert.isTrue(status.equals("UNDER-REVIEW"));
			res = this.createEditModelAndView(submission);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Submission submission, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(submission, "submission.commit.error");
				return res;
			}
			TimeUnit.MILLISECONDS.sleep(1);
			this.submissionService.save(submission);
			res = new ModelAndView("redirect:/submission/administrator/list.do");

			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(submission);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(submission, "submission.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "fill")
	public ModelAndView fill(@ModelAttribute @Valid final Submission submission, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(submission, "submission.commit.error");
				return res;
			}
			TimeUnit.MILLISECONDS.sleep(1);
			final List<Reviewer> reviewers = this.reviewerService.allReviewersByKeyword(submission.getConference().getTitle(), submission.getConference().getSummary());
			submission.setReviewer(reviewers);
			this.submissionService.save(submission);
			res = new ModelAndView("redirect:/submission/administrator/list.do");

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

		result = new ModelAndView("submission/show");
		result.addObject("submission", submission);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Submission submission) {
		ModelAndView result;

		result = this.createEditModelAndView(submission, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Submission submission, final String message) {
		ModelAndView result;

		result = new ModelAndView("submission/editReviewer");
		final Collection<Reviewer> reviewer = this.reviewerService.findAll();
		result.addObject("reviewer", reviewer);
		result.addObject("submission", submission);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/evaluate", method = RequestMethod.GET)
	public ModelAndView evaluateSubmissions() {
		final ModelAndView result;

		final Administrator actual = this.administratorService.findByPrincipal();

		final List<Submission> allSubs = this.submissionService.findAllUnderReview();

		for (final Submission s : allSubs)
			if (!s.getReviewer().isEmpty()) {
				final List<Report> reportsOfSubmission = this.reportService.findAllBySubmissionId(s.getId());
				if (reportsOfSubmission != null && !reportsOfSubmission.isEmpty()) {
					int positive = 0;
					int negative = 0;
					int neutral = 0;
					String decision = "";
					for (final Report r : reportsOfSubmission) {
						if (r.getDecision().equals("ACCEPT"))
							positive++;
						if (r.getDecision().equals("BORDER-LINE"))
							neutral++;
						if (r.getDecision().equals("REJECT"))
							negative++;
					}
					if (positive > negative) {
						decision = "ACCEPTED";
						s.setStatus(decision);
						this.submissionService.save(s);
					} else if (negative > positive) {
						decision = "REJECTED";
						s.setStatus(decision);
						this.submissionService.save(s);
					} else if ((positive + neutral) >= negative) {
						decision = "ACCEPTED";
						s.setStatus(decision);
						this.submissionService.save(s);
					} else {
						decision = "REJECTED";
						s.setStatus(decision);
						this.submissionService.save(s);
					}

				}
			}
		final Collection<Submission> submissions = this.submissionService.findAllGroupedByStatus();
		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("message", "submission.scored");
		result.addObject("requestURI", "/submission/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public ModelAndView notifyAuthors() {
		final ModelAndView result;

		final Administrator actual = this.administratorService.findByPrincipal();

		final List<Submission> allSubs = this.submissionService.findAllInvisible();

		for (final Submission s : allSubs) {
			if (s.getStatus().equals("ACCEPTED")) {
				s.setStatusVisible(true);
				final Submission s2 = this.submissionService.save(s);
				this.messageService.sendAcceptedMessage(s2, actual);
			}
			if (s.getStatus().equals("REJECTED")) {
				s.setStatusVisible(true);
				final Submission s2 = this.submissionService.save(s);
				this.messageService.sendAcceptedMessage(s2, actual);
			}
		}
		final Collection<Submission> submissions = this.submissionService.findAllGroupedByStatus();
		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("message", "submission.notified");
		result.addObject("requestURI", "/submission/administrator/list.do");

		return result;
	}

}
