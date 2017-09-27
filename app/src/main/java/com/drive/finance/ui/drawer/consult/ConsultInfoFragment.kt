package com.drive.finance.ui.drawer.consult

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.ConsultModel
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 公司简介
 */
class ConsultInfoFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val titleText by lazy {
        view?.findViewById(R.id.titleText) as TextView
    }
    val contentText by lazy {
        view?.findViewById(R.id.contentText) as TextView
    }

    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_consult_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        try {
            apiClient.requestConsultInfoData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ consultModel ->
                        updateUI(consultModel)
                    }, {})
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateUI(consultModel: ConsultModel) {
        titleText.text = consultModel.title
        contentText.text = Html.fromHtml(consultModel.content)
    }
}

fun createConsultInfoFragment(): ConsultInfoFragment {
    return ConsultInfoFragment()
}