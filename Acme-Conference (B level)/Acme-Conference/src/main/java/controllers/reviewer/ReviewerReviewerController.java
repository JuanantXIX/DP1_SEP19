
package controllers.reviewer;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.ReviewerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Reviewer;

@Controller
@RequestMapping("/reviewer/reviewer")
public class ReviewerReviewerController extends AbstractController {

	@Autowired
	private ReviewerService	reviewerService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;

		final Reviewer reviewer = this.reviewerService.findByPrincipal();
		res = this.createEditModelAndView(reviewer);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Reviewer reviewer, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(reviewer, "reviewer.edit.commit.error");
				return res;
			}
			if (!reviewer.getEmail().isEmpty() || reviewer.getEmail() != "")
				Assert.isTrue(reviewer.getEmail().matches("^[A-z0-9]+@[A-z0-9.]+$") || reviewer.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>$"), "Wrong email");

			final Collection<Actor> allAuthors = this.actorService.findAll();

			if (reviewer.getId() == 0)
				for (final Actor a : allAuthors) {
					final UserAccount ua = a.getUserAccount();
					if (ua.getUsername().toUpperCase().equals(reviewer.getUserAccount().getUsername().toUpperCase()))
						throw new IllegalArgumentException("name taken");
				}
			this.reviewerService.save(reviewer);
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(reviewer);
		} catch (final IllegalArgumentException oops) {
			res = this.createEditModelAndView(reviewer, "actor.name.taken");

		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				res = this.createEditModelAndView(reviewer, "reviewer.email.error");
			else
				res = this.createEditModelAndView(reviewer, "reviewer.edit.commit.error");
		}

		return res;
	}
	protected ModelAndView createEditModelAndView(final Reviewer reviewer) {
		ModelAndView result;

		result = this.createEditModelAndView(reviewer, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer, final String message) {
		ModelAndView result;

		result = new ModelAndView("reviewer/edit");
		result.addObject("reviewer", reviewer);
		result.addObject("message", message);

		return result;
	}
}
