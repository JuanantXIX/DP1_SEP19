
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import domain.Conference;

@Controller
@RequestMapping("/search")
public class SearchAnonController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	//List forthcoming conferences (Between the submissionDeadline and the notificationDeadline dates).

	@RequestMapping(value = "/search", method = RequestMethod.GET, params = "search")
	public ModelAndView search(@RequestParam("keyword") final String keyword, final HttpServletRequest request, final HttpServletResponse response) {
		final ModelAndView res = new ModelAndView("search/results");
		final Collection<Conference> conferences = this.conferenceService.searchConferences(keyword);
		res.addObject("conferences", conferences);
		res.addObject("requestURI", "/search/search.do");
		return res;
	}
}
