package com.pedroroig.data.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

fun randomString() = UUID.randomUUID().toString()

fun randomBoolean() = Math.random() < 0.5

fun randomInt() = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

fun randomLong() = randomInt().toLong()
