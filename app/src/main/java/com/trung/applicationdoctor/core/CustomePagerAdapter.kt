package com.trung.applicationdoctor.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CustomPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = ArrayList<Fragment>()
    private val titles : ArrayList<String>? = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles?.add(title)
    }

    fun clear() {
        fragments.clear()
        titles?.clear()
    }

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        titles?.let { arrayList ->
            arrayList.getOrNull(position)?.let {
                return it
            }
        }
        return null
    }
}