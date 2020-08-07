package com.example.justnote

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: RecyclerAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var items: ArrayList<String>
    private val filename = "items.dat"
    private val dataHelper: SaveDataHelper = SaveDataHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = dataHelper.readData()

        linearLayoutManager = LinearLayoutManager(this)
        mAdapter = RecyclerAdapter(items, dataHelper, this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mAdapter

        addButton.setOnClickListener { addItem() }

        val editText = findViewById<EditText>(R.id.editText)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addItem()
                true
            } else {
                false
            }
        }

        editTextFocus()
    }

    private fun addItem() {
        val editText = findViewById<EditText>(R.id.editText)
        val item = editText.text.toString()
        if (item.isEmpty()) {
            Toast.makeText(this, "Text box is empty", Toast.LENGTH_SHORT).show()
            return
        }

        items.add(item)
        editText.setText("")
        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
        dataHelper.writeData(items)
        mAdapter.notifyDataSetChanged()
    }

    private fun editTextFocus() {
        editText.requestFocus()
        // open the soft keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}
