package com.pedroroig.domain.interactor.bookmarked

import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.domain.interactor.CompletableUseCase
import com.pedroroig.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

open class BookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<String>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: String?): Completable {
        if(params == null) throw IllegalArgumentException("Params can't be null")
        return projectsRepository.bookmarkProject(params)
    }
}