package com.trung.applicationdoctor.ui.fragment.list

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseFragment
import com.trung.applicationdoctor.databinding.FragmentListChannelBinding
import com.trung.applicationdoctor.extension.hideKeyboard
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
        binding.lifecycleOwner = this

        binding.vm = viewModel
        binding.mainVM = mainViewModel

        binding.rvListChannel.adapter = adapter


        adapter.setOnItemClickListener { i, channelListEntity ->
            //DetailActivity.startActivity(requireActivity(), photoDetailEntity)
        }

        mainViewModel.searchLiveData.observe(viewLifecycleOwner, Observer { newText ->
            adapter.filter.filter(newText)
        })

    }

    override fun onUiEvent() = Observer<UIEvent<Int>> {
        TODO("Not yet implemented")
    }
}