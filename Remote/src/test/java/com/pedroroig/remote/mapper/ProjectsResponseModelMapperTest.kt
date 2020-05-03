package com.pedroroig.remote.mapper

import com.pedroroig.remote.test.factory.ProjectDataFactory
import org.junit.Test
import kotlin.test.assertEquals

class ProjectsResponseModelMapperTest {

    @Test
    fun mapFromModel_mapsData() {
        val mapper = ProjectsResponseModelMapper()
        val remoteModel = ProjectDataFactory.makeProject()
        val dataModel = mapper.mapFromModel(remoteModel)

        assertEquals(remoteModel.id, dataModel.id)
        assertEquals(remoteModel.name, dataModel.name)
        assertEquals(remoteModel.fullName, dataModel.fullName)
        assertEquals(remoteModel.dateCreated, dataModel.dateCreated)
        assertEquals(remoteModel.owner.ownerName, dataModel.ownerName)
        assertEquals(remoteModel.owner.ownerAvatar, dataModel.ownerAvatar)
        assertEquals(remoteModel.starCount.toString(), dataModel.starCount)
    }
}