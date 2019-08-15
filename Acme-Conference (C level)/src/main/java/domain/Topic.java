
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity {

	private Collection<String>	name;


	@ElementCollection
	public Collection<String> getName() {
		return this.name;
	}

	public void setName(final Collection<String> name) {
		this.name = name;
	}

}
