package com.trung.applicationdoctor.ui.activity.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseActivity
import com.trung.applicationdoctor.base.CustomPagerAdapter
import com.trung.applicationdoctor.databinding.ActivityMainBinding
import com.trung.applicationdoctor.extension.hideKeyboard
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment
import com.trung.applicationdoctor.util.UIEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModel(MainViewModel::class)
    private lateinit var customPagerAdapter: CustomPagerAdapter

    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.uiEvent.observe(this, onUiEvent())

        initPagerAdapterAndTabs()

        initSearchBar()
    }

    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
//                CLICK_SEARCH -> {
//                    viewModel.isSearchDisplayed.set(true)
//                    binding.photoSearch.onActionViewExpanded()
//                }
            }
        }
    }

    private fun initPagerAdapterAndTabs() {
        customPagerAdapter = CustomPagerAdapter(supportFragmentManager)
        customPagerAdapter.addFragment(ListChannelFragment(), getString(R.string.all))
        binding.pager.adapter = customPagerAdapter

        binding.tabLayout.setupWithViewPager(binding.pager)

        binding.pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //loadDataForSpecificTab()dd
            }
        })
    }

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

        binding.photoSearch.setOnSearchClickListener(View.OnClickListener {
            viewModel.isSearchDisplayed.set(true)
        })

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
        const val CLICK_SEARCH = 0
    }
}