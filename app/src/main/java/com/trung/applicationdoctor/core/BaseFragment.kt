package com.trung.applicationdoctor.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.util.UIEvent

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    abstract fun getLayoutResId(): Int

    protected lateinit var binding: T
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            getLayoutResId(),
            null,
            true
        )

        binding.lifecycleOwner = this
        return binding.root
    }

    abstract fun onUiEvent(): Observer<UIEvent<Int>>
}