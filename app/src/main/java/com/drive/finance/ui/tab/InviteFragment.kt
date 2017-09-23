package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class InviteFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val userNameText by lazy {
        view?.findViewById(R.id.userNameText) as TextView
    }
    val inviteLayout by lazy {
        view?.findViewById(R.id.inviteLayout) as LinearLayout
    }
    val ecodeLayout by lazy {
        view?.findViewById(R.id.ecodeLayout) as FrameLayout
    }
    val ecodeImage by lazy {
        view?.findViewById(R.id.ecodeImage) as ImageView
    }
    val apiClient by lazy {
        APIClient()
    }

    var shareObject: JSONObject ?=  null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_invite, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        inviteLayout.onClick {
            if (shareObject != null) {
                ecodeLayout.visibility = View.VISIBLE
                val ecodeUrl = shareObject!!.getString("srcurl")
                Glide.with(this)
                        .load(ecodeUrl)
                        .into(ecodeImage)
            }
        }

        ecodeLayout.onClick {
            ecodeLayout.visibility = View.GONE
        }

        apiClient.requestShareData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonObject ->
                    shareObject = jsonObject
                    userNameText.text = shareObject!!.getString("username")
                }, {})
    }
}

fun createInviteFragment(): InviteFragment {
    return InviteFragment()
}
