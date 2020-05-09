package com.pedroroig.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.pedroroig.domain.interactor.bookmark.GetBookmarkedProjects
import com.pedroroig.domain.model.Project
import com.pedroroig.presentation.mapper.ViewMapper
import com.pedroroig.presentation.model.ProjectView
import com.pedroroig.presentation.state.ResourceState
import com.pedroroig.presentation.test.factory.DataFactory
import com.pedroroig.presentation.test.factory.ProjectFactory
import io.reactivex.observers.DisposableObserver
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor

class BrowseBookmarkedProjectsViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockGetBookmarkedProjects = mock<GetBookmarkedProjects>()
    private val mockMapper = mock<ViewMapper>()

    private val viewModel = BrowseBookmarkedProjectsViewModel(
        mockGetBookmarkedProjects,
        mockMapper
    )

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjects_executesUseCase() {
        viewModel.fetchProjects()
        verify(mockGetBookmarkedProjects, times(1)).execute(any(), eq(null))
    }

    @Test
    fun fetchProjects_returnsSuccess() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubMapperLists(projectViews, projects)

        viewModel.fetchProjects()

        verify(mockGetBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(ResourceState.Success, viewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjects_returnsData() {
        val projects = ProjectFactory.makeProjectList(2)
        val projectViews = ProjectFactory.makeProjectViewList(2)
        stubMapperLists(projectViews, projects)

        viewModel.fetchProjects()

        verify(mockGetBookmarkedProjects).execute(captor.capture(), eq(null))

        captor.firstValue.onNext(projects)

        assertEquals(projectViews, viewModel.getProjects().value?.data)
    }

    @Test
    fun fetchProjects_returnsError() {
        viewModel.fetchProjects()

        verify(mockGetBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.Error, viewModel.getProjects().value?.status)
    }

    @Test
    fun fetchProjects_returnsMessageForError() {
        val errorMsg = DataFactory.randomUuid()

        viewModel.fetchProjects()

        verify(mockGetBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(
            RuntimeException(errorMsg))

        assertEquals(errorMsg, viewModel.getProjects().value?.message)
    }



    private fun stubProjectMapperMapToView(projectView: ProjectView, project: Project) {
        whenever(mockMapper.mapToView(project)).thenReturn(projectView)
    }

    private fun stubMapperLists(
        projectViews: MutableList<ProjectView>,
        projects: MutableList<Project>
    ) {
        projects.forEachIndexed { index, project ->
            stubProjectMapperMapToView(projectViews[index], project)
        }
    }

}