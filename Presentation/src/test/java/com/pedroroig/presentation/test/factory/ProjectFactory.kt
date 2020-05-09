package com.pedroroig.presentation.test.factory

import com.pedroroig.domain.model.Project
import com.pedroroig.presentation.model.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomBoolean())
    }

    fun makeProject(): Project {
        return Project(DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomBoolean())
    }

    fun makeProjectViewList(count: Int) =
        mutableListOf<ProjectView>().apply {
            repeat(count) {
                add(makeProjectView())
            }
        }

    fun makeProjectList(count: Int) =
        mutableListOf<Project>().apply {
            repeat(count) {
                add(makeProject())
            }
        }


}