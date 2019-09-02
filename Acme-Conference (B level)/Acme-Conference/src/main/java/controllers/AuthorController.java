
package controllers;

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
import services.AuthorService;
import domain.Actor;
import domain.Author;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

	@Autowired
	private AuthorService	authorService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final Author author = this.authorService.create();
		res = this.createEditModelAndView(author);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Author author, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(author, "author.commit.error");
				return res;
			}
			if (!author.getEmail().isEmpty() || author.getEmail() != "")
				Assert.isTrue(author.getEmail().matches("^[A-z0-9]+@[A-z0-9.]+$") || author.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>$"), "Wrong email");

			final Collection<Actor> allAuthors = this.actorService.findAll();

			for (final Actor a : allAuthors) {
				final UserAccount ua = a.getUserAccount();
				if (ua.getUsername().toUpperCase().equals(author.getUserAccount().getUsername().toUpperCase()))
					throw new IllegalArgumentException("name taken");
			}

			this.authorService.save(author);
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(author);
		} catch (final IllegalArgumentException oops) {
			res = this.createEditModelAndView(author, "actor.name.taken");

		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				res = this.createEditModelAndView(author, "author.email.error");
			else
				res = this.createEditModelAndView(author, "author.commit.error");
		}

		return res;
	}
	protected ModelAndView createEditModelAndView(final Author author) {
		ModelAndView result;

		result = this.createEditModelAndView(author, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Author author, final String message) {
		ModelAndView result;

		result = new ModelAndView("author/register");
		result.addObject("author", author);
		result.addObject("message", message);

		return result;
	}
}
