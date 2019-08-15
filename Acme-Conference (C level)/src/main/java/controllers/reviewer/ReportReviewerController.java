
package controllers.reviewer;

import java.util.Collection;
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

import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/report/reviewer")
public class ReportReviewerController extends AbstractController {

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ReportService		reportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Reviewer r = this.reviewerService.findByPrincipal();
		final Collection<Report> reports = this.reportService.findAllByReviewerId(r);
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int submissionId) {
		ModelAndView res;
		final Reviewer reviewer = this.reviewerService.findByPrincipal();

		try {
			final Submission submission = this.submissionService.findOne(submissionId);
			Assert.isTrue(submission.getStatus().equals("UNDER-REVIEW"), "Submission already evaluated");
			final Collection<Report> allReports = this.reportService.findAllByReviewerId(reviewer);
			for (final Report r : allReports)
				Assert.isTrue(r.getSubmission().getId() != submissionId, "Submission already evaluated");
			final Report report = this.reportService.create();
			report.setReviewer(reviewer);
			report.setSubmission(submission);
			res = this.createEditModelAndView(report);
			res.addObject("submission", submission);

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("Submission already evaluated")) {
				final ModelAndView result;
				final Collection<Submission> submissions = this.submissionService.findAllByReviewerId(reviewer.getId());
				result = new ModelAndView("submission/list");
				result.addObject("submissions", submissions);
				result.addObject("message", "submission.report.ev");
				return result;
			}
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		}
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Report report, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(report, "report.commit.error");
				return res;
			}
			TimeUnit.SECONDS.sleep(1);
			this.reportService.save(report);
			res = new ModelAndView("redirect:/report/reviewer/list.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(report);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(report, "report.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int reportId) {
		final ModelAndView result;
		final Report report = this.reportService.findOne(reportId);

		final Reviewer actual = this.reviewerService.findByPrincipal();

		try {
			Assert.isTrue(actual.getId() == report.getReviewer().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		result = new ModelAndView("report/show");
		result.addObject("report", report);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Report report, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}
}
