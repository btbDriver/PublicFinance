package com.drive.finance.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.tab.*
import org.jetbrains.anko.onClick


class TabHostFragment : BaseFragment() {

    val mainLayout by lazy {
        view?.findViewById(R.id.mainLayout) as LinearLayout
    }
    val financeLayout by lazy {
        view?.findViewById(R.id.financeLayout) as LinearLayout
    }
    val mineLayout by lazy {
        view?.findViewById(R.id.mineLayout) as LinearLayout
    }
    val cardLayout by lazy {
        view?.findViewById(R.id.cardLayout) as LinearLayout
    }
    lateinit var mainFragment: MainFragment
    lateinit var financeFragment: FinanceFragment
    lateinit var mineFragment: MineFragment
    lateinit var cardFragment: CardFragment

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragment = createMainFragment()
        financeFragment = createFinanceFragment()
        mineFragment = createMineFragment()
        cardFragment = createCardFragment()
        loadMultipleRootFragment(R.id.fragmentTabContent, 0, mainFragment, financeFragment, mineFragment, cardFragment)

        initView()
    }

    private fun initView() {
        mainLayout.onClick {
            showHideFragment(mainFragment)
        }

        financeLayout.onClick {
            showHideFragment(financeFragment)
        }

        mineLayout.onClick {
            showHideFragment(mineFragment)
        }

        cardLayout.onClick {
            showHideFragment(cardFragment)
        }
    }
}

fun createTabHostFragment() : TabHostFragment {
    return TabHostFragment()
}
