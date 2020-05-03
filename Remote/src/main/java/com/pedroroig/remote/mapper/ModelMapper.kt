package com.pedroroig.remote.mapper

interface ModelMapper<M, E> {

    fun mapFromModel(model: M): E

}