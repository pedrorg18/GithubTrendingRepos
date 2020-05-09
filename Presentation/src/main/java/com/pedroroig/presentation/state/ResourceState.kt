package com.pedroroig.presentation.state

sealed class ResourceState {
    object Loading : ResourceState()
    object Success : ResourceState()
    object Error : ResourceState()
}