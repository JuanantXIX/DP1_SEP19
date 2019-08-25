
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PanelRepository;
import domain.Conference;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	@Autowired
	private PanelRepository		panelRepository;

	@Autowired
	private ConferenceService	conferenceService;


	public Panel create(final int conferenceId) {
		final Panel panel = new Panel();
		panel.setAttachments(new ArrayList<String>());
		panel.setSpeakers(new ArrayList<String>());
		final Conference c = this.conferenceService.findOne(conferenceId);
		panel.setConference(c);

		return panel;
	}
	public Collection<Panel> findAll() {
		return this.panelRepository.findAll();
	}

	public Panel findOne(final int panelId) {
		return this.panelRepository.findOne(panelId);
	}

	public Panel save(final Panel panel) {
		return this.panelRepository.save(panel);
	}

	public void delete(final Panel panel) {
		this.panelRepository.delete(panel);
	}
}
