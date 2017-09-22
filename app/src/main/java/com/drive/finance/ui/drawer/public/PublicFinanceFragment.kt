package com.drive.finance.ui.drawer.public

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.ui.drawer.team.createPointTreadFragment
import com.drive.finance.ui.tab.createCardFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class PublicFinanceFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val publicCardLayout by lazy {
        view?.findViewById(R.id.publicCardLayout) as RelativeLayout
    }
    val publicCreditLayout by lazy {
        view?.findViewById(R.id.publicCreditLayout) as RelativeLayout
    }
    val publicFinanceLayout by lazy {
        view?.findViewById(R.id.publicFinanceLayout) as RelativeLayout
    }
    val publicRepayLayout by lazy {
        view?.findViewById(R.id.publicRepayLayout) as RelativeLayout
    }
    val publicTreadLayout by lazy {
        view?.findViewById(R.id.publicTreadLayout) as RelativeLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_public_finance, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        publicCardLayout.onClick {
            start(createCardFragment(true))
        }

        publicCreditLayout.onClick {
            start(createPublicCreditFragment())
        }

        publicFinanceLayout.onClick {
            start(createInvestFragment())
        }

        publicRepayLayout.onClick {
            start(createPepayFragment())
        }

        publicTreadLayout.onClick {
            start(createPointTreadFragment())
        }
    }
}

fun createPublicFinanceFragment(): PublicFinanceFragment {
    return PublicFinanceFragment()
}
