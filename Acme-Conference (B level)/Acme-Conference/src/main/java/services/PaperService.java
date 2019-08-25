
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PaperRepository;
import domain.Paper;

@Service
@Transactional
public class PaperService {

	@Autowired
	private PaperRepository	paperRepository;


	public Collection<Paper> findAll() {
		return this.paperRepository.findAll();
	}

	public Paper findOne(final int paperId) {
		return this.paperRepository.findOne(paperId);
	}

	public Paper save(final Paper paper) {
		return this.paperRepository.save(paper);
	}

	public void delete(final Paper paper) {
		this.paperRepository.delete(paper);
	}

	public void flush() {
		this.paperRepository.flush();
	}
}
