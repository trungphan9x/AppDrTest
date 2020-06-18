package com.trung.applicationdoctor.util

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.core.CustomPagerAdapter
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.di.GlideApp
import com.trung.applicationdoctor.ui.fragment.list.ListChannelAdapter
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment


@BindingAdapter("setUrlPhoto")
fun ImageView.setUrlImage(url: String?) {
    GlideApp.with(this.context)
        .load(url)
        .apply {
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
        }
        .placeholder(R.drawable.ic_default)
        .centerCrop()
        .fitCenter()
        .into(this)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ChannelList>?) {
    val adapter = recyclerView.adapter as ListChannelAdapter
    //adapter.submitList(data)

    adapter.setIetms(data)
}

@BindingAdapter("listTitles")
fun setTabTitle(viewPager: ViewPager, titles: List<ChannelCategory>?) {
    val adapter = viewPager.adapter as CustomPagerAdapter
    titles?.forEach { categoryChannel ->
        adapter.addFragment(ListChannelFragment.newInstance(tabName = categoryChannel.categoryName, tabId = categoryChannel.categoryIdx), categoryChannel.categoryName)
    }
    adapter.notifyDataSetChanged()
}

@SuppressLint("SetJavaScriptEnabled")
@BindingAdapter("loadWebView")
fun WebView.setWebView(content: String?) {
    content?.let {
        this.settings.javaScriptEnabled = true
        this.webViewClient = WebViewClient()
        this.webChromeClient = WebChromeClient()
        this.loadData(content, "text/html; charset=UTF-8", null)
    }
}

@BindingAdapter("isErrorHint")
fun TextInputLayout.isErrorHint(hasError: Boolean) {
    val normalColor = ContextCompat.getColor(this.context, R.color.colorPlaceHolder)
    val pressColor = ContextCompat.getColor(this.context, R.color.colorPrimaryText)
    val accentColor = ContextCompat.getColor(this.context, R.color.tealish)
    val errorColor = ContextCompat.getColor(this.context, R.color.colorError)

    val myColorStateList = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_focused), //1
            intArrayOf(android.R.attr.state_pressed), //2
            intArrayOf() //3
        ),
        intArrayOf(if (hasError) errorColor else accentColor, //1
            pressColor, //2
            if (hasError) errorColor else normalColor //3
        )
    )
    this.defaultHintTextColor = myColorStateList
}

/**
 * Valid button showing function
 * Shows the appropriate icon according to the validity status
 * Whether @param isValid is valid
 * @param isShow
 */
@BindingAdapter("showValidButton", "isShowValidButton", requireAll = true)
fun TextInputEditText.showValidButton(isValid: Boolean, isShow: Boolean) {
    if (!isShow) return

    val validDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_text_valid)
    val inValidDrawable = AppCompatResources.getDrawable(this.context, R.drawable.ic_round_error)
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, if (isValid) validDrawable else inValidDrawable, null)
    this.setOnTouchListener(null)
}

@BindingAdapter("onFocusChangeListener")
fun TextInputEditText.setOnFocusChangeListener(listener: View.OnFocusChangeListener) {
    // clear listeners first avoid adding duplicate listener upon calling notify update related code
    this.onFocusChangeListener = listener
}

/**
 * Function to determine the presence or absence of a clear button in the input window depending on the situation
 * Also, the function can be deleted when the button is touched.
 * Whether @param isShow is visible
 */
@BindingAdapter("showClearButton")
fun TextInputEditText.showClearButton(isShow: Boolean) {
    val clearDrawable = AppCompatResources.getDrawable(this.context, R.drawable.ic_round_cancel)
    this.setCompoundDrawablesWithIntrinsicBounds(null, null, if (isShow) clearDrawable else null, null)
    if (isShow) {
        this.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = this.compoundDrawables.getOrNull(2) ?: return@OnTouchListener false
                if (event.rawX >= this.right - drawable.bounds.width()) {
                    this.text?.clear()
                    return@OnTouchListener false
                }
            }
            false
        })

    }
}

@BindingAdapter("onClickWithDebounce")
fun onClickWithDebounce(view: View, listener: android.view.View.OnClickListener) {
    view.setClickWithDebounce {
        listener.onClick(view)
    }
}

object LastClickTimeSingleton {
    var lastClickTime: Long = 0
}

fun View.setClickWithDebounce(action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - LastClickTimeSingleton.lastClickTime < 500L) return
            else action()
            LastClickTimeSingleton.lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

/**
 * Function that returns the background color according to the status
 * @param focused Whether the parent view is focused
 * @param isError error
 */
@BindingAdapter("isParentFocused", "isViewError", requireAll = true)
fun View.isErrorBackground(focused: Boolean, isError: Boolean) {
    when {
        focused -> {
            this.setBackgroundColor(ContextCompat.getColor(context, R.color.tealish))
        }
        !focused && !isError -> {
            this.setBackgroundColor(ContextCompat.getColor(context, R.color.colorEditTextNormal))
        }
        !focused && isError -> {
            this.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
        }
    }
}
