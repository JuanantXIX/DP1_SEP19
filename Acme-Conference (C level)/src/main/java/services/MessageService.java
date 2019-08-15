
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.Submission;
import domain.Topic;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TopicService		topicService;


	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);
	}

	public Message save(final Message message) {
		final Actor a = this.actorService.findByPrincipal();
		try {
			TimeUnit.MILLISECONDS.sleep(10);
			Assert.isTrue(a.getId() == message.getSender().getId());
			Assert.isTrue(message.getMoment().before(new Date()));
			final Message saved = this.messageRepository.save(message);

			return saved;
		} catch (final Throwable oops) {
			return null;
		}
	}
	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

	public List<Message> findBySender(final Actor actual) {
		final List<Message> allMessages = (List<Message>) this.findAll();
		final List<Message> res = new ArrayList<Message>();
		for (final Message m : allMessages)
			if (m.getSender().getId() == actual.getId())
				res.add(m);

		return res;
	}

	public List<Message> findByRecipient(final Actor actual) {
		final List<Message> allMessages = (List<Message>) this.findAll();
		final List<Message> res = new ArrayList<Message>();
		for (final Message m : allMessages)
			if (m.getRecipient().getId() == actual.getId())
				res.add(m);

		return res;
	}

	public Message create() {
		final Message mes = new Message();
		mes.setBroadcast(false);
		mes.setMoment(new Date());
		return mes;
	}

	public void sendAcceptedMessage(final Submission s, final Administrator a) {
		final Message mes = this.create();
		mes.setSender(a);
		mes.setRecipient(s.getAuthor());
		mes.setBody("One of your submissions has been accepted. Please check it out on the corresponding menu. || Una de sus propuestas ha sido aceptada. Por favor, revíselo en el menú correspondiente.");
		mes.setBroadcast(false);
		mes.setMoment(new Date());
		mes.setSubject("Status change || Cambio de estado");
		final Collection<Topic> topics = this.topicService.findAll();
		for (final Topic t : topics)
			for (final String name : t.getName())
				if (name.equals("OTHER"))
					mes.setTopic(t);

		this.save(mes);

	}

	public void sendRejectedMessage(final Submission s, final Administrator a) {
		final Message mes = this.create();
		mes.setSender(a);
		mes.setRecipient(s.getAuthor());
		mes.setBody("One of your submissions has been rejected. Please check it out on the corresponding menu. || Una de sus propuestas ha sido rechazada. Por favor, revíselo en el menú correspondiente.");
		mes.setBroadcast(false);
		mes.setMoment(new Date());
		mes.setSubject("Status change || Cambio de estado");
		final Collection<Topic> topics = this.topicService.findAll();
		for (final Topic t : topics)
			for (final String name : t.getName())
				if (name.equals("OTHER"))
					mes.setTopic(t);

		this.save(mes);

	}
}
