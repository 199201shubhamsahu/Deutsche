package com.example.root.deutsche

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

/*  Created by
 *   Shubham Sahu(pizzasahu)
 *   in 2018
 */

class TwoColRecyclerAdapter(private val item1 : ArrayList<String>, private val item2 : ArrayList<String>, private val context: Context) : RecyclerView.Adapter<TwoColRecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return item1.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item1.text = item1[position]
        holder.item2.text = item2[position]
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val item1 = view.item1!!
        val item2 = view.item2!!
    }
}