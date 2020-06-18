package com.trung.applicationdoctor.ui.activity.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.BaseActivity
import com.trung.applicationdoctor.databinding.ActivityDetailBinding
import com.trung.applicationdoctor.util.AppDialog
import com.trung.applicationdoctor.util.ERROR_EVENT
import com.trung.applicationdoctor.util.UIEvent

import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    private val viewModel by viewModel(DetailViewModel::class)
    override fun getLayoutResId() = R.layout.activity_detail

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.allowEnterTransitionOverlap = false;
        window.allowReturnTransitionOverlap = false;
        //full screen
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding.vm = viewModel
        viewModel.uiEvent.observe(this, onUiEvent())
        viewModel.detailView = this
        intent?.let {
            intent.getStringExtra(DETAIL_BOARD_ID)?.let {
                //call API to get infor detail of specific item when it know its exact boardId
                viewModel.getDetailChannel(it)
            }

            intent.getStringExtra(DETAIL_ANIMATION_NAME)?.let {
                binding.ivPhotoUrl.transitionName = it
            }
        }

    }

//    override fun onStop() {
//        super.onStop()
//
//        GlideApp.with(ApplicationDoctor.context).clear(binding.ivPhotoUrl)
//    }
    /**
     * Function which observe the UI events sent from viewModel to process them
     * NO_INTERNET_AND_NO_DATA_IN_ROOM: is called when it has no internet to call API and even there is no data in ROOM to get
     */
    override fun onUiEvent() = Observer<UIEvent<Int>> {
        it.getContentIfNotHandled()?.let {
            when (it.first) {
                ERROR_EVENT -> {
                    AppDialog.showDialog(this, it.second.toString()) {super.onBackPressed()}
                }
                else -> {

                }
            }
        }
    }


    companion object {
        private const val DETAIL_BOARD_ID = "detail_board_id"
        const val DETAIL_ANIMATION_NAME = "detail_animation_mame"

        // use for animation

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun startActivity(activity: Activity?, boardId: String, view: View) {

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,
                view,
                "hihi")

            Intent(activity, DetailActivity::class.java).apply {
                this.putExtra(DETAIL_BOARD_ID, boardId)
                this.putExtra(DETAIL_ANIMATION_NAME, "hihi")
            }.also {

                activity.startActivity(it, options.toBundle())

                //activity.overridePendingTransition(R.anim.bounce, R.anim.fade_in)
            }
        }
    }
}