package com.leedsbeckett.todo_application.ui

import android.app.Dialog
import android.app.UiModeManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.leedsbeckett.todo_application.data.Todo
import com.leedsbeckett.todo_application.databinding.TodoDetailBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

class TodoDetailDialog(val todo: Todo) : DialogFragment() {
    private lateinit var titleView : TextView
    private lateinit var bodyView : TextView
    private lateinit var closeBtn : Button
    private lateinit var editBtn : Button
    private lateinit var deleteBtn : Button
    private lateinit var vm : TodoViewModel
    private lateinit var detailDialog : Dialog

    override fun onStart() {
        super.onStart()
        detailDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        detailDialog = Dialog(requireContext())
        val uiModeManager = context?.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val binding = TodoDetailBinding.inflate(layoutInflater)
        detailDialog.setContentView(binding.root)
        detailDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        titleView = binding.todoTitle
        bodyView = binding.todoBody
        closeBtn = binding.closeDialogBtn
        editBtn = binding.editTodoBtn
        deleteBtn = binding.deleteTodoBtn
        vm = ViewModelProvider(requireActivity())[TodoViewModel::class.java]

        vm.viewModelScope.launch {
            var obTodo = vm.getTodoById(todo.id)

            obTodo?.let { it.observe(this@TodoDetailDialog) {
                    if (it != null) {
                        it?.let {
                            titleView.text = it.title
                            bodyView.text = it.body
                        }
                    } else {
                        titleView.text = todo.title
                        bodyView.text = todo.body
                    }
                }
            }
        }

        closeBtn.setOnClickListener {
            dismiss()
        }

        editBtn.setOnClickListener {
            detailDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val addTodoDialog = AddTodoDialog(todo)
            addTodoDialog.show(requireActivity().supportFragmentManager, "AddTodoDialog")
            vm.allTodos.value?.first { it.id == todo.id }?.let {
                titleView.text = it.title
                bodyView.text = it.body
            }
        }

        deleteBtn.setOnClickListener {
            vm.upsertTodo(Todo(
                id = todo.id,
                title = todo.title,
                body = todo.body,
                completed = todo.completed,
                created = todo.created,
                updated = todo.updated,
                timeout = todo.timeout,
                deleted = Calendar.getInstance().timeInMillis
            ))
            dismiss()
        }

        return detailDialog
    }

}