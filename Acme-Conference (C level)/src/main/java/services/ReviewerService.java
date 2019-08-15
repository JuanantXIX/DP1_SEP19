
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Reviewer;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository		reviewerRepository;

	@Autowired
	private CustomisationService	customisationService;


	public boolean checkReviewer() {
		boolean res;
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.REVIEWER);
		res = user.getAuthorities().contains(a);
		return res;
	}

	public Reviewer findByPrincipal() {
		Reviewer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.reviewerRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Reviewer create() {
		final Reviewer res = new Reviewer();
		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.REVIEWER);
		newUser.addAuthority(f);
		res.setUserAccount(newUser);
		res.setKeywords(new ArrayList<String>());

		return res;
	}

	public Collection<Reviewer> findAll() {
		return this.reviewerRepository.findAll();
	}

	public Reviewer findOne(final int reviewerId) {
		return this.reviewerRepository.findOne(reviewerId);
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);
		//if the valid phone number the reviewer has introduced does not have any country code, the default country code is added.
		final String phone = reviewer.getPhoneNumber();
		final String code = this.customisationService.getCustomisation().getPhoneNumberCode();
		if (phone.matches("^[0-9]{4,}$"))
			reviewer.setPhoneNumber(code.concat(phone));

		//If I am editing an existing actor:
		if (reviewer.getId() != 0) {//
			Assert.isTrue(this.checkReviewer());
			//The edited reviewer must be the same as the logged reviewer:

			final Reviewer reviewerLogged = this.findByPrincipal();
			Assert.notNull(reviewerLogged);
			Assert.isTrue(reviewerLogged.getId() == reviewer.getId());
		}
		//Creating a new actor.
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String newPassword = encoder.encodePassword(reviewer.getUserAccount().getPassword(), null);
		final UserAccount uaReviewer = reviewer.getUserAccount();
		uaReviewer.setPassword(newPassword);
		reviewer.setUserAccount(uaReviewer);

		return this.reviewerRepository.save(reviewer);
	}

	public void delete(final Reviewer reviewer) {
		this.reviewerRepository.delete(reviewer);
	}

	public List<Reviewer> allReviewersByKeyword(final String title, final String summary) {
		final List<Reviewer> res = new ArrayList<Reviewer>();
		String[] wordsInTitle;
		String[] wordsInSummary;
		final List<Reviewer> reviewers = this.reviewerRepository.findAll();
		boolean containsKeyword = false;
		boolean alreadyIntroduced = false;
		wordsInTitle = title.split(" ");
		wordsInSummary = summary.split(" ");

		for (final Reviewer r : reviewers) {
			containsKeyword = false;
			alreadyIntroduced = false;
			for (int i = 0; i < wordsInTitle.length; i++)
				if (r.getKeywords().contains(wordsInTitle[i])) {
					containsKeyword = true;
					break;
				}
			if (containsKeyword == true) {
				res.add(r);
				containsKeyword = false;
				alreadyIntroduced = true;

			}
			if (!alreadyIntroduced) {
				for (int i = 0; i < wordsInSummary.length; i++)
					if (r.getKeywords().contains(wordsInSummary[i])) {
						containsKeyword = true;
						break;
					}
				if (containsKeyword == true && alreadyIntroduced == false)
					res.add(r);

			}
		}

		if (res.size() > 3)
			res.subList(0, 2);
		return res;
	}
}
