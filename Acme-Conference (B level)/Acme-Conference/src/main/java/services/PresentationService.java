
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PresentationRepository;
import domain.Conference;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationRepository	presentationRepository;

	@Autowired
	private ConferenceService		conferenceService;


	public Presentation create(final int conferenceId) {
		final Presentation presentation = new Presentation();
		presentation.setAttachments(new ArrayList<String>());
		presentation.setSpeakers(new ArrayList<String>());
		final Conference c = this.conferenceService.findOne(conferenceId);
		presentation.setConference(c);

		return presentation;
	}
	public Collection<Presentation> findAll() {
		return this.presentationRepository.findAll();
	}

	public Presentation findOne(final int presentationId) {
		return this.presentationRepository.findOne(presentationId);
	}

	public Presentation save(final Presentation presentation) {
		return this.presentationRepository.save(presentation);
	}

	public void delete(final Presentation presentation) {
		this.presentationRepository.delete(presentation);
	}
}
