package com.trung.applicationdoctor.ui.activity.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseActivity
import com.trung.applicationdoctor.databinding.ActivityDetailBinding
import com.trung.applicationdoctor.util.UIEvent

import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    private val viewModel by viewModel(DetailViewModel::class)
    override fun getLayoutResId() = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        viewModel.detailView = this
        intent?.let {
//            viewModel.detailPhotoName.set(intent.getStringExtra(DETAIL_PHOTO_NAME))
//            viewModel.detailPhotoUrl.set(intent.getStringExtra(DETAIL_PHOTO_URL))
//            viewModel.detailPhotoDescription.set(intent.getStringExtra(DETAIL_PHOTO_DESCRIPTION))
        }
    }


    companion object {
//        private const val DETAIL_PHOTO_NAME = "detail_photo_name"
//        private const val DETAIL_PHOTO_URL = "detail_photo_url"
//        private const val DETAIL_PHOTO_DESCRIPTION = "detail_photo_description"
//
//        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//        fun startActivity(activity: Activity?, detailPhoto: PhotoDetailEntity) {
//            Intent(activity, DetailActivity::class.java).apply {
//                this.putExtra(DETAIL_PHOTO_NAME, detailPhoto.name)
//                this.putExtra(DETAIL_PHOTO_URL, detailPhoto.urlPhoto)
//                this.putExtra(DETAIL_PHOTO_DESCRIPTION, detailPhoto.description)
//            }.also {
//                activity?.startActivity(it)
//                activity?.overridePendingTransition(R.anim.bounce, R.anim.fade_in)
//            }
//        }
    }

    override fun onUiEvent() = Observer<UIEvent<Int>> {
        TODO("Not yet implemented")
    }
}