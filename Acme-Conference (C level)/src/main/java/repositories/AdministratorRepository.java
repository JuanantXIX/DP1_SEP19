
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int userAccountId);

	//Dashboard queries:

	//1. Min, max, avg and stddev of the number of submissions per conference
	@Query("select max(1*(select count(s) from Submission s where s.conference.id =c.id)) from Conference c")
	Integer maxSubmissionsPerConference();

	@Query("select min(1*(select count(s) from Submission s where s.conference.id =c.id)) from Conference c")
	Integer minSubmissionsPerConference();

	@Query("select avg(1.0*(select count(s) from Submission s where s.conference.id =c.id)) from Conference c")
	Double avgSubmissionsPerConference();

	@Query("select stddev(1.0*(select count(s) from Submission s where s.conference.id =c.id)) from Conference c")
	Double stddevSubmissionsPerConference();

	//2. Min, max, avg and stddev of the number of registrations per conference
	@Query("select max(1*(select count(s) from Registration s where s.conference.id =c.id)) from Conference c")
	Integer maxRegistrationsPerConference();

	@Query("select min(1*(select count(s) from Registration s where s.conference.id =c.id)) from Conference c")
	Integer minRegistrationsPerConference();

	@Query("select avg(1.0*(select count(s) from Registration s where s.conference.id =c.id)) from Conference c")
	Double avgRegistrationsPerConference();

	@Query("select stddev(1.0*(select count(s) from Registration s where s.conference.id =c.id)) from Conference c")
	Double stddevRegistrationsPerConference();

	//3. Min, max, avg and stddev of the number of conference fees.
	@Query("select max(c.fee) from Conference c")
	Integer maxConferenceFees();

	@Query("select min(c.fee) from Conference c")
	Integer minConferenceFees();

	@Query("select avg(c.fee) from Conference c")
	Double avgConferenceFees();

	@Query("select stddev(c.fee) from Conference c")
	Double stddevConferenceFees();

	//4. Min, max avg and stddev of the number of days per conference.
	@Query("select max(1*(datediff(c.endDate, c.startDate))) from Conference c")
	Integer maxDaysPerConference();
	@Query("select min(1*(datediff(c.endDate, c.startDate))) from Conference c")
	Integer minDaysPerConference();
	@Query("select avg(1*(datediff(c.endDate, c.startDate))) from Conference c")
	Double avgDaysPerConference();
	@Query("select stddev(1*(datediff(c.endDate, c.startDate))) from Conference c")
	Double stddevDaysPerConference();

}
