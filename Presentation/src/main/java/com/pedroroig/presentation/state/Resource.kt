package com.pedroroig.presentation.state

class Resource<out T>(
    val status: ResourceState,
    val data: T?,
    val message: String?) {
}