package com.madhat.todolist

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var todoItem: EditText
    lateinit var addBtn: Button
    lateinit var listView: ListView

    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.mipmap.logo_image)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.title = "TODO List"

        setContentView(R.layout.activity_main)

        todoItem = findViewById(R.id.editTextItem)
        addBtn = findViewById(R.id.button)
        listView = findViewById(R.id.listView)

        itemList = fileHelper.readData(this)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        listView.adapter = arrayAdapter

        addBtn.setOnClickListener {
            val itemName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                val formatted = LocalDateTime.now().format(formatter)
                todoItem.text.toString() + " ~ [$formatted]"
            } else {
                todoItem.text.toString()
            }
            itemList.add(itemName)
            todoItem.setText("")
            fileHelper.writeData(itemList, this)
            arrayAdapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adapterView, view, position, l ->
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Delete")
            alertDialog.setMessage("Do you want to delete this item from the list?")
            alertDialog.setCancelable(false)
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList, this)
            })
            alertDialog.show()

        }
    }
}