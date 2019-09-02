
package controllers.author;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ReportService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Report;
import domain.Submission;

@Controller
@RequestMapping("/report/author")
public class ReportAuthorController extends AbstractController {

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ReportService		reportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int submissionId) {
		final ModelAndView result;
		final Submission submission = this.submissionService.findOne(submissionId);
		final Author actual = this.authorService.findByPrincipal();
		try {
			Assert.isTrue(!(submission.getStatus().equals("UNDER-REVIEW")));
			Assert.isTrue(submission.isStatusVisible() == true);
			Assert.isTrue(submission.getAuthor().getId() == actual.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		final Collection<Report> reports = this.reportService.findAllBySubmissionId(submissionId);
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int reportId) {
		final ModelAndView result;
		final Report report = this.reportService.findOne(reportId);

		final Author actual = this.authorService.findByPrincipal();

		try {
			Assert.isTrue(actual.getId() == report.getSubmission().getAuthor().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		result = new ModelAndView("report/show");
		result.addObject("report", report);
		return result;
	}

}
