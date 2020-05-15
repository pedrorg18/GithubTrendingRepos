package com.pedroroig.mobile_ui.browse

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.domain.model.Project
import com.pedroroig.mobile_ui.R
import com.pedroroig.mobile_ui.test.TestApplication
import com.pedroroig.mobile_ui.test.factory.ProjectDataFactory
import io.reactivex.rxjava3.core.Observable
import org.junit.Rule
import org.junit.Test

class BrowseProjectsActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule(BrowseActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubProjectsRepositoryGetProjects(Observable.just(listOf(ProjectDataFactory.makeProject())))
        activity.launchActivity(null)
    }

    @Test
    fun projectsDisplay() {
        val projects = listOf(
            ProjectDataFactory.makeProject(),
            ProjectDataFactory.makeProject(),
            ProjectDataFactory.makeProject()
        )
        stubProjectsRepositoryGetProjects(Observable.just(projects))

        activity.launchActivity(null)

        projects.forEachIndexed { index, project ->
            onView(withId(R.id.recycler_projects))
                .perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))

            onView(withId(R.id.recycler_projects))
                .check(matches(hasDescendant(withText(project.fullName))))
        }

    }

    private fun stubProjectsRepositoryGetProjects(observable: Observable<List<Project>>) {
        whenever(TestApplication.appComponent().projectsRepository().getProjects())
            .thenReturn(observable)
    }
}