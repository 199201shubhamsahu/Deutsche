package com.example.root.deutsche

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.test_list_item.view.*
import android.widget.Toast
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.root.deutsche.R.id.testRadioGroup

/*  Created by
 *   Shubham Sahu(pizzasahu)
 *   in 2018
 */

class TestRecyclerAdapter(private val item : ArrayList<Test>, private val context: Context) : RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return item.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.test_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.question.text = item[position].question
        holder.option1.text = item[position].option1
        holder.option2.text = item[position].option2
        holder.option3.text = item[position].option3

        holder.option1.setOnClickListener{
            if(item[position].option1==item[position].answer) {
                item[position].right = 1
            }
            else
                item[position].right = 0
        }

        holder.option2.setOnClickListener{
            if(item[position].option2==item[position].answer) {
                item[position].right = 1
            }
            else
                item[position].right = 0
        }

        holder.option3.setOnClickListener{
            if(item[position].option3==item[position].answer) {
                item[position].right = 1
            }
            else
                item[position].right = 0
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val question = view.testQuestion!!
        val option1 = view.testItem1!!
        val option2 = view.testItem2!!
        val option3 = view.testItem3!!

    }
}