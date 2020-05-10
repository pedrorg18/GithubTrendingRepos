package com.pedroroig.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.remote.mapper.ProjectsResponseModelMapper
import com.pedroroig.remote.model.ProjectModel
import com.pedroroig.remote.model.ProjectsResponseModel
import com.pedroroig.remote.service.GithubTrendingService
import com.pedroroig.remote.test.factory.ProjectDataFactory
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

import org.mockito.ArgumentMatchers.anyString

class ProjectsRemoteImplTest {

    private val mockService = mock<GithubTrendingService>()
    private val mockMapper = mock<ProjectsResponseModelMapper>()
    private val remote = ProjectsRemoteImpl(mockService, mockMapper)

    @Before
    fun setUp() {
    }

    @Test
    fun getProjects_completes() {
        stubServiceGetProjects(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        remote.getProjects().test().assertComplete()
    }

    @Test
    fun getProjects_callsServer() {
        stubServiceGetProjects(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        remote.getProjects().test()
        verify(mockService).searchRepositories(anyString(), anyString(), anyString())
    }

    @Test
    fun getProjects_returnsData() {
        val response = ProjectDataFactory.makeProjectsResponse()
        stubServiceGetProjects(Observable.just(response))
        val entities = response.items.map { model ->
            ProjectDataFactory.makeProjectEntity()
                .also { entity ->
                    stubMapper(model, entity)
                }
        }
        remote.getProjects().test().assertValue(entities)
    }

    @Test
    fun getProjects_callsServerWithCorrectParameters() {
        stubServiceGetProjects(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        remote.getProjects().test()
        verify(mockService).searchRepositories("language:kotlin", "stars", "desc")
    }


    private fun stubMapper(model: ProjectModel, projectEntity: ProjectEntity) {
        whenever(mockMapper.mapFromModel(model))
            .thenReturn(projectEntity)
    }

    private fun stubServiceGetProjects(observable: Observable<ProjectsResponseModel>) {
        whenever(mockService.searchRepositories(anyString(), anyString(), anyString()))
            .thenReturn(observable)
    }
}