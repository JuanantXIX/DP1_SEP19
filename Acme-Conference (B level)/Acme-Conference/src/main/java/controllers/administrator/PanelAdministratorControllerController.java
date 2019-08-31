
package controllers.administrator;

import java.util.Calendar;

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
import services.PanelService;
import controllers.AbstractController;
import domain.Conference;
import domain.Panel;

@Controller
@RequestMapping("/panel/administrator")
public class PanelAdministratorControllerController extends AbstractController {

	@Autowired
	private PanelService			panelService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Panel panel = this.panelService.create(conferenceId);

		result = this.createEditModelAndView(panel);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit2(@RequestParam final int panelId) {
		this.administratorService.checkAdministrator();
		final ModelAndView result;
		final Panel panel = this.panelService.findOne(panelId);

		result = this.createEditModelAndView(panel);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int panelId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Panel panel = this.panelService.findOne(panelId);

		result = new ModelAndView("panel/show");
		result.addObject("panel", panel);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int panelId) {
		final ModelAndView result;
		this.administratorService.checkAdministrator();
		final Panel panel = this.panelService.findOne(panelId);
		this.panelService.delete(panel);

		result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + panel.getConference().getId());
		result.addObject("panel", panel);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Panel panel, final BindingResult binding) {
		ModelAndView res;

		try {
			this.administratorService.checkAdministrator();
			if (binding.hasErrors()) {
				res = this.createEditModelAndView(panel, "panel.commit.error");
				return res;
			}
			//Calculo endDate a partir de startDate y duration:
			final Calendar cal = Calendar.getInstance();
			cal.setTime(panel.getStartDate());

			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + panel.getDuration());

			panel.setEndDate(cal.getTime());

			//Fin del cálculo
			final Conference c = panel.getConference();
			Assert.isTrue(!c.getStartDate().after(panel.getStartDate()), "wrong start");
			Assert.isTrue(!c.getEndDate().before(panel.getEndDate()), "wrong end");
			Assert.isTrue(panel.getStartDate().before(panel.getEndDate()), "wrong dates");
			Assert.notNull(panel.getSpeakers(), "invalid speakers");
			Assert.isTrue(!panel.getSpeakers().isEmpty(), "invalid speakers");

			this.panelService.save(panel);
			res = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + panel.getConference().getId());
			return res;
		} catch (final Throwable oops) {
			if (oops.getMessage() == "invalid speakers")
				res = this.createEditModelAndView(panel, "activity.speakers.error");
			else if (oops.getMessage() == "wrong dates")
				res = this.createEditModelAndView(panel, "activity.dates.error");
			else if (oops.getMessage() == "wrong start")
				res = this.createEditModelAndView(panel, "activity.start.error");
			else if (oops.getMessage() == "wrong end")
				res = this.createEditModelAndView(panel, "activity.end.error");
			else
				res = this.createEditModelAndView(panel, "panel.commit.error");
		}

		return res;
	}

	protected ModelAndView createEditModelAndView(final Panel panel) {
		ModelAndView result;

		result = this.createEditModelAndView(panel, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Panel panel, final String message) {
		ModelAndView result;

		result = new ModelAndView("panel/edit");
		result.addObject("panel", panel);
		result.addObject("message", message);

		return result;
	}

}
