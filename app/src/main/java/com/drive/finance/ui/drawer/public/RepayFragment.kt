package com.drive.finance.ui.drawer.public

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import com.drive.finance.widget.StatusLayout
import org.jetbrains.anko.onClick

class RepayFragment : BaseFragment() {

    val containerLayout by lazy {
        view?.findViewById(R.id.containerLayout) as StatusLayout
    }
    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_repay, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        containerLayout.showEmpty()
    }
}

fun createPepayFragment(): RepayFragment {
    return RepayFragment()
}
