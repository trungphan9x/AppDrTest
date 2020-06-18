package com.trung.applicationdoctor.ui.fragment.list

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseFragment
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.databinding.FragmentListChannelBinding
import com.trung.applicationdoctor.ui.activity.detail.DetailActivity
import com.trung.applicationdoctor.ui.activity.main.MainViewModel
import com.trung.applicationdoctor.util.AppDialog
import com.trung.applicationdoctor.util.UIEvent
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListChannelFragment : BaseFragment<FragmentListChannelBinding>() {
    private val viewModel: ListChannelViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val adapter = ListChannelAdapter()

    override fun getLayoutResId() = R.layout.fragment_list_channel


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //tab name info which is passed here when ListChannelFragment is created to distinguish different tabs sharing the same ListChannelFragment
        arguments?.let {
            viewModel.tabInformation.set(ChannelCategory(
                categoryIdx = it.getString(ARG_TAB_ID)!!,
                categoryName = it.getString(ARG_TAB_NAME)!!
            ))
        }

        binding.vm = viewModel
        binding.mainVM = mainViewModel

        //get items from API after having tabs name is bound TabLayout
        viewModel.getItemsFromApi()

        //Init Adapter Of recyclerview
        binding.rvListChannel.adapter = adapter

        adapter.setOnItemClickListener { position, channelList, view ->

            DetailActivity.startActivity(requireActivity(), channelList.boardIdx, view)

        }

        //filter items in recyclerview based on keyword of searchbar
        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer { newText ->
            adapter.filter.filter(newText)
        })

    }

    /**
     * Function which observe the UI events sent from viewModel to process them
     * ERROR_MESSAGE: dialog showing error message
     */
    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
                ERROR_MESSAGE -> {
                    AppDialog.showDialog(requireContext(), it.second.toString())
                }

                else -> {

                }
            }
        }
    }

    companion object {
        const val ERROR_MESSAGE = 0
        const val ARG_TAB_NAME = "tab_name"
        const val ARG_TAB_ID = "tab_id"
        fun newInstance(tabName: String, tabId: String) = ListChannelFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TAB_NAME,tabName)
                putString(ARG_TAB_ID, tabId)
            }
        }
    }
}