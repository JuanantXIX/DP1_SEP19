
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;
import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select c from Conference c where (c.title like %?1% or c.venue like %?1% or c.summary like %?1% or c.acronym like %?1%) and c.finalMode = true group by c.id")
	public Collection<Conference> findConferencesByKeyword(String keyword);

	@Query("select f from Conference f where f.category.id = ?1 and f.finalMode = true")
	public Collection<Conference> findConferencesByCategory(int categoryId);

	@Query("select f from Conference f where f.startDate > ?1 and f.finalMode = true")
	public Collection<Conference> findConferencesByStartDate(Date startDate);

	@Query("select f from Conference f where f.endDate < ?1 and f.finalMode = true")
	public Collection<Conference> findConferencesByEndtDate(Date endDate);

	@Query("select f from Conference f where f.fee < ?1 and f.finalMode = true")
	public Collection<Conference> findConferencesByFee(Double fee);
}
