package com.drive.finance.tab

import android.support.design.widget.TabLayout
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.R
import com.drive.finance.base.BaseFragment

class FinanceFragment : BaseFragment() {

    val financeTabLayout by lazy {
        view?.findViewById(R.id.financeTabLayout) as TabLayout
    }
    val viewPager by lazy {
        view?.findViewById(R.id.viewPager) as ViewPager
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        financeTabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = FinancePageAdapter(childFragmentManager)
    }
}

class FinancePageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val titles = arrayOf("定期理财产品", "活期理财产品")

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = createFinanceListFragment(titles[position])

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}

fun createFinanceFragment(): FinanceFragment {
    return FinanceFragment()
}
