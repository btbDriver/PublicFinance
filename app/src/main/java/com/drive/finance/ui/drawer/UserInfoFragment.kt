package com.drive.finance.ui.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick


class UserInfoFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

    }
}

fun createUserInfoFragment(): UserInfoFragment {
    return UserInfoFragment()
}
