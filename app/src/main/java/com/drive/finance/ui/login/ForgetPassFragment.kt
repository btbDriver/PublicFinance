package com.drive.finance.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class ForgetPassFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val nextPlaceText by lazy {
        view?.findViewById(R.id.nextPlaceText) as TextView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
    }
}

fun createForgetPassFragment(): ForgetPassFragment {
    return ForgetPassFragment()
}
