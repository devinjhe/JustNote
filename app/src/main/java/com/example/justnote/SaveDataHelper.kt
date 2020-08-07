package com.example.justnote

import android.app.Activity
import android.content.Context

class SaveDataHelper(private val activity: Activity) {
    private var mActivity: Activity = activity

    fun readData():ArrayList<String> {
        val dataItems: ArrayList<String> = arrayListOf()
        val sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE) ?: return dataItems

        val nItems = sharedPref.getInt("nItems", 0)
        for (i in 0 until nItems) {
            val item = sharedPref.getString("Items$i", "")
            if (item != null) {
                dataItems.add(item)
            }
        }
        return dataItems
    }

    fun writeData(dataList: ArrayList<String>) {
        val sharedPref = mActivity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("nItems", dataList.size)
            for (i in 0 until dataList.size) {
                putString("Items$i", dataList[i])
            }
            apply()
        }
    }
}