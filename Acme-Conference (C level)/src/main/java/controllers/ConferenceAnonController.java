
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceAnonController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	//List forthcoming conferences (Between the submissionDeadline and the notificationDeadline dates).
	@RequestMapping(value = "/listForthcoming", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAllForthcomingConferences());

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "/conference/listForthcoming.do");
		result.addObject("actualDate", new Date());

		return result;
	}

	@RequestMapping(value = "/listRunning", method = RequestMethod.GET)
	public ModelAndView list2() {
		ModelAndView result;

		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAllRunningConferences());

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "/conference/listRunning.do");
		result.addObject("actualDate", new Date());

		return result;
	}

	@RequestMapping(value = "/listPast", method = RequestMethod.GET)
	public ModelAndView list3() {
		ModelAndView result;

		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAllPastConferences());

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "/conference/listPast.do");
		result.addObject("actualDate", new Date());

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		result = new ModelAndView("conference/show");
		result.addObject("conference", conference);

		return result;
	}
}
