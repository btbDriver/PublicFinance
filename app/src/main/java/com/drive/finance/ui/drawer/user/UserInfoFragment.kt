package com.drive.finance.ui.drawer.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.ui.tab.createInviteFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick


class UserInfoFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val userInfoLayout by lazy {
        view?.findViewById(R.id.userInfoLayout) as RelativeLayout
    }
    val updateLoginLayout by lazy {
        view?.findViewById(R.id.updateLoginLayout) as RelativeLayout
    }
    val updateTreadLayout by lazy {
        view?.findViewById(R.id.updateTreadLayout) as RelativeLayout
    }
    val passProtectLayout by lazy {
        view?.findViewById(R.id.passProtectLayout) as RelativeLayout
    }
    val inviteLayout by lazy {
        view?.findViewById(R.id.inviteLayout) as RelativeLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        /**
         * 个人信息
         */
        userInfoLayout.onClick {
            start(createUserFragment())
        }

        /**
         * 更新登录密码
         */
        updateLoginLayout.onClick {
            start(createUpdateLoginFragment())
        }

        /**
         * 更新交易密码
         */
        updateTreadLayout.onClick {
            start(createUpdateTreadFragment())
        }

        /**
         * 密码保护
         */
        passProtectLayout.onClick {
            start(createPassProtectFragment())
        }

        /**
         * 邀请链接
         */
        inviteLayout.onClick {
            start(createInviteFragment())
        }
    }
}

fun createUserInfoFragment(): UserInfoFragment {
    return UserInfoFragment()
}
