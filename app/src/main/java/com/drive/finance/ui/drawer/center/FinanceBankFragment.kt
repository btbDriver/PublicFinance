package com.drive.finance.ui.drawer.center

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.BankModel
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FinanceBankFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val backSpinner by lazy {
        view?.findViewById(R.id.backSpinner) as Spinner
    }
    val bankUserEdit by lazy {
        view?.findViewById(R.id.bankUserEdit) as EditText
    }
    val provEdit by lazy {
        view?.findViewById(R.id.provEdit) as EditText
    }
    val cityEdit by lazy {
        view?.findViewById(R.id.cityEdit) as EditText
    }
    val bankAddressEdit by lazy {
        view?.findViewById(R.id.bankAddressEdit) as EditText
    }
    val telEdit by lazy {
        view?.findViewById(R.id.telEdit) as EditText
    }
    val bankCardEdit by lazy {
        view?.findViewById(R.id.bankCardEdit) as EditText
    }
    val bankCardConfirmEdit by lazy {
        view?.findViewById(R.id.bankCardConfirmEdit) as EditText
    }
    val submitLayout by lazy {
        view?.findViewById(R.id.submitLayout) as LinearLayout
    }

    val apiClient by lazy {
        APIClient()
    }
    val bankArray = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_finance_bank, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        apiClient.requestSelectBankData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonArray ->
                    bankArray.clear()
                    bankArray.add("请选择")
                    for (i in 0..jsonArray.length() - 1) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        bankArray.add(jsonObject.getString("bank"))
                    }
                    val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, bankArray)
                    backSpinner.adapter = adapter
                }, {})

        submitLayout.onClick {
            val bankModel = BankModel()
            if (bankArray[backSpinner.selectedItemPosition] == "请选择") {
                Toast.makeText(context, "请选择开户行", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (TextUtils.isEmpty(bankUserEdit.text.toString())) {
                Toast.makeText(context, "请填写开户人姓名", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (TextUtils.isEmpty(provEdit.text.toString())) {
                Toast.makeText(context, "请填写银行卡省份", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (TextUtils.isEmpty(cityEdit.text.toString())) {
                Toast.makeText(context, "请填写银行卡城市", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (bankCardEdit.text.toString() != bankCardConfirmEdit.text.toString()) {
                Toast.makeText(context, "两次填写银行卡号不一致", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (TextUtils.isEmpty(bankCardEdit.text.toString())) {
                Toast.makeText(context, "请填写银行卡号", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            if (bankArray[backSpinner.selectedItemPosition] != "请选择") {
                bankModel.bank = bankArray[backSpinner.selectedItemPosition]
            }
            bankModel.bankuser = bankUserEdit.text.toString()
            bankModel.prvo = provEdit.text.toString()
            bankModel.city = cityEdit.text.toString()
            bankModel.bankaddress = bankAddressEdit.text.toString()
            bankModel.tel = telEdit.text.toString()
            bankModel.bankcard = bankCardEdit.text.toString()

            apiClient.sendAddBank(bankModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ resultModel ->
                        if (resultModel.success == 0) {
                            Toast.makeText(context, "添加银行卡信息成功", Toast.LENGTH_SHORT).show()
                            pop()
                        } else {
                            Toast.makeText(context, resultModel.info, Toast.LENGTH_SHORT).show()
                        }
                    }, {})

        }
    }
}

fun createFinanceBankFragment(): FinanceBankFragment {
    return FinanceBankFragment()
}
