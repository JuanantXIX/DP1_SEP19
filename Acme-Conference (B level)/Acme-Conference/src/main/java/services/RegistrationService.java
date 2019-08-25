
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RegistrationRepository;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	@Autowired
	private RegistrationRepository	registrationRepository;


	public Collection<Registration> findAll() {
		return this.registrationRepository.findAll();
	}

	public Registration findOne(final int registrationId) {
		return this.registrationRepository.findOne(registrationId);
	}

	public Registration save(final Registration registration) {
		return this.registrationRepository.save(registration);
	}

	public void delete(final Registration registration) {
		this.registrationRepository.delete(registration);
	}

	public Collection<Registration> findAllByAuthorId(final int id) {
		return this.registrationRepository.findAllByAuthorId(id);
	}

	public Registration create(final int conferenceId) {
		final Registration reg = new Registration();

		return reg;
	}
}
