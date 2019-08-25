
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FinderRepository;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private ConferenceService	conferenceService;


	public Finder create() {
		final Finder finder = new Finder();
		finder.setConferences(new ArrayList<Conference>());
		return finder;
	}
	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final int finderId) {
		return this.finderRepository.findOne(finderId);
	}

	public Finder save(final Finder finder) {
		return this.finderRepository.save(finder);
	}

	public void delete(final Finder finder) {
		this.finderRepository.delete(finder);
	}
	public void flush() {
		this.finderRepository.flush();
	}
	public Collection<Conference> searchConferences(final Finder finder) {
		final List<Conference> allConferences = (List<Conference>) this.conferenceService.findAll();
		if (finder.getKeyword() != null && finder.getKeyword() != "")
			allConferences.retainAll(this.finderRepository.findConferencesByKeyword(finder.getKeyword()));
		if (finder.getCategory() != null)
			allConferences.retainAll(this.finderRepository.findConferencesByCategory(finder.getCategory().getId()));
		if (finder.getStartDate() != null)
			allConferences.retainAll((this.finderRepository.findConferencesByStartDate(finder.getStartDate())));
		if (finder.getEndDate() != null)
			allConferences.retainAll(this.finderRepository.findConferencesByEndtDate(finder.getEndDate()));
		if (finder.getMaximumFee() != null)
			allConferences.retainAll(this.finderRepository.findConferencesByFee(finder.getMaximumFee()));
		return allConferences;
	}

}
