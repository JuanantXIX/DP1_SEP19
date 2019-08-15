
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends Activity {

	private Paper	paper;


	@OneToOne
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(final Paper paper) {
		this.paper = paper;
	}

}
