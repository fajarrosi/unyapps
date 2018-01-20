package com.example.fajar.unyapps.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.module.AppGlideModule
import com.example.fajar.unyapps.R
import com.example.fajar.unyapps.adapter.NewsAdapter
import com.example.fajar.unyapps.models.NewsModel
import kotlinx.android.synthetic.main.item_news.view.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
class NewsHolder(itemView: View, var onClickListener: NewsAdapter.OnClickListener?):   RecyclerView.ViewHolder(itemView),View.OnClickListener  {

    private val tv_news: TextView
//    private val iv_news: ImageView

    init {
        itemView.setOnClickListener(this)
        tv_news = itemView.findViewById(R.id.tv_news)
//        iv_news = itemView.findViewById(R.id.iv_news)
    }

    override fun onClick(view: View) {
        if (onClickListener != null) {
            onClickListener!!.onClick(adapterPosition)
        }
    }

    fun bind(model: NewsModel) {
        tv_news.text = model.title
    }

}

