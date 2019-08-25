
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomisationService	customisationService;


	public boolean checkAdministrator() {
		boolean res;
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		res = user.getAuthorities().contains(a);
		return res;
	}

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.administratorRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Administrator create() {
		final Administrator res = new Administrator();
		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.ADMIN);
		newUser.addAuthority(f);
		res.setUserAccount(newUser);

		return res;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		//if the valid phone number the administrator has introduced does not have any country code, the default country code is added.
		final String phone = administrator.getPhoneNumber();
		final String code = this.customisationService.getCustomisation().getPhoneNumberCode();
		if (phone.matches("^[0-9]{4,}$"))
			administrator.setPhoneNumber(code.concat(phone));

		//If I am editing an existing actor:
		if (administrator.getId() != 0) {
			Assert.isTrue(this.checkAdministrator());
			//The edited administrator must be the same as the logged administrator:

			final Administrator administratorLogged = (Administrator) this.actorService.findByPrincipal();
			Assert.notNull(administratorLogged);
			Assert.isTrue(administratorLogged.getId() == administrator.getId());
		}
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String newPassword = encoder.encodePassword(administrator.getUserAccount().getPassword(), null);
		final UserAccount uaAdministrator = administrator.getUserAccount();
		uaAdministrator.setPassword(newPassword);
		administrator.setUserAccount(uaAdministrator);

		return this.administratorRepository.save(administrator);
	}

	public void delete(final Administrator administrator) {
		this.administratorRepository.delete(administrator);
	}
}
