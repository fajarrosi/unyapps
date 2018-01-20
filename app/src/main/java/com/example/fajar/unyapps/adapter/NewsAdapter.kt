package com.example.fajar.unyapps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.fajar.unyapps.R
import com.example.fajar.unyapps.models.NewsModel
import com.example.fajar.unyapps.views.NewsHolder
import kotlinx.android.synthetic.main.item_news.view.*

/**
 * Created by fajar on 28/12/17.
 */
class NewsAdapter : RecyclerView.Adapter<NewsHolder> {
    internal var models: List<NewsModel>
    internal lateinit var context : Context

    constructor(models: List<NewsModel>,context: Context) : super() {
        this.models = models
        this.context = context
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        return NewsHolder(v, onClickListener)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(models[position])
//        Glide.with(context)
//                .load(models.get(2))
//                .into(holder.itemView.iv_news)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    interface OnClickListener {
        fun onClick(adapterPosition: Int)
    }


}

