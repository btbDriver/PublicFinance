package com.drive.finance.ui.drawer.center

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.ui.drawer.user.createUserFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FinancePickFragment : BaseFragment() {
    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val bankUserText by lazy {
        view?.findViewById(R.id.bankUserText) as TextView
    }
    val bankText by lazy {
        view?.findViewById(R.id.bankText) as TextView
    }
    val bankCardText by lazy {
        view?.findViewById(R.id.bankCardText) as TextView
    }
    val prvoText by lazy {
        view?.findViewById(R.id.prvoText) as TextView
    }
    val cityText by lazy {
        view?.findViewById(R.id.cityText) as TextView
    }
    val telText by lazy {
        view?.findViewById(R.id.telText) as TextView
    }
    val bankAddressText by lazy {
        view?.findViewById(R.id.bankAddressText) as TextView
    }
    val commitInfoLayout by lazy {
        view?.findViewById(R.id.commitInfoLayout) as LinearLayout
    }
    val pickNowLayout by lazy {
        view?.findViewById(R.id.pickNowLayout) as LinearLayout
    }
    val treadPassEdit by lazy {
        view?.findViewById(R.id.treadPassEdit) as EditText
    }
    val moneyEdit by lazy {
        view?.findViewById(R.id.moneyEdit) as EditText
    }

    val apiClient by lazy {
        APIClient()
    }

    lateinit var bankObject: JSONObject
    lateinit var sender: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_finance_pick, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        commitInfoLayout.onClick {
            start(createUserFragment())
        }

        pickNowLayout.onClick {
            try {
                if (TextUtils.isEmpty(moneyEdit.text.toString())) {
                    Toast.makeText(context, "请输入提取金额", Toast.LENGTH_SHORT).show()
                    return@onClick
                }
                if (TextUtils.isEmpty(treadPassEdit.text.toString())) {
                    Toast.makeText(context, "请输入资金密码", Toast.LENGTH_SHORT).show()
                    return@onClick
                }

                apiClient.sendPickmoney(bankObject.getString("id"), treadPassEdit.text.toString(), moneyEdit.text.toString(), sender)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ resultModel ->
                            if (resultModel.success == 0) {
                                Toast.makeText(context, "提现成功", Toast.LENGTH_SHORT).show()
                                pop()
                            } else {
                                Toast.makeText(context, resultModel.info, Toast.LENGTH_SHORT).show()
                            }
                        }, {})
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            bankUserText.text = bankObject.getString("bankuser")
            bankText.text = "【" + bankObject.getString("bank") + "】"
            bankCardText.text = bankObject.getString("bankcard")
            prvoText.text = bankObject.getString("prvo")
            cityText.text = bankObject.getString("city")
            telText.text = bankObject.getString("tel")
            bankAddressText.text = bankObject.getString("bankaddress")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun createFinancePickFragment(bankObject: JSONObject, sender: String): FinancePickFragment {
    val financePickFragment = FinancePickFragment()
    financePickFragment.bankObject = bankObject
    financePickFragment.sender = sender
    return financePickFragment
}
