
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Activity;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Tutorial;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController extends AbstractController {

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private PresentationService	presentationService;

	@Autowired
	private PanelService		panelService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference c = this.conferenceService.findOne(conferenceId);
		final List<Activity> activities = new ArrayList<Activity>(this.activityService.findAllByConference(conferenceId));
		final List<Panel> panels = new ArrayList<Panel>();
		final List<Presentation> presentations = new ArrayList<Presentation>();
		final List<Tutorial> tutorials = new ArrayList<Tutorial>();
		//Divido las conferencias en sus tres tipos: Presentation, panel y tutorial
		for (final Activity a : activities) {
			final Panel a1 = this.panelService.findOne(a.getId());
			if (a1 != null)
				panels.add(a1);
			else {
				final Presentation a2 = this.presentationService.findOne(a.getId());
				if (a2 != null)
					presentations.add(a2);
				else {
					final Tutorial a3 = this.tutorialService.findOne(a.getId());
					if (a3 != null)
						tutorials.add(a3);
				}
			}
		}
		result = new ModelAndView("activity/list");
		result.addObject("tutorials", tutorials);
		result.addObject("panels", panels);
		result.addObject("presentations", presentations);
		result.addObject("conferenceId", conferenceId);
		result.addObject("conference", c);
		result.addObject("requestURI", "/activity/administrator/list.do?conferenceId=" + conferenceId);

		return result;
	}

}
