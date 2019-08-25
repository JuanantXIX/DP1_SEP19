
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private Collection<String>	keywords;


	@ElementCollection
	public Collection<String> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(final Collection<String> keywords) {
		this.keywords = keywords;
	}

}
