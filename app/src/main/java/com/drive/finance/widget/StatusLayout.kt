package com.drive.finance.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.drive.finance.R
import org.jetbrains.anko.onClick

/**
 * Created by aaron on 2017/9/25.
 */
class StatusLayout : FrameLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    lateinit var emptyView: View
    lateinit var errorView: View
    lateinit var errorLayout: LinearLayout

    override fun onFinishInflate() {
        super.onFinishInflate()

        emptyView = LayoutInflater.from(context).inflate(R.layout.widget_empty, null, false)
        errorView = LayoutInflater.from(context).inflate(R.layout.widget_error, null, false)
        errorLayout = errorView.findViewById(R.id.errorLayout) as LinearLayout

        val lps = android.widget.FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT, android.widget.FrameLayout.LayoutParams.MATCH_PARENT)
        this.addView(emptyView, lps)
        this.addView(errorView, lps)
        showContent()
    }

    fun showContent() {
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    fun showError() {
        errorView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    fun showEmpty() {
        emptyView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }
}
