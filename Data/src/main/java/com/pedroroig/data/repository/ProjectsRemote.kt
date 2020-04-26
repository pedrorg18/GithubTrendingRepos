package com.pedroroig.data.repository

import com.pedroroig.data.model.ProjectEntity
import io.reactivex.Observable

interface ProjectsRemote {
    fun getProjects(): Observable<List<ProjectEntity>>
}