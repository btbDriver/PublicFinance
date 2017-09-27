package com.drive.finance.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    val submitText by lazy {
        view?.findViewById(R.id.submitText) as TextView
    }
    val userNumEdit by lazy {
        view?.findViewById(R.id.userNumEdit) as EditText
    }
    val telEdit by lazy {
        view?.findViewById(R.id.telEdit) as EditText
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

        submitText.onClick {
            try {
                if (TextUtils.isEmpty(userNumEdit.text.toString())) {
                    Toast.makeText(context, "请输入用户编号", Toast.LENGTH_SHORT).show()
                    return@onClick
                }
                if (TextUtils.isEmpty(telEdit.text.toString())) {
                    Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show()
                    return@onClick
                }

                apiClient.requestForgetPass(userNumEdit.text.toString(), telEdit.text.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ resultModel ->
                            if (resultModel.success == 0) {
                                pop()
                                Toast.makeText(context, "重置密码短信已发送到您的手机上", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, resultModel.info, Toast.LENGTH_SHORT).show()
                            }
                        }, {})
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun createForgetPassFragment(): ForgetPassFragment {
    return ForgetPassFragment()
}
