package com.example.root.deutsche

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerAdapter(private val item : ArrayList<Alphabets>, private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item1.text = item[position].letter
        holder.item2.text = item[position].pronunciation
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val item1 = view.item1!!
        val item2 = view.item2!!
    }
}