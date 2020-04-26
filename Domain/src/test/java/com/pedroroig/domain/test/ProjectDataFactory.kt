package com.pedroroig.domain.test

import com.pedroroig.domain.model.Project
import java.util.*

fun randomUuId() = UUID.randomUUID().toString()

fun randomBoolean() = Math.random() < 0.5

fun makeProject() =
    Project(
        randomUuId(),
        randomUuId(),
        randomUuId(),
        randomUuId(),
        randomUuId(),
        randomUuId(),
        randomUuId(),
        randomBoolean()
    )

fun makeProjectList(count: Int) =
    mutableListOf<Project>().apply {
        repeat(count) {
            add(makeProject())
        }
    }