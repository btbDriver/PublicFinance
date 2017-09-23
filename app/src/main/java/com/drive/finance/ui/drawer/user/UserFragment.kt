package com.drive.finance.ui.drawer.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.UserModel
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.widget.ArrayAdapter



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
    val mobelEdit by lazy {
        view?.findViewById(R.id.mobelEdit) as EditText
    }
    val emailEdit by lazy {
        view?.findViewById(R.id.emailEdit) as EditText
    }
    val backSpinner by lazy {
        view?.findViewById(R.id.backSpinner) as Spinner
    }

    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
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
        mobelEdit.setText(userModel.myinfo.tel)
        emailEdit.setText(userModel.myinfo.email)

        if (userModel.banklist != null && userModel.banklist.size > 0) {
            val array = ArrayList<String>()
            for (i in 0..userModel.banklist.size - 1) {
                array.add(userModel.banklist[i].bank)
            }
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, array)
            backSpinner.adapter = adapter
        }
    }
}

fun createUserFragment(): UserFragment {
    return UserFragment()
}
