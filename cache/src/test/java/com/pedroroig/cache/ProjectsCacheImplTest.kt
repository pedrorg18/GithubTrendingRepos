package com.pedroroig.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.cache.mapper.CachedProjectMapper
import com.pedroroig.cache.test.factory.ProjectDataFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()
    private val cache = ProjectsCacheImpl(database, CachedProjectMapper())

    @Test
    fun clearProjects_completes() {
        cache.clearProjects().test().assertComplete()
    }

    @Test
    fun saveProjects_completes() {
        cache.saveProjects(listOf(ProjectDataFactory.makeProjectEntity())).test().assertComplete()
    }

    @Test
    fun getProjects_returnsData() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()
        cache.getProjects().test().assertValue(projects)
    }

    @Test
    fun getBookmarkedProjects_returnsData() {
        val bookmarked = ProjectDataFactory.makeBookmarkedProjectEntity()
        val nonBookmarked = ProjectDataFactory.makeNonBookmarkedProjectEntity()
        val projects = listOf(bookmarked, nonBookmarked)
        cache.saveProjects(projects).test()
        cache.getBookmarkedProjects().test().assertValue(listOf(bookmarked))
    }

    @Test
    fun setBookmarkedProjects_updatesProjects() {
        val project = ProjectDataFactory.makeNonBookmarkedProjectEntity()
        val bookmarkedProject = project.copy(isBookmarked = true)
        cache.saveProjects(listOf(project)).test()
        cache.setProjectAsBookmarked(project.id).test()
        cache.getBookmarkedProjects().test().assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setNonBookmarkedProjects_updatesProjects() {
        val project = ProjectDataFactory.makeBookmarkedProjectEntity()
        cache.saveProjects(listOf(project)).test()
        cache.setProjectAsNotBookmarked(project.id).test()
        cache.getBookmarkedProjects().test().assertValue(emptyList())
    }

    @Test
    fun areProjectsCached_returnsData() {
        val project = ProjectDataFactory.makeBookmarkedProjectEntity()
        val projects = listOf(project)
        cache.saveProjects(projects).test()
        cache.areProjectsCached().test().assertValue(true)
    }

    @Test
    fun setLastCacheTime_completes() {
        cache.setLastCacheTime(1000L).test().assertComplete()
    }

    @Test
    fun isProjectsCacheExpired_returnsNotExpired() {
        val currentTime = System.currentTimeMillis()
        cache.setLastCacheTime(currentTime).test()
        cache.isProjectsCacheExpired().test().assertValue(false)
    }

}