package com.corradodev.mvvm.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.corradodev.mvvm.R
import com.corradodev.mvvm.data.Task
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_task_list.*
import javax.inject.Inject


class TaskListActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var tasksViewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasksViewModel = ViewModelProviders.of(this, viewModelFactory).get(TasksViewModel::class.java)
        setContentView(R.layout.activity_task_list)

        recycler_view.layoutManager = LinearLayoutManager(this)

        tasksViewModel.getTasks().observe(this, Observer<List<Task>> { it ->
            it?.let {
                recycler_view.adapter = TaskAdapter(it) {
                    startActivity(taskEditIntent(it))
                }
            }
        })

        fab_add.setOnClickListener {
            startActivity(taskEditIntent(null))
        }
    }
}
