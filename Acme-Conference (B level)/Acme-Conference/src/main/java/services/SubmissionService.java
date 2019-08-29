
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SubmissionRepository;
import domain.Author;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;


	public Submission create() {
		final Submission s = new Submission();
		s.setReviewer(new ArrayList<Reviewer>());
		s.setMoment(new Date());
		s.setStatus("UNDER-REVIEW");
		s.setStatusVisible(false);
		return s;
	}

	public List<Submission> findAllByConferenceId(final int conferenceId) {
		return this.submissionRepository.findAllByConferenceId(conferenceId);
	}

	public Collection<Submission> findAll() {
		return this.submissionRepository.findAll();
	}

	public Submission findOne(final int submissionId) {
		return this.submissionRepository.findOne(submissionId);
	}

	public Submission save(final Submission submission) {
		return this.submissionRepository.save(submission);
	}

	public void delete(final Submission submission) {
		this.submissionRepository.delete(submission);
	}

	public Collection<Submission> findAllByAuthorId(final int id) {
		return this.submissionRepository.findAllByAuthorId(id);
	}

	public String generateTicker(final Author author) {
		String ticker = "";
		final List<String> alpha = new ArrayList<String>();
		alpha.add("A");
		alpha.add("B");
		alpha.add("C");
		alpha.add("D");
		alpha.add("E");
		alpha.add("F");
		alpha.add("G");
		alpha.add("H");
		alpha.add("I");
		alpha.add("J");
		alpha.add("K");
		alpha.add("L");
		alpha.add("M");
		alpha.add("N");
		alpha.add("O");
		alpha.add("P");
		alpha.add("Q");
		alpha.add("R");
		alpha.add("S");
		alpha.add("T");
		alpha.add("U");
		alpha.add("V");
		alpha.add("W");
		alpha.add("X");
		alpha.add("Y");
		alpha.add("Z");

		final List<String> nums = new ArrayList<String>();
		nums.add("0");
		nums.add("1");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		nums.add("6");
		nums.add("7");
		nums.add("8");
		nums.add("9");

		final String name = author.getName();
		final String middleName = author.getMiddleName();
		final String surname = author.getSurname();

		ticker = ticker + name.charAt(0);
		if (middleName != null && middleName != "")
			ticker += middleName.charAt(0);

		ticker = ticker + surname.charAt(0);

		ticker = ticker + "-";
		for (Integer i = 0; i < 4; i++) {
			final Integer selector = new Random().nextInt(26);
			if (selector > 14) {
				final Integer letra = new Random().nextInt(26);
				final String a = alpha.get(letra);
				ticker = ticker + a;
			} else {
				final Integer numero = new Random().nextInt(9);
				final String b = nums.get(numero);
				ticker = ticker + b;
			}
		}

		return ticker;
	}

	public Collection<Submission> findAllGroupedByStatus() {
		return this.submissionRepository.findAllGroupedByStatus();
	}

	public Collection<Submission> findAllByReviewerId(final int id) {
		return this.submissionRepository.findAllByReviewerId(id);
	}

	public List<Submission> findAllUnderReview() {
		return this.submissionRepository.findAllUnderReview();
	}

	public List<Submission> findAllInvisible() {
		return this.submissionRepository.findAllInvisible();
	}

	public Collection<Submission> findAllConferencesICanApply(final int id) {
		return this.submissionRepository.findAllConferencesICanAppy(id);
	}
}
