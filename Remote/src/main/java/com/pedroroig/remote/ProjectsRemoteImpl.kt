package com.pedroroig.remote

import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.data.repository.ProjectsRemote
import com.pedroroig.remote.mapper.ProjectsResponseModelMapper
import com.pedroroig.remote.service.GithubTrendingService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
    private val service: GithubTrendingService,
    private val mapper: ProjectsResponseModelMapper
) : ProjectsRemote {

    override fun getProjects(): Observable<List<ProjectEntity>> =
        service.searchRepositories("language:kotlin", "stars", "desc")
            .map { response ->
                response.items.map {
                    mapper.mapFromModel(it)
                }
            }
}