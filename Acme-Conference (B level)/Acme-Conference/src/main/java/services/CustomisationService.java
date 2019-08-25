
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomisationRepository;
import domain.Customisation;

@Service
@Transactional
public class CustomisationService {

	@Autowired
	private CustomisationRepository	customisationRepository;


	public Customisation getCustomisation() {
		return this.customisationRepository.findAll().get(0);
	}
	public Collection<Customisation> findAll() {
		return this.customisationRepository.findAll();
	}

	public Customisation findOne(final int customisationId) {
		return this.customisationRepository.findOne(customisationId);
	}

	public Customisation save(final Customisation customisation) {
		return this.customisationRepository.save(customisation);
	}

	public void delete(final Customisation customisation) {
		this.customisationRepository.delete(customisation);
	}
}
