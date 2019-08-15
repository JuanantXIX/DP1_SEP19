
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Actor;
import domain.Topic;

public class MessageForm {

	private Collection<Actor>	recipient;
	private Topic				topic;
	private String				body;
	private String				subject;
	private boolean				broadcast;
	private Integer				conferenceId;
	private Integer				type;


	@ElementCollection(targetClass = Actor.class)
	public Collection<Actor> getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Collection<Actor> recipient) {
		this.recipient = recipient;
	}

	@NotNull
	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(final Topic topic) {
		this.topic = topic;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotNull
	public boolean isBroadcast() {
		return this.broadcast;
	}

	public void setBroadcast(final boolean broadcast) {
		this.broadcast = broadcast;
	}

	@NotNull
	public Integer getConferenceId() {
		return this.conferenceId;
	}

	public void setConferenceId(final Integer conferenceId) {
		this.conferenceId = conferenceId;
	}

	@NotNull
	public Integer getType() {
		return this.type;
	}

	public void setType(final Integer type) {
		this.type = type;
	}

}
