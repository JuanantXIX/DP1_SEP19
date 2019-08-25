
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.TopicRepository;
import domain.Topic;

@Service
@Transactional
public class TopicService {

	@Autowired
	private TopicRepository	topicRepository;


	public Topic create() {
		final Topic topic = new Topic();
		topic.setName(new ArrayList<String>());
		return topic;
	}
	public Collection<Topic> findAll() {
		return this.topicRepository.findAll();
	}

	public Topic findOne(final int topicId) {
		return this.topicRepository.findOne(topicId);
	}

	public Topic save(final Topic topic) {
		return this.topicRepository.save(topic);
	}

	public void delete(final Topic topic) {
		this.topicRepository.delete(topic);
	}
}
