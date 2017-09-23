package com.drive.finance.ui.drawer.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.MyInfo
import com.drive.finance.network.model.UserModel
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val userNumEdit by lazy {
        view?.findViewById(R.id.userNumEdit) as TextView
    }
    val realNameEdit by lazy {
        view?.findViewById(R.id.realNameEdit) as TextView
    }
    val cardStatus by lazy {
        view?.findViewById(R.id.cardStatus) as TextView
    }
    val backCardEdit by lazy {
        view?.findViewById(R.id.backCardEdit) as EditText
    }
    val backAddressEdit by lazy {
        view?.findViewById(R.id.backAddressEdit) as EditText
    }
    val telEdit by lazy {
        view?.findViewById(R.id.telEdit) as EditText
    }
    val emailEdit by lazy {
        view?.findViewById(R.id.emailEdit) as EditText
    }
    val backSpinner by lazy {
        view?.findViewById(R.id.backSpinner) as Spinner
    }
    val sendCodeText by lazy {
        view?.findViewById(R.id.sendCodeText) as TextView
    }
    val submitText by lazy {
        view?.findViewById(R.id.submitText) as TextView
    }
    val pEdit by lazy {
        view?.findViewById(R.id.pEdit) as EditText
    }
    val smsCodeEdit by lazy {
        view?.findViewById(R.id.smsCodeEdit) as EditText
    }
    val bankArray = ArrayList<String>()

    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        /**
         * 发送短信验证码
         */
        sendCodeText.onClick {
            apiClient.sendMobileCode(telEdit.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ mobileCodeModel ->
                        if (mobileCodeModel.success == 0) {
                            Toast.makeText(activity, "发送短信验证码成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, mobileCodeModel.info, Toast.LENGTH_SHORT).show()
                        }
                    }, {})
        }

        /**
         * 更新个人信息
         */
        submitText.onClick {
            val userModel = UserModel()
            userModel.myinfo = MyInfo()
            if (bankArray[backSpinner.selectedItemPosition] != "请选择") {
                userModel.myinfo.bank = bankArray[backSpinner.selectedItemPosition]
            }
            userModel.myinfo.bankaddress = backAddressEdit.text.toString()
            userModel.myinfo.bankcard = backCardEdit.text.toString()
            userModel.myinfo.email = emailEdit.text.toString()
            userModel.myinfo.p = pEdit.text.toString()
            userModel.myinfo.smscode = smsCodeEdit.text.toString()
            userModel.myinfo.tel = telEdit.text.toString()

            apiClient.sendUpdateUser(userModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ resultModel ->
                        if (resultModel.success == 0) {
                            Toast.makeText(activity, "更新个人信息成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, resultModel.info, Toast.LENGTH_SHORT).show()
                        }
                    }, {})

        }

        apiClient.requestUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userModel ->
                    updateUI(userModel)
                }, {})
    }

    fun updateUI(userModel: UserModel) {
        userNumEdit.text = userModel.myinfo.username
        realNameEdit.text = userModel.myinfo.realname
        if (userModel.banklist != null && userModel.banklist.size > 0) {
            cardStatus.text = "已提交"
        } else {
            cardStatus.text = "未提交"
        }

        backAddressEdit.setText(userModel.myinfo.bankaddress)
        backCardEdit.setText(userModel.myinfo.bankcard)
        telEdit.setText(userModel.myinfo.tel)
        emailEdit.setText(userModel.myinfo.email)

        if (userModel.banklist != null && userModel.banklist.size > 0) {
            bankArray.clear()
            bankArray.add("请选择")
            for (i in 0..userModel.banklist.size - 1) {
                bankArray.add(userModel.banklist[i].bank)
            }
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, bankArray)
            backSpinner.adapter = adapter
        }
    }
}

fun createUserFragment(): UserFragment {
    return UserFragment()
}
