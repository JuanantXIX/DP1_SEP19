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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import services.ActivityService;
import services.AdministratorService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Activity;
import domain.Administrator;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActivityService			activityService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int listingType) {
		final ModelAndView res;
		Assert.isTrue(this.administratorService.checkAdministrator());
		final Collection<Conference> conferences = this.conferenceService.findAll();
		Collection<Conference> resultConferences = new ArrayList<Conference>();

		if (listingType == 0)
			resultConferences = conferences;
		else if (listingType == 1) { //conferences whose submission deadline elapsed in the last five days;
			final Date actual = new Date();
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -5);
			final Date date5daysbefore = cal.getTime();

			for (final Conference c : conferences)
				if ((c.getSubmissionDeadline().after(date5daysbefore) && c.getSubmissionDeadline().before(actual)))
					resultConferences.add(c);
		} else if (listingType == 2) { //conferences whose notification deadline elapses in less than five days;
			final Date actual = new Date();
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +5);
			final Date date5daysAfter = cal.getTime();

			for (final Conference c : conferences)
				if ((c.getNotificationDeadline().after(actual) && c.getNotificationDeadline().before(date5daysAfter)))
					resultConferences.add(c);
		} else if (listingType == 3) { //conferences whose camera-ready deadline elapses in less than five days;
			final Date actual = new Date();
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +5);
			final Date date5daysAfter = cal.getTime();

			for (final Conference c : conferences)
				if ((c.getCameraReadyDeadline().after(actual) && c.getCameraReadyDeadline().before(date5daysAfter)))
					resultConferences.add(c);
		} else if (listingType == 4) { //conferences that are going to be organised in less than five days;
			final Date actual = new Date();
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +5);
			final Date date5daysAfter = cal.getTime();

			for (final Conference c : conferences)
				if ((c.getStartDate().after(actual) && c.getStartDate().before(date5daysAfter)))
					resultConferences.add(c);
		} else {
			res = new ModelAndView("redirect: /welcome/index.do");
			return res;
		}

		res = new ModelAndView("conference/list");
		res.addObject("requestURI", "/conference/administrator/list.do");
		res.addObject("conferences", resultConferences);
		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Conference c = this.conferenceService.create();
		final Administrator actual = this.administratorService.findByPrincipal();
		c.setAdministrator(actual);
		result = this.createEditModelAndView(c);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView result;
		final Conference c = this.conferenceService.findOne(conferenceId);
		try {
			Assert.isTrue(!c.isFinalMode());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?listingType=0");
			return result;
		}
		result = this.createEditModelAndView(c);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Conference conference, final BindingResult binding) {
		ModelAndView result;
		try {
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(conference, "conference.commit.error");
				return result;
			}
			this.conferenceService.doesConferenceMatchDatesCriteria(conference);
			this.conferenceService.save(conference);

			result = new ModelAndView("redirect:/conference/administrator/list.do?listingType=0");
		} catch (final IllegalArgumentException oops) {
			result = this.createEditModelAndView(conference, "conference.dates.error");
			return result;
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.commit.error");
			return result;
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int conferenceId) {
		ModelAndView result;
		final Conference c = this.conferenceService.findOne(conferenceId);
		try {
			Assert.isTrue(!c.isFinalMode());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?listingType=0");
			return result;
		}
		final Collection<Activity> activities = this.activityService.findAllByConference(conferenceId);
		try {
			for (final Activity a : activities)
				this.activityService.delete(a);

			this.conferenceService.delete(c);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do?listingType=0");

		}
		result = new ModelAndView("redirect:list.do?listingType=0");
		return result;
	}
	protected ModelAndView createEditModelAndView(final Conference c) {
		ModelAndView result;

		result = this.createEditModelAndView(c, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Conference c, final String message) {
		ModelAndView result;

		result = new ModelAndView("conference/edit");
		result.addObject("conference", c);
		result.addObject("message", message);

		return result;
	}

}
