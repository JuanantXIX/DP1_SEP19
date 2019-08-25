
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
import services.CustomisationService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private RegistrationService		registrationService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private CustomisationService	customisationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Author a = this.authorService.findByPrincipal();
		final Collection<Registration> regs = this.registrationService.findAllByAuthorId(a.getId());
		result = new ModelAndView("registration/list");
		result.addObject("requestURI", "/registration/author/list.do");
		result.addObject("registrations", regs);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView res;

		try {
			final Author author = this.authorService.findByPrincipal();
			final Conference con = this.conferenceService.findOne(conferenceId);
			Assert.isTrue(con.isFinalMode());
			Assert.isTrue(con.getStartDate().after(new Date()), "register elapsed");

			final Collection<Registration> registrations = this.registrationService.findAllByAuthorId(author.getId());
			for (final Registration s : registrations)
				if (s.getConference().getId() == conferenceId)
					throw new IllegalArgumentException("duplicated conference");

			final Registration reg = this.registrationService.create(conferenceId);
			reg.setAuthor(author);
			reg.setConference(con);
			res = this.createEditModelAndView(reg);

		} catch (final IllegalArgumentException oops) {
			final ModelAndView result;
			final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAllForthcomingConferences());
			result = new ModelAndView("conference/list");
			result.addObject("conferences", conferences);
			result.addObject("requestURI", "/conference/listForthcoming.do");
			result.addObject("actualDate", new Date());
			if (oops.getMessage().equals("register elapsed"))
				result.addObject("message", "registration.elapsed");
			else
				result.addObject("message", "registration.duplicated");
			return result;

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Registration registration, final BindingResult binding) {
		ModelAndView res;

		try {
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(registration, "author.commit.error");
				return res;
			}
			TimeUnit.SECONDS.sleep(1);
			Assert.isTrue(registration.getExpirationYear() >= (new Date().getYear() - 100));
			if (registration.getExpirationYear() == (new Date().getYear() - 100))
				Assert.isTrue(registration.getExpirationMonth() > new Date().getMonth() - 1);
			this.registrationService.save(registration);
			res = new ModelAndView("redirect:/registration/author/list.do");
			return res;
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(registration);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(registration, "registration.edit.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int registrationId) {
		final ModelAndView result;
		final Registration registration = this.registrationService.findOne(registrationId);

		final Author actual = this.authorService.findByPrincipal();

		try {
			Assert.isTrue(actual.getId() == registration.getAuthor().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		result = new ModelAndView("registration/show");
		result.addObject("registration", registration);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Registration registration) {
		ModelAndView result;

		result = this.createEditModelAndView(registration, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Registration registration, final String message) {
		ModelAndView result;

		result = new ModelAndView("registration/edit");
		result.addObject("registration", registration);
		result.addObject("message", message);
		final Collection<String> creditCardMakes = this.customisationService.getCustomisation().getCreditCardMakes();
		result.addObject("makes", creditCardMakes);

		return result;
	}
}
