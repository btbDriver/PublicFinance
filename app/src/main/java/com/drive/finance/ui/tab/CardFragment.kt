package com.drive.finance.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

class CardFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val submitText by lazy {
        view?.findViewById(R.id.submitText) as TextView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments.getBoolean("isTitleBarVisible")) {
            simpleTitleBar.visibility = View.VISIBLE

            simpleTitleBar.backLayout!!.onClick {
                pop()
            }
        } else {
            simpleTitleBar.visibility = View.GONE
        }

        submitText.onClick {
            Toast.makeText(context, "您还未达到申请条件", Toast.LENGTH_SHORT).show()
        }
    }
}

fun createCardFragment(isTitleBarVisible: Boolean) : CardFragment {
    val cardFragment = CardFragment()
    cardFragment.arguments = Bundle()
    cardFragment.arguments.putBoolean("isTitleBarVisible", isTitleBarVisible)
    return cardFragment
}
