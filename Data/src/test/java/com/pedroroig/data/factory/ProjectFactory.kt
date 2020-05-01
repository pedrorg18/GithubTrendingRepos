package com.pedroroig.data.factory

import com.pedroroig.data.mapper.ProjectMapper
import com.pedroroig.data.model.ProjectEntity


fun makeProjectEntity() =
    ProjectEntity(
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomBoolean()
    )

fun makeProject() = ProjectMapper().mapFromEntity(makeProjectEntity())
