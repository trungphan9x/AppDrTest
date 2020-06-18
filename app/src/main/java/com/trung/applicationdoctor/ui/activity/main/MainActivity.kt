package com.trung.applicationdoctor.ui.activity.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseActivity
import com.trung.applicationdoctor.core.CustomPagerAdapter
import com.trung.applicationdoctor.databinding.ActivityMainBinding
import com.trung.applicationdoctor.util.extension.hideKeyboard
import com.trung.applicationdoctor.util.extension.setHasFirstLauchApp
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment
import com.trung.applicationdoctor.util.AppDialog
import com.trung.applicationdoctor.util.ERROR_EVENT
import com.trung.applicationdoctor.util.UIEvent
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModel(MainViewModel::class)
    private lateinit var customPagerAdapter: CustomPagerAdapter

    override fun getLayoutResId() = R.layout.activity_main

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.allowEnterTransitionOverlap = false;
        window.allowReturnTransitionOverlap = false;
        this.setHasFirstLauchApp(false)

        binding.vm = viewModel

        viewModel.uiEvent.observe(this, onUiEvent())

        initPagerAdapterAndTabs()

        initSearchBar()
    }
    /**
     * Function which observe the UI events sent from viewModel to process them
     */
    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
                ERROR_EVENT -> {
                    AppDialog.showDialog(this, it.second.toString())
                }
            }
        }
    }

    /**
     * Initiate PagerAdapter and TabLayout
     */
    private fun initPagerAdapterAndTabs() {
        customPagerAdapter = CustomPagerAdapter(supportFragmentManager)
        customPagerAdapter.addFragment(ListChannelFragment.newInstance(tabName = getString(R.string.all), tabId = getString(R.string.all)), getString(R.string.all))
        binding.pager.adapter = customPagerAdapter

        binding.tabLayout.setupWithViewPager(binding.pager)

        binding.pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        viewModel.currentTab.set(getString(R.string.all))
                    }


                }
            }
        })
    }

    /**
     * Initiate SearchBar
     */
    private fun initSearchBar() {
        binding.photoSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchLiveData.postValue(newText)
                return false
            }

        })

        //If SearchBar is displayed, title of MainActivity is invisible
        binding.photoSearch.setOnSearchClickListener(View.OnClickListener {
            viewModel.isSearchDisplayed.set(true)
        })

        //if click close btn of search bar, remove keyword and close soft keyboard
        binding.photoSearch.findViewById<ImageView>(R.id.search_close_btn).setOnClickListener {
            //remove keyword
            binding.photoSearch.findViewById<TextView>(R.id.search_src_text).text = ""

            //close soft keyboard
            viewModel.isSearchDisplayed.set(false)
            binding.root.hideKeyboard()
            binding.photoSearch.onActionViewCollapsed()
        }
    }

    private fun tabListener() {
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {}

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val views = arrayListOf<View>()
                tab.view.findViewsWithText(views, tab.text, View.FIND_VIEWS_WITH_TEXT)
                views.forEach { view ->
                    if (view is TextView) {
                        TextViewCompat.setTextAppearance(view, R.style.tab_text_regular)
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                val views = arrayListOf<View>()
                tab.view.findViewsWithText(views, tab.text, View.FIND_VIEWS_WITH_TEXT)
                views.forEach { view ->
                    if (view is TextView) {
                        TextViewCompat.setTextAppearance(view, R.style.tab_text_bold)
                    }
                }
            }
        })
    }

    companion object {

        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }
    }
}