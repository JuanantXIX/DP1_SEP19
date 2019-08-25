
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuthorService;
import services.ConferenceService;
import services.MessageService;
import services.TopicService;
import controllers.AbstractController;
import domain.Actor;
import domain.Author;
import domain.Message;
import domain.Topic;
import forms.MessageForm;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	@Autowired
	private MessageService		messageService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TopicService		topicService;

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast(final int broadcastType, final int conferenceId) {
		final ModelAndView res;

		Collection<Author> recipients = new ArrayList<Author>();
		final Collection<Actor> good = new ArrayList<Actor>();
		if (broadcastType == 0) //Registers
			recipients = this.authorService.findAllRegisteredByConferenceId(conferenceId);
		else if (broadcastType == 1) //Submissions
			recipients = this.authorService.findAllSubmissionByConferenceId(conferenceId);
		else {
			res = new ModelAndView("redirect:/message/list.do");
			return res;
		}
		for (final Author a : recipients)
			good.add(a);
		final MessageForm messageForm = new MessageForm();
		messageForm.setConferenceId(conferenceId);
		messageForm.setType(broadcastType);
		messageForm.setRecipient(good);
		res = this.createEditModelAndView(messageForm);
		return res;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "send")
	public ModelAndView broadcast(@Valid @ModelAttribute final MessageForm message, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {

				final Actor a = this.actorService.findByPrincipal();
				final Message messageRecursive = this.messageService.create();
				messageRecursive.setSender(a);
				messageRecursive.setMoment(new Date());
				messageRecursive.setBody(message.getBody());
				messageRecursive.setBroadcast(true);
				messageRecursive.setTopic(message.getTopic());
				messageRecursive.setSubject(message.getSubject());

				for (final Actor actor : message.getRecipient()) {
					messageRecursive.setRecipient(actor);
					this.messageService.save(messageRecursive);
				}

				result = new ModelAndView("redirect:/message/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm mes) {
		ModelAndView result;

		result = this.createEditModelAndView(mes, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final MessageForm mes, final String message) {
		ModelAndView result;
		final Collection<Topic> topics = this.topicService.findAll();
		result = new ModelAndView("message/broadcast");
		result.addObject("messageForm", mes);
		result.addObject("topics", topics);
		result.addObject("message", message);
		return result;
	}
}
