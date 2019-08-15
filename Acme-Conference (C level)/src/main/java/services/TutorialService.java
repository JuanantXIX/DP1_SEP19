
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.TutorialRepository;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private SectionService		sectionService;


	public Tutorial create(final int conferenceId) {
		final Tutorial t = new Tutorial();
		t.setAttachments(new ArrayList<String>());
		t.setSpeakers(new ArrayList<String>());
		final Conference c = this.conferenceService.findOne(conferenceId);
		t.setConference(c);

		return t;
	}

	public Collection<Tutorial> findAll() {
		return this.tutorialRepository.findAll();
	}

	public Tutorial findOne(final int tutorialId) {
		return this.tutorialRepository.findOne(tutorialId);
	}

	public Tutorial save(final Tutorial tutorial) {
		return this.tutorialRepository.save(tutorial);
	}

	public void delete(final Tutorial tutorial) {
		final Collection<Section> sections = this.sectionService.findAllByTutorial(tutorial.getId());
		for (final Section s : sections)
			this.sectionService.delete(s);

		this.tutorialRepository.delete(tutorial);
	}
}
