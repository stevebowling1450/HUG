package com.interapt.togglit.injection.component;

import android.content.Context;

import com.interapt.togglit.injection.module.ActivityModule;
import com.interapt.togglit.injection.module.SchedulerModule;
import com.interapt.togglit.injection.qualifier.ActivityContext;
import com.interapt.togglit.injection.scope.PerActivity;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.events.EventsActivity;
import com.interapt.togglit.ui.events.eventsCalendar.EventsCalendarActivity;
import com.interapt.togglit.ui.events.eventsDetail.EventDetailActivity;
import com.interapt.togglit.ui.filter.FilterActivity;
import com.interapt.togglit.ui.homeScreenPods.HomeScreenPodsActivity;
import com.interapt.togglit.ui.lessonDetail.LessonDetailActivity;
import com.interapt.togglit.ui.lessonList.LessonListActivity;
import com.interapt.togglit.ui.progressReport.ProgressReportActivity;
import com.interapt.togglit.ui.progressReport.acumen.AcumenFragment;
import com.interapt.togglit.ui.progressReport.lesson.LessonFragment;
import com.interapt.togglit.ui.progressReport.lesson.lessonList.LessonListFragment;
import com.interapt.togglit.ui.progressReport.lesson.lessonResult.LessonResultFragment;
import com.interapt.togglit.ui.progressReport.overview.OverviewFragment;
import com.interapt.togglit.ui.schoolDirectory.SchoolDirectoryActivity;
import com.interapt.togglit.ui.schoolProfile.SchoolProfileActivity;
import com.interapt.togglit.ui.settings.SettingsActivity;
import com.interapt.togglit.ui.studentDirectory.StudentDirectoryActivity;
import com.interapt.togglit.ui.studentProfile.StudentProfileActivity;
import com.interapt.togglit.ui.updateProgress.UpdateProgressActivity;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryActivity;
import com.interapt.togglit.ui.volunteerProfile.VolunteerProfileActivity;

import dagger.Component;

/**
 * Created by miller.barrera on 14/10/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SchedulerModule.class})
public interface ActivityComponent {

    //Inject all activities

    void inject(BaseActivity baseActivity);

    void inject(HomeScreenPodsActivity homeScreenPodsActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(EventsActivity eventsActivity);

    void inject(EventDetailActivity eventDetailActivity);

    void inject(EventsCalendarActivity eventsCalendarActivity);

    void inject(VolunteerDirectoryActivity volunteerDirectoryActivity);

    void inject(StudentDirectoryActivity studentDirectoryActivity);

    void inject(LessonListActivity lessonListActivity);

    void inject(VolunteerProfileActivity volunteerProfileActivity);

    void inject(StudentProfileActivity studentProfileActivity);

    void inject(SchoolDirectoryActivity schoolDirectoryActivity);

    void inject(ProgressReportActivity progressReportActivity);

    void inject(LessonListFragment lessonListFragment);

    void inject(LessonResultFragment lessonResultFragment);

    void inject(SchoolProfileActivity schoolProfileActivity);

    void inject(UpdateProgressActivity updateProgressActivity);

    void inject(FilterActivity filterActivity);


    @ActivityContext
    Context context();

    void inject(LessonFragment lessonFragment);

    void inject(AcumenFragment acumenFragment);

    void inject(OverviewFragment overviewFragment);

    void inject(LessonDetailActivity lessonDetailActivity);
}
