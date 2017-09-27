package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.BaseUrl
import com.drive.finance.network.model.PayModel
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.ProgressBar

class PayFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val snText by lazy {
        view?.findViewById(R.id.snText) as TextView
    }
    val goodsName by lazy {
        view?.findViewById(R.id.goodsName) as TextView
    }
    val submitLayout by lazy {
        view?.findViewById(R.id.submitLayout) as LinearLayout
    }
    val webLayout by lazy {
        view?.findViewById(R.id.webLayout) as LinearLayout
    }
    val progressBar1 by lazy {
        view?.findViewById(R.id.progressBar1) as ProgressBar
    }
    val webView by lazy {
        view?.findViewById(R.id.webView) as WebView
    }
    val apiClient by lazy {
        APIClient()
    }
    lateinit var payModel: PayModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_pay, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }
        submitLayout.onClick {
            try {
                val sb = StringBuffer(BaseUrl.CENTER_PAY_API)
                sb.append("&uid=").append(APIClient.uid)
                sb.append("&goodsid=").append(payModel.goodsId)
                sb.append("&order=").append(payModel.sn)
                sb.append("&paymoney=").append(payModel.price.toString())
                sb.append("&attach=大众财富")
                webLayout.visibility = View.VISIBLE
                webView.loadUrl(sb.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun initView() {
        snText.text = payModel.sn
        goodsName.text = payModel.goodsName

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
    }
}

fun createPayFragment(payModel: PayModel): PayFragment {
    val payFragment = PayFragment()
    payFragment.payModel = payModel
    return payFragment
}