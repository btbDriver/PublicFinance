package com.drive.finance.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class LoginFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }
    }
}

fun createLoginFragment(): LoginFragment {
    return LoginFragment()
}