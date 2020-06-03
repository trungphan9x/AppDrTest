package com.trung.applicationdoctor.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.CustomPagerAdapter
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
import com.trung.applicationdoctor.ui.fragment.list.ListChannelAdapter
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment

@BindingAdapter("setUrlPhoto")
fun ImageView.setUrlImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_default)
//        .error(R.drawable.bg_error_image)
        .centerCrop()
        .fitCenter()
        .into(this)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ChannelListEntity>?) {
    val adapter = recyclerView.adapter as ListChannelAdapter
    //adapter.submitList(data)
    adapter.setIetms(data)
}

@BindingAdapter("listTitles")
fun setTabTitle(viewPager: ViewPager, titles: List<ChannelCategoryEntity>?) {
    val adapter = viewPager.adapter as CustomPagerAdapter
    titles?.forEach { categoryChannel ->
        adapter.addFragment(ListChannelFragment(), categoryChannel.categoryName)
    }
    adapter.notifyDataSetChanged()
}