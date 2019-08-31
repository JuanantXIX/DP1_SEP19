
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private Integer		originalityScore;
	private Integer		qualityScore;
	private Integer		readabilityScore;
	private String		decision;
	private String		comments;
	private Reviewer	reviewer;
	private Submission	submission;


	@NotNull
	@Range(min = 0, max = 10)
	public Integer getOriginalityScore() {
		return this.originalityScore;
	}

	public void setOriginalityScore(final Integer originalityScore) {
		this.originalityScore = originalityScore;
	}
	@NotNull
	@Range(min = 0, max = 10)
	public Integer getQualityScore() {
		return this.qualityScore;
	}

	public void setQualityScore(final Integer qualityScore) {
		this.qualityScore = qualityScore;
	}
	@NotNull
	@Range(min = 0, max = 10)
	public Integer getReadabilityScore() {
		return this.readabilityScore;
	}

	public void setReadabilityScore(final Integer readabilityScore) {
		this.readabilityScore = readabilityScore;
	}
	@NotBlank
	@Pattern(regexp = "^" + "REJECT" + "|" + "ACCEPT" + "|" + "BORDER-LINE" + "$")
	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@ManyToOne(optional = false)
	public Reviewer getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(final Reviewer reviewer) {
		this.reviewer = reviewer;
	}

	@ManyToOne(optional = false)
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

}
