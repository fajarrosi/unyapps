package com.example.fajar.unyapps.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fajar.unyapps.models.SectionModel
import java.util.ArrayList

/**
 * Created by fajar on 01/01/18.
 */
class SectionSView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr)  {



    private val mTitle = ArrayList<TextView>()

    init {
        orientation = LinearLayout.HORIZONTAL

        (0 until 2).forEach {
            val icon = TextView(getContext())
            addView(icon)
            mTitle.add(icon)
        }
    }

    fun bind(sectionModel: SectionModel) {


    }
}
