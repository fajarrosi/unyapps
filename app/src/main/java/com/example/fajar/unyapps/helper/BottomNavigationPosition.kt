package com.example.fajar.unyapps.helper

import android.support.v4.app.Fragment
import com.example.fajar.unyapps.Home.HomeFragment
import com.example.fajar.unyapps.fragment.NewsFragment
import com.example.fajar.unyapps.fragment.AnnouncementFragment
import com.example.fajar.unyapps.R

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    HOME(0, R.id.home),
    NEWS(1, R.id.nav_news),
    ANNOUNCEMENT(2, R.id.nav_announcement);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME
    BottomNavigationPosition.NEWS.id -> BottomNavigationPosition.NEWS
    BottomNavigationPosition.ANNOUNCEMENT.id -> BottomNavigationPosition.ANNOUNCEMENT
    else -> BottomNavigationPosition.HOME
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.HOME -> HomeFragment.newInstance()
    BottomNavigationPosition.NEWS-> NewsFragment.newInstance()
    BottomNavigationPosition.ANNOUNCEMENT -> AnnouncementFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.HOME -> HomeFragment.TAG
    BottomNavigationPosition.NEWS -> NewsFragment.TAG
    BottomNavigationPosition.ANNOUNCEMENT -> AnnouncementFragment.TAG
}

