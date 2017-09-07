package com.drive.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.base.BaseFragment


class UserInfoFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user_info, container, false)
    }

}

fun createUserInfoFragment(): UserInfoFragment {
    return UserInfoFragment()
}
