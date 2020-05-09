package com.pedroroig.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroroig.domain.interactor.bookmark.BookmarkProject
import com.pedroroig.domain.interactor.bookmark.GetBookmarkedProjects
import com.pedroroig.domain.interactor.bookmark.UnBookmarkProject
import com.pedroroig.domain.model.Project
import com.pedroroig.presentation.mapper.ViewMapper
import com.pedroroig.presentation.model.ProjectView
import com.pedroroig.presentation.state.Resource
import com.pedroroig.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects,
    private val mapper: ViewMapper
): ViewModel() {

    private val liveData = MutableLiveData<Resource<List<ProjectView>>>()

    fun getProjects(): LiveData<Resource<List<ProjectView>>> =
        liveData

    fun fetchProjects() {
        liveData.value =
            Resource(ResourceState.Loading, null, null)
        getBookmarkedProjects.execute(ProjectsSubscriber())
    }

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    inner class ProjectsSubscriber: DisposableObserver<List<Project>>() {
        override fun onNext(t: List<Project>) {
            liveData.value = Resource(
                ResourceState.Success,
                t.map {
                    mapper.mapToView(it)
                },
                null
            )
        }

        override fun onError(e: Throwable) {
            liveData.value = Resource(
                ResourceState.Error,
                null,
                e.localizedMessage
            )
        }

        override fun onComplete() { }
    }

}