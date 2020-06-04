package com.trung.applicationdoctor.ui.fragment.list

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseFragment
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.databinding.FragmentListChannelBinding
import com.trung.applicationdoctor.extension.hideKeyboard
import com.trung.applicationdoctor.ui.activity.detail.DetailActivity
import com.trung.applicationdoctor.ui.activity.main.MainViewModel
import com.trung.applicationdoctor.util.UIEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListChannelFragment : BaseFragment<FragmentListChannelBinding>() {
    private val viewModel: ListChannelViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val adapter = ListChannelAdapter()

    override fun getLayoutResId() = R.layout.fragment_list_channel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            viewModel.tabInformation.set(ChannelCategory(
                categoryIdx = it.getString(ARG_TAB_ID)!!,
                categoryName = it.getString(ARG_TAB_NAME)!!
            ))
        }


        binding.lifecycleOwner = this

        binding.vm = viewModel
        binding.mainVM = mainViewModel

        viewModel.getItemsFromApi()

        binding.rvListChannel.adapter = adapter

        adapter.setOnItemClickListener { i, channelList ->
            DetailActivity.startActivity(requireActivity(), channelList.boardIdx)
        }

        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer { newText ->
            adapter.filter.filter(newText)
        })

    }

    override fun onUiEvent() = Observer<UIEvent<Int>> {
        TODO("Not yet implemented")
    }

    companion object {
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