
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SectionRepository;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private TutorialService		tutorialService;


	public Section create(final int tutorialId) {
		final Section s = new Section();
		s.setPictures(new ArrayList<String>());
		final Tutorial t = this.tutorialService.findOne(tutorialId);
		s.setTutorial(t);

		return s;
	}

	public Collection<Section> findAll() {
		return this.sectionRepository.findAll();
	}

	public Section findOne(final int sectionId) {
		return this.sectionRepository.findOne(sectionId);
	}

	public Section save(final Section section) {
		return this.sectionRepository.save(section);
	}

	public void delete(final Section section) {
		this.sectionRepository.delete(section);
	}

	public List<Section> findAllByTutorial(final int tutorialId) {
		return this.sectionRepository.findAllByTutorial(tutorialId);
	}
}
