
package controllers.administrator;

import java.util.Collection;

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

import services.AdministratorService;
import services.TopicService;
import controllers.AbstractController;
import domain.Topic;

@Controller
@RequestMapping("/topic/administrator")
public class TopicAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TopicService			topicService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Topic> topics = this.topicService.findAll();
		result = new ModelAndView("topic/list");
		result.addObject("topics", topics);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit() {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Topic topic = this.topicService.create();
		result = this.createEditModelAndView(topic);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final int topicId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Topic topic = this.topicService.findOne(topicId);
		result = this.createEditModelAndView(topic);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int topicId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Topic topic = this.topicService.findOne(topicId);

		result = new ModelAndView("topic/show");
		result.addObject("topic", topic);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int topicId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Topic topic = this.topicService.findOne(topicId);
		try {
			this.topicService.delete(topic);

		} catch (final Throwable oops) {
			final String message = oops.getMessage();
			if (message.contains("ConstraintViolationException")) {
				final Collection<Topic> topics = this.topicService.findAll();
				result = new ModelAndView("topic/list");
				result.addObject("topics", topics);
				result.addObject("message", "topic.delete.error.messages");
				return result;
			}
		}

		result = new ModelAndView("redirect:/topic/administrator/list.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Topic topic, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			Assert.isTrue(topic.getName().size() == 2, "Wrong size");
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(topic, "topic.commit.error");
				return res;
			}
			this.topicService.save(topic);
			res = new ModelAndView("redirect:/topic/administrator/list.do");
			return res;
		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong size")
				res = this.createEditModelAndView(topic, "topic.size.error");
			else
				res = this.createEditModelAndView(topic, "topic.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Topic topic) {
		ModelAndView result;

		result = this.createEditModelAndView(topic, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Topic topic, final String message) {
		ModelAndView result;

		result = new ModelAndView("topic/edit");
		result.addObject("topic", topic);
		result.addObject("message", message);

		return result;
	}

}
