
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReportRepository;
import domain.Report;
import domain.Reviewer;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;


	public Report create() {
		final Report r = new Report();
		return r;
	}
	public Collection<Report> findAll() {
		return this.reportRepository.findAll();
	}

	public Report findOne(final int reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public Report save(final Report report) {
		return this.reportRepository.save(report);
	}

	public void delete(final Report report) {
		this.reportRepository.delete(report);
	}

	public Collection<Report> findAllByReviewerId(final Reviewer r) {
		return this.reportRepository.findAllByReviewerId(r.getId());
	}
	public List<Report> findAllBySubmissionId(final int id) {
		return this.reportRepository.findAllBySubmissionId(id);
	}
}
