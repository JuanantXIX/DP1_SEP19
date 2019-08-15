
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Panel;
import domain.Presentation;
import domain.Tutorial;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository	activityRepository;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private PanelService		panelService;

	@Autowired
	private PresentationService	presentationService;


	public Collection<Activity> findAll() {
		return this.activityRepository.findAll();
	}

	public Collection<Activity> findAllByConference(final int conferenceId) {
		return this.activityRepository.findAllByConference(conferenceId);
	}

	public Activity findOne(final int activityId) {
		return this.activityRepository.findOne(activityId);
	}

	public Activity save(final Activity activity) {
		return this.activityRepository.save(activity);
	}

	public void delete(final Activity activity) {
		try {
			final Tutorial t = this.tutorialService.findOne(activity.getId());
			if (t == null) {
				final Panel p = this.panelService.findOne(activity.getId());
				if (p == null) {
					final Presentation pre = this.presentationService.findOne(activity.getId());
					if (pre == null)
						throw new IllegalArgumentException();
					else
						this.presentationService.delete(pre);

				} else
					this.panelService.delete(p);
			} else
				this.tutorialService.delete(t);

		} catch (final Throwable oops) {

		}
	}
}
