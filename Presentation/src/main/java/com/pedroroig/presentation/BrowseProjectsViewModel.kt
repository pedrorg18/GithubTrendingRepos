package com.pedroroig.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroroig.domain.interactor.bookmark.BookmarkProject
import com.pedroroig.domain.interactor.bookmark.UnBookmarkProject
import com.pedroroig.domain.interactor.browse.GetProjects
import com.pedroroig.domain.model.Project
import com.pedroroig.presentation.mapper.ViewMapper
import com.pedroroig.presentation.model.ProjectView
import com.pedroroig.presentation.state.Resource
import com.pedroroig.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects,
    private val bookmarkProject: BookmarkProject,
    private val unBookmarkProject: UnBookmarkProject,
    private val mapper: ViewMapper
): ViewModel() {

    private val liveData = MutableLiveData<Resource<List<ProjectView>>>()

    fun getProjects(): LiveData<Resource<List<ProjectView>>> =
        liveData

    fun fetchProjects() {
        liveData.value =
            Resource(ResourceState.Loading, null, null)
        getProjects.execute(ProjectsSubscriber())
    }

    fun bookmarkProject(projectId: String) {
        liveData.value =
            Resource(ResourceState.Loading, null, null)
        return bookmarkProject.execute(BookmarkProjectSubscriber(), projectId)
    }

    fun unBookmarkProject(projectId: String) {
        liveData.value =
            Resource(ResourceState.Loading, null, null)
        return unBookmarkProject.execute(BookmarkProjectSubscriber(), projectId)
    }

    override fun onCleared() {
        getProjects.dispose()
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

    inner class BookmarkProjectSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.value = Resource(
                ResourceState.Success,
                liveData.value?.data,
                null
            )
        }

        override fun onError(e: Throwable) {
            liveData.value = Resource(
                ResourceState.Error,
                liveData.value?.data,
                e.localizedMessage
            )
        }
    }
}