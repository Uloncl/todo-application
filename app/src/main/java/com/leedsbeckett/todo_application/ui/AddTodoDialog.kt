package com.leedsbeckett.todo_application.ui

import android.app.Dialog
import android.app.UiModeManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.leedsbeckett.todo_application.data.Todo
import com.leedsbeckett.todo_application.databinding.AddTodoDialogBinding
import java.util.Calendar

class AddTodoDialog(val todo: Todo?) : DialogFragment() {
    private lateinit var editTitle : EditText
    private lateinit var editBody : EditText
    private lateinit var saveTodoBtn : Button
    private lateinit var cancelEditBtn : Button
    private lateinit var vm : TodoViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val uiModeManager = context?.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val binding = AddTodoDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        editTitle = binding.editTodoTitle
        editBody = binding.editTodoBody
        saveTodoBtn = binding.confirmEditTodoBtn
        cancelEditBtn = binding.cancelEditTodoBtn
        vm = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        editTitle.setText(todo?.title ?: "")
        editBody.setText(todo?.body ?: "")

        cancelEditBtn.setOnClickListener {
            dismiss()
        }

        saveTodoBtn.setOnClickListener {
            val title = editTitle.text.toString()
            val body = editBody.text.toString()
            var newTodo : Todo = Todo(
                title = title,
                body = body,
                completed = false,
                created = Calendar.getInstance().timeInMillis,
                updated = null,
                timeout = null,
                deleted = null
            )
            if (todo != null) {
                newTodo = Todo(
                    id = todo.id,
                    title = title,
                    body = body,
                    completed = todo.completed,
                    created = todo.created,
                    updated = Calendar.getInstance().timeInMillis,
                    timeout = null,
                    deleted = null
                )
            }
            vm.upsertTodo(newTodo)
            dismiss()
        }

        return dialog
    }
}