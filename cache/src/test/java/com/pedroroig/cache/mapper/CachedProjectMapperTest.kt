package com.pedroroig.cache.mapper

import com.pedroroig.cache.test.factory.ProjectDataFactory
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CachedProjectMapperTest {

    private lateinit var mapper: CachedProjectMapper

    @Before
    fun setUp() {
        mapper = CachedProjectMapper()
    }

    @Test
    fun mapFromCached_mapsData() {
        val cacheProject = ProjectDataFactory.makeCachedProject()
        val dataProject = mapper.mapFromCached(cacheProject)
        assertEquals(cacheProject.id, dataProject.id)
        assertEquals(cacheProject.name, dataProject.name)
        assertEquals(cacheProject.dateCreated, dataProject.dateCreated)
        assertEquals(cacheProject.fullName, dataProject.fullName)
        assertEquals(cacheProject.isBookmarked, dataProject.isBookmarked)
        assertEquals(cacheProject.ownerAvatar, dataProject.ownerAvatar)
        assertEquals(cacheProject.ownerName, dataProject.ownerName)
        assertEquals(cacheProject.starCount, dataProject.starCount)
    }

    @Test
    fun mapToCached_mapsData() {
        val dataProject = ProjectDataFactory.makeProjectEntity()
        val cacheProject = mapper.mapToCached(dataProject)
        assertEquals(cacheProject.id, dataProject.id)
        assertEquals(cacheProject.name, dataProject.name)
        assertEquals(cacheProject.dateCreated, dataProject.dateCreated)
        assertEquals(cacheProject.fullName, dataProject.fullName)
        assertEquals(cacheProject.isBookmarked, dataProject.isBookmarked)
        assertEquals(cacheProject.ownerAvatar, dataProject.ownerAvatar)
        assertEquals(cacheProject.ownerName, dataProject.ownerName)
        assertEquals(cacheProject.starCount, dataProject.starCount)
    }
}