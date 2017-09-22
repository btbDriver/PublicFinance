package com.drive.finance.ui.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.ui.tab.createPickFragment
import com.drive.finance.ui.tab.createBonusListFragment
import com.drive.finance.ui.tab.createFinanceFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class FinanceCenterFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val financeCenterLayout by lazy {
        view?.findViewById(R.id.financeCenterLayout) as RelativeLayout
    }
    val financeIncomeLayout by lazy {
        view?.findViewById(R.id.financeIncomeLayout) as RelativeLayout
    }
    val financeAwardLayout by lazy {
        view?.findViewById(R.id.financeAwardLayout) as RelativeLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_finance_center, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        financeCenterLayout.onClick {
            start(createBonusListFragment())
        }

        financeIncomeLayout.onClick {
            start(createPickFragment())
        }

        financeAwardLayout.onClick {
            start(createFinanceFragment(true))
        }
    }
}

fun createFinanceCenterFragment() : FinanceCenterFragment {
    return FinanceCenterFragment()
}
