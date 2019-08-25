
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private List<String>	title;
	private List<Category>	parents;
	private List<Category>	children;


	@ElementCollection
	public List<String> getTitle() {
		return this.title;
	}

	public void setTitle(final List<String> title) {
		this.title = title;
	}

	@ManyToMany
	public List<Category> getParents() {
		return this.parents;
	}

	public void setParents(final List<Category> parents) {
		this.parents = parents;
	}

	@ManyToMany
	public List<Category> getChildren() {
		return this.children;
	}

	public void setChildren(final List<Category> children) {
		this.children = children;
	}

}
