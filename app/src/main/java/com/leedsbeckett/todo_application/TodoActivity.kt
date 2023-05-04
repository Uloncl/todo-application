package com.leedsbeckett.todo_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.leedsbeckett.todo_application.data.Todo
import com.leedsbeckett.todo_application.databinding.TodoActivityBinding
import com.leedsbeckett.todo_application.ui.AddTodoDialog
import com.leedsbeckett.todo_application.ui.TodoDetailDialog
import com.leedsbeckett.todo_application.ui.TodoListAdapter
import com.leedsbeckett.todo_application.ui.TodoViewModel

class TodoActivity : AppCompatActivity(), TodoListAdapter.OnTodoItemClickListener {
    companion object {
        const val TAG = "TodoActivity"
    }

    private lateinit var binding : TodoActivityBinding

    private lateinit var viewModel : TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        binding = TodoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        val adapter = TodoListAdapter(emptyList(), this)
        binding.todosRecyclerView.adapter = adapter

        viewModel.activeTodos.observe(this) {
            adapter.todos = it
            adapter.notifyDataSetChanged()
        }

        val addTodoFab : FloatingActionButton = binding.addTodoFAB
        addTodoFab.setOnClickListener {
            val dialog = AddTodoDialog(null)
            dialog.show(supportFragmentManager, "AddTodoDialog")
        }
    }

    override fun onTodoItemClicked(item: Todo) {
        val detailDialog = TodoDetailDialog(item)
        detailDialog.show(supportFragmentManager, "TodoDetailDialog")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }
}