
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private AuthorRepository		authorRepository;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private ActorService			actorService;


	public Author findByPrincipal() {
		Author res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.authorRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Author create() {
		final Author res = new Author();
		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.AUTHOR);
		newUser.addAuthority(f);
		res.setUserAccount(newUser);

		return res;
	}

	public boolean checkAuthor() {
		boolean res;
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.AUTHOR);
		res = user.getAuthorities().contains(a);
		return res;
	}

	public Collection<Author> findAll() {
		return this.authorRepository.findAll();
	}

	public Author findOne(final int authorId) {
		return this.authorRepository.findOne(authorId);
	}

	public Author save(final Author author) {
		Assert.notNull(author);
		//if the valid phone number the author has introduced does not have any country code, the default country code is added.
		final String phone = author.getPhoneNumber();
		final String code = this.customisationService.getCustomisation().getPhoneNumberCode();
		if (phone.matches("^[0-9]{4,}$"))
			author.setPhoneNumber(code.concat(phone));

		//If I am editing an existing actor:
		if (author.getId() != 0) {
			Assert.isTrue(this.checkAuthor());
			//The edited author must be the same as the logged author:

			final Author authorLogged = (Author) this.actorService.findByPrincipal();
			Assert.notNull(authorLogged);
			Assert.isTrue(authorLogged.getId() == author.getId());
		}
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String newPassword = encoder.encodePassword(author.getUserAccount().getPassword(), null);
		final UserAccount uaAuthor = author.getUserAccount();
		uaAuthor.setPassword(newPassword);
		author.setUserAccount(uaAuthor);

		return this.authorRepository.save(author);
	}
	public void delete(final Author author) {
		this.authorRepository.delete(author);
	}

	public Collection<Author> findAllRegisteredByConferenceId(final int conferenceId) {
		return this.authorRepository.findAllRegisteredByConferenceId(conferenceId);
	}

	public Collection<Author> findAllSubmissionByConferenceId(final int conferenceId) {
		return this.authorRepository.findAllSubmissionByConferenceId(conferenceId);
	}
}
