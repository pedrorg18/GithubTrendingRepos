package com.pedroroig.domain.executor

import io.reactivex.Scheduler

interface PostExecutorThread {
    val scheduler: Scheduler
}