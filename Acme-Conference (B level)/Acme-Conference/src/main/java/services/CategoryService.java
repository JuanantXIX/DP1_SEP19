
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CategoryRepository;
import domain.Category;
import domain.Conference;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;

	@Autowired
	private ConferenceService	conferenceService;


	public Category create() {
		final Category category = new Category();
		category.setTitle(new ArrayList<String>());
		category.setChildren(new ArrayList<Category>());
		return category;
	}
	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public Category save(final Category category) {
		return this.categoryRepository.save(category);
	}

	public void delete(final Category category) {
		this.categoryRepository.delete(category);
	}
	public void prepareCategoryForDelete(final Category category) {
		final Category newParent = category.getParents().get(0);

		for (final Category c : category.getParents()) {
			c.getChildren().remove(category);
			this.categoryRepository.save(c);
		}
		for (final Category c : category.getChildren()) {
			c.getParents().remove(category);
			this.categoryRepository.save(c);
		}
		final Category newParent2 = this.categoryRepository.findOne(newParent.getId());
		newParent2.getChildren().addAll(category.getChildren());
		this.categoryRepository.save(newParent2);

		for (final Category c : category.getChildren()) {
			c.getParents().add(newParent2);
			this.categoryRepository.save(c);
		}
		this.switchCategoryFromAllConferences(category);

	}
	private void switchCategoryFromAllConferences(final Category category) {
		final Collection<Conference> allConferences = this.conferenceService.findAll();
		for (final Conference c : allConferences)
			if (c.getCategory().equals(category)) {
				c.setCategory(category.getParents().get(0));
				this.conferenceService.save(c);
			}

	}

	public void fixLogicErrorsOnCategories(final Category formCategory) {
		for (final Category c : formCategory.getParents()) {
			c.getChildren().add(formCategory);
			this.categoryRepository.save(c);
		}
		for (final Category c : formCategory.getChildren()) {
			c.getParents().add(formCategory);
			this.categoryRepository.save(c);
		}
	}

	public void fixLogicErrorsOnCategories2(final Category formCategory, final Category databaseCategory) {
		final Collection<Category> allCategories = this.categoryRepository.findAll();

		for (final Category c : allCategories) {
			if (c.getParents().contains(databaseCategory) && !formCategory.getChildren().contains(c)) {
				c.getParents().remove(databaseCategory);
				this.categoryRepository.save(c);
			} else if (!c.getParents().contains(databaseCategory) && formCategory.getChildren().contains(c)) {
				c.getParents().add(databaseCategory);
				this.categoryRepository.save(c);
			}

			if (c.getChildren().contains(databaseCategory) && !formCategory.getParents().contains(c)) {
				c.getChildren().remove(databaseCategory);
				this.categoryRepository.save(c);
			} else if (!c.getChildren().contains(databaseCategory) && formCategory.getParents().contains(c)) {
				c.getChildren().add(databaseCategory);
				this.categoryRepository.save(c);
			}
		}

	}
	public void checkRootCategory(final Category category) {
		if (category.getTitle().contains("CONFERENCE") || category.getTitle().contains("CONFERENCIA"))
			throw new IllegalArgumentException("Editing root category");
	}
}
