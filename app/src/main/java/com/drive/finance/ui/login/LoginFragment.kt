package com.drive.finance.ui.login

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.drive.finance.LoginSuccessEvent
import com.drive.finance.R

import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.widget.SimpleTitleBar
import com.hwangjr.rxbus.RxBus
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class LoginFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val forgetPassText by lazy {
        view?.findViewById(R.id.forgetPassText) as TextView
    }
    val loginText by lazy {
        view?.findViewById(R.id.loginText) as TextView
    }
    val userNameEdit by lazy {
        view?.findViewById(R.id.userNameEdit) as EditText
    }
    val passwordEdit by lazy {
        view?.findViewById(R.id.passwordEdit) as EditText
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
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backImage!!.visibility = View.GONE

        forgetPassText.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        forgetPassText.onClick {
            start(createForgetPassFragment())
        }

        loginText.onClick {
            try {
                if (TextUtils.isEmpty(userNameEdit.text.toString())) {
                    Toast.makeText(context, "请输入客户编号", Toast.LENGTH_SHORT).show()
                    return@onClick
                }
                if (TextUtils.isEmpty(passwordEdit.text.toString())) {
                    Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show()
                    return@onClick
                }

                apiClient.requestLogin(userNameEdit.text.toString(), passwordEdit.text.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ jsonObject ->
                            if (jsonObject.getInt("success") == 0) {
                                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                                pop()
                                RxBus.get().post(LoginSuccessEvent(jsonObject.getString("uid"), jsonObject.getString("username")))
                            } else {
                                Toast.makeText(context, jsonObject.getString("info"), Toast.LENGTH_SHORT).show()
                            }
                        }, {})
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        codeImage.onClick {
            apiClient.requestCode()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonObject ->
                        Glide.with(context)
                                .load(jsonObject.getString("url"))
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(codeImage)
                    }, {})
        }

        apiClient.requestCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonObject ->
                     Glide.with(context)
                             .load(jsonObject.getString("url"))
                             .into(codeImage)
                }, {})
    }
}

fun createLoginFragment(): LoginFragment {
    return LoginFragment()
}