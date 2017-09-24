package com.drive.finance.ui.tab

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.PayModel
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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
    val payResult by lazy {
        view?.findViewById(R.id.payResult) as WebView
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
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        snText.text = payModel.sn
        goodsName.text = payModel.goodsName

        submitLayout.onClick {
            apiClient.requestPayData(payModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        // Log.i("#########", jsonObject.toString())
                        payResult.visibility = View.VISIBLE
                        payResult.loadUrl(jsonObject.getString("url"))
                    }, {})
        }
    }
}

fun createPayFragment(payModel: PayModel): PayFragment {
    val payFragment = PayFragment()
    payFragment.payModel = payModel
    return payFragment
}