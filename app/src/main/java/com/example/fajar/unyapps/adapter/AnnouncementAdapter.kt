package com.example.fajar.unyapps.adapter

/**
 * Created by fajar on 02/01/18.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.fajar.unyapps.R
import com.example.fajar.unyapps.models.AnnouncementModel
import com.example.fajar.unyapps.views.AnnouncementHolder


class AnnouncementAdapter : RecyclerView.Adapter<AnnouncementHolder> {
    internal var models: List<AnnouncementModel>

    constructor(models: List<AnnouncementModel>) : super() {
        this.models = models
    }

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_announcement, parent, false)
        return AnnouncementHolder(v, onClickListener)
    }

    override fun onBindViewHolder(holder: AnnouncementHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount(): Int {
        return models.size
    }

    interface OnClickListener {
        fun onClick(adapterPosition: Int)
    }


}
