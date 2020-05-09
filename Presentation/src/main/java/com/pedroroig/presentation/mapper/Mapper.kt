package com.pedroroig.presentation.mapper

interface Mapper<out V, in D> {
    fun mapToView(domainModel: D): V
}