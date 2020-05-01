package com.pedroroig.data.store

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class ProjectsDataStoreFactoryTest {

    @Mock lateinit var projectsCacheDataStore: ProjectsCacheDataStore
    @Mock lateinit var projectsRemoteDataStore: ProjectsRemoteDataStore
    private lateinit var factory: ProjectsDataStoreFactory

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        factory = ProjectsDataStoreFactory(projectsCacheDataStore,
            projectsRemoteDataStore)
    }

    @Test
    fun getDataStoreReturnsRemote_whenCacheExpired() {
        assertEquals(projectsRemoteDataStore,
            factory.getDataStore(true, true))
    }

    @Test
    fun getDataStoreReturnsRemote_whenNoCache() {
        assertEquals(projectsRemoteDataStore,
            factory.getDataStore(false, false))
    }

    @Test
    fun getDataStoreReturnsCache() {
        assertEquals(projectsCacheDataStore,
            factory.getDataStore(true, false))
    }

    @Test
    fun getCacheDataStore_returnsCache() {
        assertEquals(projectsCacheDataStore, factory.getCacheDataStore())
    }

}