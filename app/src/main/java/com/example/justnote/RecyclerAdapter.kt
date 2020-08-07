package com.example.justnote

import android.app.Activity
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerAdapter(private val data: ArrayList<String>, private val dataHelper: SaveDataHelper,
    private val activity: Activity) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    private val mDataHelper: SaveDataHelper = dataHelper
    private val mActivity: Activity = activity

    class RecyclerViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var textView: TextView = v.rowTextView
        var buttonDone: Button = v.doneButton
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.RecyclerViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return RecyclerViewHolder(inflatedView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerAdapter.RecyclerViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item

        holder.buttonDone.setOnClickListener {
            data.removeAt(position)
            mDataHelper.writeData(data)
            Toast.makeText(mActivity, "$item was deleted", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }
    }

}