package com.drive.finance.ui.drawer.consult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class ConsultFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val consultInfoLayout by lazy {
        view?.findViewById(R.id.consultInfoLayout) as RelativeLayout
    }
    val consultFinanceLayout by lazy {
        view?.findViewById(R.id.consultFinanceLayout) as RelativeLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_consult, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        /**
         * 公司简介
         */
        consultInfoLayout.onClick {
            start(createConsultInfoFragment())
        }

        /**
         * 理财政策
         */
        consultFinanceLayout.onClick {
            start(createConsultFinanceFragment())
        }
    }
}

fun createConsultFragment() : ConsultFragment {
    return ConsultFragment()
}