package com.drive.finance.ui.drawer

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class FinanceCenterFragment : BaseFragment() {

    val financeTabLayout by lazy {
        view?.findViewById(R.id.financeTabLayout) as TabLayout
    }
    val viewPager by lazy {
        view?.findViewById(R.id.viewPager) as ViewPager
    }
    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_finance_center, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        financeTabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = FinanceCenterPageAdapter(childFragmentManager)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }
    }
}

class FinanceCenterPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val titles = arrayOf("奖金列表", "收益列表", "定期理财", "活期理财")

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return createConsultFragment()
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}

fun createFinanceCenterFragment() : FinanceCenterFragment {
    return FinanceCenterFragment()
}
