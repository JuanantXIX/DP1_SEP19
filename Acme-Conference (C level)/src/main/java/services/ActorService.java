
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	public ActorRepository			actorRepository;

	@Autowired
	public UserAccountRepository	userAccountRepository;


	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.actorRepository.findByPrincipal(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor res;
		Assert.notNull(userAccount);
		Assert.notNull(userAccount.getId());

		res = this.actorRepository.findByUserAccountId(userAccount.getId());

		return res;
	}
	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public Actor save(final Actor actor) {
		return this.actorRepository.save(actor);
	}

	public void delete(final Actor actor) {
		this.actorRepository.delete(actor);
	}

}
