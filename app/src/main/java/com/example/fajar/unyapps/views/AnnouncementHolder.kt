package com.example.fajar.unyapps.views

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.fajar.unyapps.R
import com.example.fajar.unyapps.models.AnnouncementModel
import android.R.attr.onClick
import android.util.Log
import com.example.fajar.unyapps.adapter.AnnouncementAdapter



/**
 * Created by fajar on 02/01/18.
 */
class AnnouncementHolder(itemView: View, var onClickListener: AnnouncementAdapter.OnClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val tv_title: TextView
    private val tv_date: TextView

    init {
        itemView.setOnClickListener(this)
        tv_title = itemView.findViewById(R.id.title_announcement)
        tv_date = itemView.findViewById(R.id.date_announcement)
    }

    override fun onClick(view: View) {
        if (onClickListener != null) {
            onClickListener!!.onClick(adapterPosition)
        }
    }

    fun bind(model: AnnouncementModel) {
        tv_title.text = model.title
        Log.d("titleholder",model.title)
        tv_date.text = model.date
    }

}