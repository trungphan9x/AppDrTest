package com.trung.applicationdoctor.ui.activity.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseActivity
import com.trung.applicationdoctor.core.CustomPagerAdapter
import com.trung.applicationdoctor.databinding.ActivityMainBinding
import com.trung.applicationdoctor.util.extension.hideKeyboard
import com.trung.applicationdoctor.util.extension.setHasFirstLauchApp
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment
import com.trung.applicationdoctor.util.UIEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModel(MainViewModel::class)
    private lateinit var customPagerAdapter: CustomPagerAdapter

    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setHasFirstLauchApp(false)

        binding.lifecycleOwner = this
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
                        viewModel.clickedTab.set(getString(R.string.all))
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

    companion object {

        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }
    }
}