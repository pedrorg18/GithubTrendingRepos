package com.pedroroig.mobile_ui.bookmarked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedroroig.mobile_ui.R
import com.pedroroig.mobile_ui.browse.BrowseActivity
import com.pedroroig.mobile_ui.injection.ViewModelFactory
import com.pedroroig.presentation.BrowseBookmarkedProjectsViewModel
import com.pedroroig.presentation.model.ProjectView
import com.pedroroig.presentation.state.Resource
import com.pedroroig.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import javax.inject.Inject

class BookmarkedActivity : AppCompatActivity() {

    @Inject lateinit var adapter: BookmarkedAdapter
//    @Inject lateinit var mapper: ViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var browseViewModel: BrowseBookmarkedProjectsViewModel

    companion object {
        fun getStartIntent(ctx: Context) =
            Intent(ctx, BrowseActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_bookmarked)
        AndroidInjection.inject(this)

        browseViewModel = ViewModelProvider(this, viewModelFactory)
            .get(BrowseBookmarkedProjectsViewModel::class.java)
        setupBookmarkedRecycler()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getProjects().observe(this, Observer {
            it?.let {
                handleDataState(it)
            }
        })
        browseViewModel.fetchProjects()
    }

    private fun setupBookmarkedRecycler() {
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when(resource.status) {
            ResourceState.Loading -> {
                progress.visibility = VISIBLE
                recycler_projects.visibility = GONE
            }
            ResourceState.Success -> {
                progress.visibility = GONE
                recycler_projects.visibility = VISIBLE
                setupScreenForSuccess(resource.data)
            }
            ResourceState.Error -> {
                progress.visibility = GONE
            }

        }
    }

    private fun setupScreenForSuccess(projects: List<ProjectView>?) {
        projects?.let {
            adapter.projects = it
            adapter.notifyDataSetChanged()
        }
            ?: run {
                // manage error
            }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}