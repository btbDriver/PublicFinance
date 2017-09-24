package com.drive.finance.ui.drawer.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class ContactUsFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val progressBar1 by lazy {
        view?.findViewById(R.id.progressBar1) as ProgressBar
    }
    val webView by lazy {
        view?.findViewById(R.id.webView) as WebView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_contact_us, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        val webSettings = webView.settings
        webSettings.useWideViewPort = true//设置此属性，可任意比例缩放
        webSettings.loadWithOverviewMode = true
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })

        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar1.visibility = View.GONE//加载完网页进度条消失
                } else {
                    progressBar1.visibility = View.VISIBLE//开始加载网页时显示进度条
                    progressBar1.progress = newProgress//设置进度值
                }
            }
        })

        webView.loadUrl("http://www11.53kf.com/webCompany.php?arg=10147286&style=2%5D%5Bimg%5Dhttp%3A")
    }
}

fun createContactUsFragment(): ContactUsFragment {
    return ContactUsFragment()
}
