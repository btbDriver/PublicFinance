package com.drive.finance.ui.drawer.user

import android.os.Bundle
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

class UpdateTreadFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val originPassEdit by lazy {
        view?.findViewById(R.id.originPassEdit) as EditText
    }
    val newPassEdit by lazy {
        view?.findViewById(R.id.newPassEdit) as EditText
    }
    val confirmPassEdit by lazy {
        view?.findViewById(R.id.confirmPassEdit) as EditText
    }
    val submitText by lazy {
        view?.findViewById(R.id.submitText) as TextView
    }
    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_update_tread, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        submitText.onClick {
            try {
                val op = originPassEdit.text.toString()
                val p = newPassEdit.text.toString()
                val rp = confirmPassEdit.text.toString()
                apiClient.sendUpdateTreadPass(op, p, rp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ resultModel ->
                            if (resultModel.success == 0) {
                                Toast.makeText(activity, "更新密码成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, resultModel.info, Toast.LENGTH_SHORT).show()
                            }
                        }, {})
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun createUpdateTreadFragment(): UpdateTreadFragment {
    return UpdateTreadFragment()
}
