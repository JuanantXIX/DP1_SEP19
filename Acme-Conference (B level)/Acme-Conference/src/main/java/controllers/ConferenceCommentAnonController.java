
package controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.ConferenceService;
import domain.Actor;
import domain.Comment;
import domain.Conference;
import forms.CommentForm;

@Controller
@RequestMapping("/comment/conference")
public class ConferenceCommentAnonController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CommentService		commentService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		try {
			Assert.isTrue(conference.isFinalMode());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		result = new ModelAndView("comment/list");
		result.addObject("requestURI", "/comment/conference/list.do");
		result.addObject("comments", conference.getComments());
		result.addObject("relationId", conferenceId);
		result.addObject("creationURL", "comment/conference/post.do");
		result.addObject("showURL", "comment/conference/show.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int commentId) {
		final ModelAndView res;
		final Comment comment = this.commentService.findOne(commentId);
		res = new ModelAndView("comment/show");
		res.addObject("comment", comment);
		return res;
	}

	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ModelAndView post(@RequestParam final int id) {
		final ModelAndView res;
		try {
			final Conference c = this.conferenceService.findOne(id);
			Assert.isTrue(c.isFinalMode());
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
			return res;
		}
		final CommentForm commentForm = new CommentForm();
		commentForm.setRelationId(id);
		res = this.createEditModelAndView(commentForm);
		return res;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST, params = "save")
	public ModelAndView post(@Valid @ModelAttribute final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {

				final Comment commentToSave = this.commentService.create();
				commentToSave.setAuthor(commentForm.getAuthor());
				commentToSave.setTitle(commentForm.getTitle());
				commentToSave.setMoment(commentForm.getMoment());
				commentToSave.setText(commentForm.getText());

				final Conference conference = this.conferenceService.findOne(commentForm.getRelationId());
				final Comment saved = this.commentService.save(commentToSave);
				conference.getComments().add(saved);
				this.conferenceService.save(conference);

				result = new ModelAndView("redirect:/comment/conference/list.do?conferenceId=" + conference.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final CommentForm mes) {
		ModelAndView result;

		result = this.createEditModelAndView(mes, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final CommentForm mes, final String message) {
		ModelAndView result;
		try {
			final Actor a = this.actorService.findByPrincipal();
			mes.setAuthor(a.getName() + " " + a.getMiddleName() + " " + a.getSurname());
			mes.setMoment(new Date());
		} catch (final Throwable oops) {
			mes.setMoment(new Date());
		}

		result = new ModelAndView("comment/post");
		result.addObject("commentForm", mes);
		result.addObject("message", message);
		result.addObject("returnURL", "comment/conference/post.do");
		result.addObject("backURL", "comment/conference/list.do?conferenceId=" + mes.getRelationId());
		return result;
	}
}
