package com.leedsbeckett.todo_application.ui

import android.app.Application
import androidx.lifecycle.*
import com.leedsbeckett.todo_application.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * the view model, the the link between the ui and the data
 */
class TodoViewModel (application: Application) : AndroidViewModel(application) {
    private val todoDB = TodoDatabase.getInstance(application)
    private val todoDao = todoDB.todoDao()

    val allTodos : LiveData<List<Todo>> = todoDao.getTodos()
    val activeTodos : LiveData<List<Todo>> = todoDao.getActiveTodos()

    var observedTodo : LiveData<Todo?>? = null

    fun getTodoById(id: Int) : LiveData<Todo?>  {
        return todoDao.getTodoById(id)
    }

    fun upsertTodo(todo : Todo) {
        viewModelScope.launch {
            todoDao.upsert(todo)
        }
    }

    fun deleteTodo(todo : Todo) = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            todoDao.delete(todo)
        }
    }
}