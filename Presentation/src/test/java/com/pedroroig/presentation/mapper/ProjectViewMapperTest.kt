package com.pedroroig.presentation.mapper

import com.pedroroig.presentation.test.factory.ProjectFactory.makeProject
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectViewMapperTest{

    @Test
    fun mapToView_mapsData() {
        val project = makeProject()
        val projectView = ViewMapper().mapToView(project)

        assertEquals(project.id, projectView.id)
        assertEquals(project.name, projectView.name)
        assertEquals(project.fullName, projectView.fullName)
        assertEquals(project.starCount, projectView.starCount)
        assertEquals(project.dateCreated, projectView.dateCreated)
        assertEquals(project.ownerName, projectView.ownerName)
        assertEquals(project.ownerAvatar, projectView.ownerAvatar)
        assertEquals(project.isBookmarked, projectView.isBookmarked)
    }
}