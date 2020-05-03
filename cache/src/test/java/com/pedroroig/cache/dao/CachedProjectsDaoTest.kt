package com.pedroroig.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.pedroroig.cache.db.ProjectsDatabase
import com.pedroroig.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        ProjectsDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    private lateinit var dao: CachedProjectsDao

    @Before
    fun setup() {
        dao = database.cachedProjectsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getProjects_returnsData() {
        val projects = listOf(
            ProjectDataFactory.makeCachedProject(),
            ProjectDataFactory.makeCachedProject()
        )
        dao.insertProjects(projects)
        dao.getProjects().test().assertValue(projects)
    }

    @Test
    fun deleteProjects_clearsData() {
        val projects = listOf(
            ProjectDataFactory.makeCachedProject(),
            ProjectDataFactory.makeCachedProject()
        )
        dao.insertProjects(projects)
        dao.deleteProjects()
        dao.getProjects().test().assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjects_returnsData() {
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
        val projects = listOf(
            project,
            bookmarkedProject
        )
        dao.insertProjects(projects)
        dao.getBookmarkedProjects().test().assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setBookmarkedStatus() {
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
        val projects = listOf(
            project,
            bookmarkedProject
        )
        dao.insertProjects(projects)
        dao.setBookmarkStatus(false, bookmarkedProject.id)
        dao.setBookmarkStatus(true, project.id)
        val invertedList = listOf(
            project.copy(isBookmarked = true),
            bookmarkedProject.copy(isBookmarked = false)
        )
        dao.getProjects().test().assertValue(invertedList)
    }

}