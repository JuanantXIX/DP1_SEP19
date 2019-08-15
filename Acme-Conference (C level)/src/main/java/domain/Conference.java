
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends DomainEntity {

	private String			title;
	private String			acronym;
	private String			venue;
	private Date			submissionDeadline;
	private Date			notificationDeadline;
	private Date			cameraReadyDeadline;
	private Date			startDate;
	private Date			endDate;
	private String			summary;
	private Double			fee;
	private Administrator	administrator;
	private boolean			finalMode;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getAcronym() {
		return this.acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

	@NotBlank
	public String getVenue() {
		return this.venue;
	}

	public void setVenue(final String venue) {
		this.venue = venue;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getSubmissionDeadline() {
		return this.submissionDeadline;
	}

	public void setSubmissionDeadline(final Date submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getNotificationDeadline() {
		return this.notificationDeadline;
	}

	public void setNotificationDeadline(final Date notificationDeadline) {
		this.notificationDeadline = notificationDeadline;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getCameraReadyDeadline() {
		return this.cameraReadyDeadline;
	}

	public void setCameraReadyDeadline(final Date cameraReadyDeadline) {
		this.cameraReadyDeadline = cameraReadyDeadline;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@NotNull
	@Range(min = 0, max = 100)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@ManyToOne(optional = false)
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

}
