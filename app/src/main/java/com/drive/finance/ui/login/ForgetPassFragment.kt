package com.drive.finance.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ForgetPassFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val nextPlaceText by lazy {
        view?.findViewById(R.id.nextPlaceText) as TextView
    }
    val codeImage by lazy {
        view?.findViewById(R.id.codeImage) as ImageView
    }
    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_forget_pass, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        nextPlaceText.onClick {
            start(createConfirmFragment())
        }

        try {
            apiClient.requestCode()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        Glide.with(context)
                                .load(jsonObject.getString("url"))
                                .into(codeImage)
                    }, {})
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun createForgetPassFragment(): ForgetPassFragment {
    return ForgetPassFragment()
}
