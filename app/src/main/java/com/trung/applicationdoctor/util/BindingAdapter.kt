package com.trung.applicationdoctor.util

import android.content.res.ColorStateList
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.trung.applicationdoctor.R
import com.trung.applicationdoctor.base.CustomPagerAdapter
import com.trung.applicationdoctor.data.db.entity.ChannelCategoryEntity
import com.trung.applicationdoctor.data.db.entity.ChannelListEntity
import com.trung.applicationdoctor.data.remote.response.ChannelCategory
import com.trung.applicationdoctor.data.remote.response.ChannelList
import com.trung.applicationdoctor.ui.fragment.list.ListChannelAdapter
import com.trung.applicationdoctor.ui.fragment.list.ListChannelFragment
import com.trung.applicationdoctor.ui.fragment.list.ListChannelViewModel

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

@BindingAdapter("loadWebView")
fun setWebView(webView: WebView, content: String?) {
    content?.let {
        webView.loadData(content, "text/html", "UTF-8")
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
 * 유효 버튼 보여주는 함수
 * 유효성 상태에 따라 적절한 아이콘을 보여준다
 * @param isValid 유효성 여부
 * @param isShow 보여줄지
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
 * 상황에 따라서 입력창에 클리어 버튼 유무를 정해주는 함수
 * 그리고 그 버튼 터치시 삭제될 수 있게 기능 추가
 * @param isShow 보이는 여부
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
 * 상태에 따라서 배경색을 리턴해주는 함수
 * @param focused 부모 뷰의 포커스 여부
 * @param isError 오류가 있는지
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

