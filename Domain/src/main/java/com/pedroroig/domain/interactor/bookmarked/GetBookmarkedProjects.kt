package com.pedroroig.domain.interactor.bookmarked

import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.domain.interactor.ObservableUseCase
import com.pedroroig.domain.model.Project
import com.pedroroig.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetBookmarkedProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> =
        projectsRepository.getBookmarkedProjects()

}