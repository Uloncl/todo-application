package com.leedsbeckett.todo_application.ui

import android.app.UiModeManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.recyclerview.widget.RecyclerView
import com.leedsbeckett.todo_application.R
import com.leedsbeckett.todo_application.TodoActivity
import com.leedsbeckett.todo_application.data.Todo
import com.leedsbeckett.todo_application.databinding.TodoItemCardBinding

/**
 * adapter for the recycler view, sets the correct data for each of the cards to be added to the
 * recycler view
 */
class TodoListAdapter(var todos : List<Todo>, listener : OnTodoItemClickListener) : RecyclerView.Adapter<TodoListAdapter.TodoHolder>() {
    private var mListener : OnTodoItemClickListener = listener
    private lateinit var uiModeManager : UiModeManager

    inner class TodoHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TodoItemCardBinding.bind(view)
        val title : TextView = binding.todoTitle
        val body : TextView = binding.todoBody
    }
    interface OnTodoItemClickListener {
        fun onTodoItemClicked(item: Todo)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        uiModeManager = parent.context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val itemview = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_item_card,
            parent,
            false
        )
        return TodoHolder(itemview)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        val currTodo = todos[position]
        holder.title.text = currTodo.title
        currTodo.body?.let { holder.body.text = it }
        holder.itemView.setOnClickListener {
            mListener.onTodoItemClicked(currTodo)
        }
    }

    override fun getItemCount() = todos.size
}