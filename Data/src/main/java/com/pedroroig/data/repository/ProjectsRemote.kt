package com.pedroroig.data.repository

import com.pedroroig.data.model.ProjectEntity
import io.reactivex.rxjava3.core.Observable

interface ProjectsRemote {
    fun getProjects(): Observable<List<ProjectEntity>>
}