
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select s from Submission s where s.conference.id = ?1")
	List<Submission> findAllByConferenceId(int conferenceId);

	@Query("select s from Submission s where s.author.id = ?1")
	Collection<Submission> findAllByAuthorId(int id);

	@Query("select s from Submission s order by s.status")
	Collection<Submission> findAllGroupedByStatus();

	@Query("select s from Submission s where ?1 member of s.reviewer")
	Collection<Submission> findAllByReviewerId(int id);

	@Query("select s from Submission s where s.status = 'UNDER-REVIEW'")
	List<Submission> findAllUnderReview();

	@Query("select distinct s.conference from Submission s where s.author.id != ?1")
	Collection<Submission> findAllConferencesICanAppy(int id);

	@Query("select s from Submission s where s.statusVisible = FALSE")
	List<Submission> findAllInvisible();

}
