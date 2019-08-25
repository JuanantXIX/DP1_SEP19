
package controllers.reviewer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/reviewer")
public class SubmissionReviewerController extends AbstractController {

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
		final Collection<Submission> submissions = this.submissionService.findAllByReviewerId(r.getId());
		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("requestURI", "/submission/reviewer/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		final ModelAndView result;
		final Submission submission = this.submissionService.findOne(submissionId);
		final Reviewer actual = this.reviewerService.findByPrincipal();
		try {
			Assert.isTrue(submission.getReviewer().contains(actual));
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}

		result = new ModelAndView("submission/show");
		result.addObject("submission", submission);
		return result;
	}
}
