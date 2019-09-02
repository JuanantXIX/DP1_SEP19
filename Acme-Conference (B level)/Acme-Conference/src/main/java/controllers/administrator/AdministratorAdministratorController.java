
package controllers.administrator;

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
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final Administrator administrator = this.administratorService.create();
		res = this.createEditModelAndView(administrator);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(administrator, "administrator.commit.error");
				return res;
			}
			if (!administrator.getEmail().isEmpty() || administrator.getEmail() != "")
				Assert.isTrue(administrator.getEmail().matches("^[A-z0-9]+@$") || administrator.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@>$"), "Wrong email");

			final Collection<Actor> allAuthors = this.actorService.findAll();

			for (final Actor a : allAuthors) {
				final UserAccount ua = a.getUserAccount();
				if (ua.getUsername().toUpperCase().equals(administrator.getUserAccount().getUsername().toUpperCase()))
					throw new IllegalArgumentException("name taken");
			}

			this.administratorService.save(administrator);
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(administrator);
		} catch (final IllegalArgumentException oops) {
			res = this.createEditModelAndView(administrator, "actor.name.taken");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				res = this.createEditModelAndView(administrator, "administrator.email.error");
			else
				res = this.createEditModelAndView(administrator, "administrator.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;

		try {
			final Administrator administrator = this.administratorService.findByPrincipal();
			res = this.createEditModelAndView2(administrator);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(@ModelAttribute @Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView2(administrator, "administrator.commit.error");
				return res;
			}
			if (!administrator.getEmail().isEmpty() || administrator.getEmail() != "")
				Assert.isTrue(administrator.getEmail().matches("^[A-z0-9]+@$") || administrator.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@>$"), "Wrong email");
			TimeUnit.SECONDS.sleep(1);
			final Collection<Actor> allAuthors = this.actorService.findAll();

			if (administrator.getId() == 0)
				for (final Actor a : allAuthors) {
					final UserAccount ua = a.getUserAccount();
					if (ua.getUsername().toUpperCase().equals(administrator.getUserAccount().getUsername().toUpperCase()))
						throw new IllegalArgumentException("name taken");
				}

			this.administratorService.save(administrator);
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(administrator);
		} catch (final IllegalArgumentException oops) {
			res = this.createEditModelAndView(administrator, "actor.name.taken");

		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				res = this.createEditModelAndView2(administrator, "administrator.email.error");
			else
				res = this.createEditModelAndView2(administrator, "administrator.edit.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/register");
		result.addObject("administrator", administrator);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView2(administrator, null);

		return result;

	}

	protected ModelAndView createEditModelAndView2(final Administrator administrator, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", message);

		return result;
	}
}
