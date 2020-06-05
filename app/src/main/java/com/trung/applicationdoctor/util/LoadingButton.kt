package com.trung.applicationdoctor.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.trung.applicationdoctor.R

/**
  * Custom button with round progress bar in the middle of the button
  * You can show the progress bar through the isLoading function.
  */
class LoadingButton(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val progressBar: ProgressBar = ProgressBar(context)
    private val textView: TextView = TextView(context)
    lateinit var buttonText: String
    private var enableTextColor: Int = Color.BLACK
    private var unableTextColor: Int = Color.DKGRAY
    private var buttonTextSize : Float = 5.0f


    init {
        val a = context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0, 0)

        a?.let {
//            val fontId = a.getResourceId(R.styleable.LoadingButton_buttonTextFontFamily, Typeface.DEFAULT)
//            ResourcesCompat.getFont(context, fontId)?.let {
//                setTypeface(it)
//            }
            buttonText = a.getString(R.styleable.LoadingButton_buttonText) ?: ""
            setLoading(a.getBoolean(R.styleable.LoadingButton_setLoading, false))
            setEnable(a.getBoolean(R.styleable.LoadingButton_setEnable, false))
            enableTextColor = it.getColor(R.styleable.LoadingButton_buttonEnableTextColor,
                ContextCompat.getColor(context, R.color.colorValid))
            unableTextColor = it.getColor(R.styleable.LoadingButton_buttonUnableTextColor,
                ContextCompat.getColor(context, R.color.white))

            buttonTextSize = it.getDimension(R.styleable.LoadingButton_buttonTextSize, 14.0f)

        }

        initButton()
        initProgress()
        isClickable = true
    }

    private fun initProgress() {
        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.gravity = Gravity.CENTER
        progressBar.setPadding(8, 8, 8, 8)
        progressBar.layoutParams = lp
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
        }
        super.addView(progressBar)
    }

    private fun initButton() {
        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.gravity = Gravity.CENTER
        textView.apply {
            text = buttonText
            //textSize = buttonTextSize
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, buttonTextSize)
            layoutParams = lp
            isAllCaps = true
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setTextColor(if (isEnabled) enableTextColor else unableTextColor)
        }
        super.addView(textView)
    }

    fun setText(text: String) {
        this.textView.text = text
    }

    fun setTextSize(size: Float) {
        this.textView.textSize = size
    }

    fun setTypeface(typeface: Typeface) {
        textView.typeface = typeface
    }

    fun setLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        textView.visibility = if (loading) View.INVISIBLE else View.VISIBLE
    }

    fun setEnable(enable: Boolean) {
        isEnabled = enable
        isClickable = enable
        setTextColor()
    }

    fun setTextColor() {
        textView.setTextColor(if (isEnabled) enableTextColor else unableTextColor)
    }
}

@BindingAdapter("buttonText")
fun LoadingButton.setButtonText(string: String) {
    this.setText(string)
}

@BindingAdapter("buttonTextSize")
fun LoadingButton.setButtonTextSize(size: Float) {
    this.setTextSize(size)
}

@BindingAdapter("setLoading")
fun LoadingButton.setLoading(loading: Boolean) {
    this.setLoading(loading)
}

@BindingAdapter("setEnable")
fun LoadingButton.setEnable(enable: Boolean) {
    this.setEnable(enable)
}
