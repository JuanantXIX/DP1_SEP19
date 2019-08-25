
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;


	public Comment create() {
		final Comment comment = new Comment();
		comment.setMoment(new Date());
		return comment;
	}
	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment findOne(final int commentId) {
		return this.commentRepository.findOne(commentId);
	}

	public Comment save(final Comment comment) {
		return this.commentRepository.save(comment);
	}

	public void delete(final Comment comment) {
		this.commentRepository.delete(comment);
	}
}
