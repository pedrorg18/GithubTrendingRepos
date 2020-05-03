package com.pedroroig.remote.service

import com.pedroroig.remote.model.ProjectsResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubTrendingService {

    @GET("search/repositorires")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Observable<ProjectsResponseModel>


}