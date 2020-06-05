package com.trung.applicationdoctor.ui.activity.detail

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_OUTSIDE
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.ApplicationDoctor
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.BaseActivity
import com.trung.applicationdoctor.databinding.ActivityDetailBinding
import com.trung.applicationdoctor.di.GlideApp
import com.trung.applicationdoctor.util.UIEvent

import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    private val viewModel by viewModel(DetailViewModel::class)
    private lateinit var alertDialog : AlertDialog.Builder
    override fun getLayoutResId() = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        alertDialog = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
        binding.vm = viewModel
        viewModel.uiEvent.observe(this, onUiEvent())
        viewModel.detailView = this
        intent?.let {
            intent.getStringExtra(DETAIL_BOARD_ID)?.let {
                viewModel.getDetailChannel(it)
            }
        }
    }

//    override fun onStop() {
//        super.onStop()
//
//        GlideApp.with(ApplicationDoctor.context).clear(binding.ivPhotoUrl)
//    }

    companion object {
        private const val DETAIL_BOARD_ID = "detail_board_id"
        const val NO_INTERNET_AND_NO_DATA_IN_ROOM = 0
        fun startActivity(activity: Activity?, boardId: String) {
            Intent(activity, DetailActivity::class.java).apply {
                this.putExtra(DETAIL_BOARD_ID, boardId)
            }.also {
                activity?.startActivity(it)
                activity?.overridePendingTransition(R.anim.bounce, R.anim.fade_in)
            }
        }
    }

    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
                NO_INTERNET_AND_NO_DATA_IN_ROOM -> {
                    alertDialog.setMessage(it.second.toString())
                    alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                        super.onBackPressed()
                    })
                    alertDialog.setOnCancelListener{
                        super.onBackPressed()
                    }
                    alertDialog.show()
                }
                else -> {

                }
            }
        }
    }
}