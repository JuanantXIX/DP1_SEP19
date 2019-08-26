
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PanelService;
import domain.Panel;

@Controller
@RequestMapping("/panel")
public class PanelAnonController extends AbstractController {

	@Autowired
	private PanelService	panelService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int panelId) {
		final ModelAndView result;
		final Panel panel = this.panelService.findOne(panelId);

		result = new ModelAndView("panel/show");
		result.addObject("panel", panel);
		return result;
	}

}
