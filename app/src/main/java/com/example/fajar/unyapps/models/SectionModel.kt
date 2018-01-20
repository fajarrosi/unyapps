package com.example.fajar.unyapps.models

import com.example.fajar.unyapps.R
import java.util.ArrayList

/**
 * Created by fajar on 01/01/18.
 */
data class SectionModel(val title: String) {

    companion object {

        fun mockItems(): ArrayList<SectionModel> {
            return (1 until 2).mapTo(ArrayList()) {
                SectionModel(
                        title = "Chapter " + it
                )
            }
        }

    }

}