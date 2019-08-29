
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	private String					ticker;			//No need to pattern, it is generated automatically by the system.
	private Date					moment;
	private Paper					paper;
	private Paper					cameraReadyPaper;
	private String					status;
	private Collection<Reviewer>	reviewer;
	private Author					author;
	private Conference				conference;
	private boolean					statusVisible;


	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@OneToOne
	@Valid
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(final Paper paper) {
		this.paper = paper;
	}

	@OneToOne(optional = true)
	@Valid
	public Paper getCameraReadyPaper() {
		return this.cameraReadyPaper;
	}

	public void setCameraReadyPaper(final Paper cameraReadyPaper) {
		this.cameraReadyPaper = cameraReadyPaper;
	}

	@NotBlank
	@Pattern(regexp = "^" + "UNDER-REVIEW" + "|" + "ACCEPTED" + "|" + "REJECTED" + "$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@ManyToMany
	public Collection<Reviewer> getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(final Collection<Reviewer> reviewer) {
		this.reviewer = reviewer;
	}

	@ManyToOne(optional = false)
	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(final Author author) {
		this.author = author;
	}

	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@NotNull
	public boolean isStatusVisible() {
		return this.statusVisible;
	}

	public void setStatusVisible(final boolean statusVisible) {
		this.statusVisible = statusVisible;
	}

}
