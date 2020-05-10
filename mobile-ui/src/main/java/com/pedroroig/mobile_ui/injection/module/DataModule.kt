package com.pedroroig.mobile_ui.injection.module

import com.pedroroig.data.ProjectsDataRepository
import com.pedroroig.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository


}