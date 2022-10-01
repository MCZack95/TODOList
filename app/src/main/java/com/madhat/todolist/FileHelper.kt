package com.madhat.todolist

import android.content.Context
import java.io.*

class FileHelper {

    val FILENAME = "todo_list_info.dat"

    fun writeData(items: ArrayList<String>, context: Context)
    {
        val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(items)
        oos.close()
    }

    fun readData(context: Context): ArrayList<String>
    {
        var itemList: ArrayList<String>

        try {
            val fis: FileInputStream = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)
            itemList = ois.readObject() as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
        } catch (e: FileNotFoundException)
        {
            itemList = ArrayList<String>()
        }

        return itemList
    }

}