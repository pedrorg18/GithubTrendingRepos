package com.pedroroig.data.mapper

import com.pedroroig.data.factory.makeProject
import com.pedroroig.data.factory.makeProjectEntity
import com.pedroroig.data.model.ProjectEntity
import com.pedroroig.domain.model.Project
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ProjectMapperTest {

    private lateinit var mapper: ProjectMapper

    @Before
    fun setUp() {
        mapper = ProjectMapper()
    }

    @Test
    fun mapFromEntity_mapsData() {
        val dataProject = makeProjectEntity()
        val domainProject = mapper.mapFromEntity(dataProject)

        assertEqualData(dataProject, domainProject)
    }

    @Test
    fun mapToEntity() {
        val domainProject = makeProject()
        val dataProject = mapper.mapToEntity(domainProject)

        assertEqualData(dataProject, domainProject)
    }


    private fun assertEqualData(dataProject: ProjectEntity, domainProject: Project) {
        assertEquals(dataProject.id, domainProject.id)
        assertEquals(dataProject.fullName, domainProject.fullName)
        assertEquals(dataProject.name, domainProject.name)
        assertEquals(dataProject.ownerName, domainProject.ownerName)

    }

}