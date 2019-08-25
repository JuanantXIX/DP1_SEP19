
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r  where r.reviewer.id = ?1")
	Collection<Report> findAllByReviewerId(int r);

	@Query("select r from Report r where r.submission.id = ?1")
	List<Report> findAllBySubmissionId(int id);

}
