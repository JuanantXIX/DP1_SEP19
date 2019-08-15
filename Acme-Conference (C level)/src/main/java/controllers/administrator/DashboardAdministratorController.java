/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import repositories.AdministratorRepository;
import services.AdministratorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AdministratorRepository	administratorRepository;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res;
		Assert.isTrue(this.administratorService.checkAdministrator());

		final Integer maxd1 = this.administratorRepository.maxSubmissionsPerConference();
		final Integer mind1 = this.administratorRepository.minSubmissionsPerConference();
		final Double avgd1 = this.administratorRepository.avgSubmissionsPerConference();
		final Double stddevd1 = this.administratorRepository.stddevSubmissionsPerConference();

		final Integer maxd2 = this.administratorRepository.maxRegistrationsPerConference();
		final Integer mind2 = this.administratorRepository.minRegistrationsPerConference();
		final Double avgd2 = this.administratorRepository.avgRegistrationsPerConference();
		final Double stddevd2 = this.administratorRepository.stddevRegistrationsPerConference();

		final Integer maxd3 = this.administratorRepository.maxConferenceFees();
		final Integer mind3 = this.administratorRepository.minConferenceFees();
		final Double avgd3 = this.administratorRepository.avgConferenceFees();
		final Double stddevd3 = this.administratorRepository.stddevConferenceFees();

		final Integer maxd4 = this.administratorRepository.maxDaysPerConference();
		final Integer mind4 = this.administratorRepository.minDaysPerConference();
		final Double avgd4 = this.administratorRepository.avgDaysPerConference();
		final Double stddevd4 = this.administratorRepository.stddevDaysPerConference();

		res = new ModelAndView("administrator/dashboard");
		res.addObject("max1", maxd1);
		res.addObject("max2", maxd2);
		res.addObject("max3", maxd3);
		res.addObject("max4", maxd4);

		res.addObject("min1", mind1);
		res.addObject("min2", mind2);
		res.addObject("min3", mind3);
		res.addObject("min4", mind4);

		res.addObject("avg1", avgd1);
		res.addObject("avg2", avgd2);
		res.addObject("avg3", avgd3);
		res.addObject("avg4", avgd4);

		res.addObject("stddev1", stddevd1);
		res.addObject("stddev2", stddevd2);
		res.addObject("stddev3", stddevd3);
		res.addObject("stddev4", stddevd4);
		return res;
	}

}
