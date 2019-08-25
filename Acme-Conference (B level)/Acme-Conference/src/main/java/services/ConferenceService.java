
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository	conferenceRepository;


	public Conference create() {
		final Conference c = new Conference();
		c.setFinalMode(false);
		return c;
	}
	public Collection<Conference> findAll() {
		return this.conferenceRepository.findAll();
	}

	public Conference findOne(final int conferenceId) {
		return this.conferenceRepository.findOne(conferenceId);
	}

	public Conference save(final Conference conference) {
		return this.conferenceRepository.save(conference);
	}

	public void delete(final Conference conference) {
		this.conferenceRepository.delete(conference);
	}

	public Collection<Conference> findAllForthcomingConferences() {
		final List<Conference> allConferences = this.conferenceRepository.findAll();
		final Collection<Conference> res = new ArrayList<Conference>();
		for (final Conference c : allConferences)
			if (c.getStartDate().after(new Date()) && c.isFinalMode() == true)
				res.add(c);

		return res;
	}

	public Collection<Conference> findAllRunningConferences() {
		final List<Conference> allConferences = this.conferenceRepository.findAll();
		final Collection<Conference> res = new ArrayList<Conference>();
		for (final Conference c : allConferences)
			if (c.getStartDate().before(new Date()) && c.getEndDate().after(new Date()) && c.isFinalMode() == true)
				res.add(c);

		return res;
	}

	public Collection<Conference> findAllPastConferences() {
		final List<Conference> allConferences = this.conferenceRepository.findAll();
		final Collection<Conference> res = new ArrayList<Conference>();
		for (final Conference c : allConferences)
			if (c.getEndDate().before(new Date()) && c.isFinalMode() == true)
				res.add(c);

		return res;
	}

	public Collection<Conference> searchConferences(final String keyword) {
		return this.conferenceRepository.searchConferences(keyword);
	}
	public void doesConferenceMatchDatesCriteria(final Conference conference) {
		Assert.isTrue(conference.getSubmissionDeadline().after(new Date()));
		Assert.isTrue(conference.getSubmissionDeadline().before(conference.getNotificationDeadline()));
		Assert.isTrue(conference.getNotificationDeadline().before(conference.getCameraReadyDeadline()));
		Assert.isTrue(conference.getCameraReadyDeadline().before(conference.getStartDate()));
		Assert.isTrue(conference.getStartDate().before(conference.getEndDate()));

	}
}
