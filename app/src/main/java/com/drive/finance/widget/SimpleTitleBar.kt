package com.drive.finance.widget

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.R
import com.drive.finance.util.dip2px

/**
 * Created by aaron on 2017/2/9.
 * 自定义ActionBar组件
 */
class SimpleTitleBar : LinearLayout {

    var backImage: ImageView? = null
    var titleView: TextView? = null
    var backLayout: LinearLayout? = null

    constructor(context: Context) : super(context) {

        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        if (context == null) {
            return
        }

        val paddings = dip2px(context, 15.toFloat())
        setPadding(paddings, paddings, paddings, paddings)
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(Color.WHITE)

        val rootView = LayoutInflater.from(context).inflate(R.layout.widget_titlebar, null)
        val lps = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        this.addView(rootView, lps)

        initView(rootView)

        /**
         * 初始化自定义属性
         */
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleTitleBar)
            val titleContent = ta.getString(R.styleable.SimpleTitleBar_titleBar_content)
            if (!TextUtils.isEmpty(titleContent)) {
                titleView!!.text = titleContent
            }

            val titleTextSize = ta.getDimension(R.styleable.SimpleTitleBar_titleBar_text_size, titleView!!.textSize)
            titleView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)

            val titleTextColor = ta.getColor(R.styleable.SimpleTitleBar_titleBar_text_color, titleView!!.currentTextColor)
            titleView!!.setTextColor(titleTextColor)
        }
    }

    private fun initView(rootView: View) {
        backImage = rootView.findViewById(R.id.titleBarBack) as ImageView
        titleView = rootView.findViewById(R.id.titleBarText) as TextView
        backLayout = rootView.findViewById(R.id.backLayout) as LinearLayout
    }

    fun setText(text: String) {
        titleView?.text = text
    }


}