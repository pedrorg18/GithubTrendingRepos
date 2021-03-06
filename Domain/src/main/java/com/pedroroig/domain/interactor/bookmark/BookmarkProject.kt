package com.pedroroig.domain.interactor.bookmark

import com.pedroroig.domain.executor.PostExecutionThread
import com.pedroroig.domain.interactor.CompletableUseCase
import com.pedroroig.domain.repository.ProjectsRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

open class BookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<String>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: String?): Completable {
        if(params == null) throw IllegalArgumentException("Params can't be null")
        return projectsRepository.bookmarkProject(params)
    }
}