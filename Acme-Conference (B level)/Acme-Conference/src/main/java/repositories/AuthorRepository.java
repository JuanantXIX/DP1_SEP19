
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.userAccount.id = ?1")
	Author findByPrincipal(int userAccountId);

	@Query("select a from Registration r join r.author a where r.conference.id = ?1")
	Collection<Author> findAllRegisteredByConferenceId(int conferenceId);

	@Query("select a from Submission r join r.author a where r.conference.id = ?1")
	Collection<Author> findAllSubmissionByConferenceId(int conferenceId);
}
