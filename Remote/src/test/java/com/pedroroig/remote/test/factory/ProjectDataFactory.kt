package com.pedroroig.remote.test.factory

import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.remote.model.OwnerModel
import com.pedroroig.remote.model.ProjectModel
import com.pedroroig.remote.model.ProjectsResponseModel

object ProjectDataFactory {

    fun makeOwner(): OwnerModel {
        return OwnerModel(DataFactory.randomUuid(), DataFactory.randomUuid())
    }

    fun makeProject(): ProjectModel {
        return ProjectModel(DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomInt(),
            DataFactory.randomUuid(), makeOwner())
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid(), DataFactory.randomUuid(),
            DataFactory.randomUuid())
    }

    fun makeProjectsResponse(): ProjectsResponseModel {
        return ProjectsResponseModel(listOf(makeProject(), makeProject()))
    }

}