
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.MessageService;
import services.TopicService;
import domain.Actor;
import domain.Message;
import domain.Topic;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private TopicService	topicService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res;

		final Actor actual = this.actorService.findByPrincipal();

		final List<Message> sentByActual = this.messageService.findBySender(actual);
		final List<Message> receivedByActual = this.messageService.findByRecipient(actual);

		res = new ModelAndView("message/list");
		res.addObject("sent", sentByActual);
		res.addObject("received", receivedByActual);
		res.addObject("requestURI", "/message/list.do");

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int messageId) {
		final ModelAndView res;
		final Message message = this.messageService.findOne(messageId);

		try {

			final Actor actual = this.actorService.findByPrincipal();
			Assert.isTrue(message.getSender().getId() == actual.getId() || message.getRecipient().getId() == actual.getId());
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:list.do");
			return res;
		}
		res = new ModelAndView("message/show");
		res.addObject("mes", message);
		return res;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		final ModelAndView res;
		final Message message = this.messageService.create();
		res = this.createEditModelAndView(message);
		return res;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		final Actor a = this.actorService.findByPrincipal();
		message.setSender(a);
		message.setMoment(new Date());

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {

				final Message saved = this.messageService.save(message);
				result = new ModelAndView("redirect:list.do");
				if (saved == null)
					throw new Exception();
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "sendToAll")
	public ModelAndView send2(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		final Actor a = this.actorService.findByPrincipal();
		message.setSender(a);
		message.setMoment(new Date());

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				final Authority adminauthority = new Authority();
				adminauthority.setAuthority(Authority.ADMIN);
				final Collection<Actor> allActors = this.actorService.findAll();
				for (final Actor actor : allActors)
					if (!actor.getUserAccount().getAuthorities().contains(adminauthority)) {
						message.setRecipient(actor);
						this.messageService.save(message);
					}

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "sendToAllIncAdmins")
	public ModelAndView send3(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;
		final Actor a = this.actorService.findByPrincipal();
		message.setSender(a);
		message.setMoment(new Date());

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {

				final Collection<Actor> allActors = this.actorService.findAll();
				for (final Actor actor : allActors) {
					message.setRecipient(actor);
					this.messageService.save(message);
				}

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		final ModelAndView res;
		final Message message = this.messageService.findOne(messageId);

		try {

			final Actor actual = this.actorService.findByPrincipal();
			Assert.isTrue(message.getSender().getId() == actual.getId() || message.getRecipient().getId() == actual.getId());
			this.messageService.delete(message);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect: list.do");
			return res;
		}
		res = new ModelAndView("redirect: list.do");
		return res;
	}

	protected ModelAndView createEditModelAndView(final Message mes) {
		ModelAndView result;

		result = this.createEditModelAndView(mes, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message mes, final String message) {
		ModelAndView result;
		final Actor actual = this.actorService.findByPrincipal();
		final Collection<Topic> topics = this.topicService.findAll();
		final Collection<Actor> allActors = this.actorService.findAll();
		allActors.remove(actual);
		result = new ModelAndView("message/edit");
		result.addObject("mes", mes);
		result.addObject("recipients", allActors);
		result.addObject("topics", topics);
		result.addObject("message", message);
		return result;
	}
}
