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
import com.drive.finance.ui.tab.createFinanceListFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class PublicFinanceFragment : BaseFragment() {

    val publicFinanceTabLayout by lazy {
        view?.findViewById(R.id.publicFinanceTabLayout) as TabLayout
    }
    val viewPager by lazy {
        view?.findViewById(R.id.viewPager) as ViewPager
    }
    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_public_finance, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        publicFinanceTabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = PublicFinancePageAdapter(childFragmentManager)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }
    }
}

class PublicFinancePageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val titles = arrayOf("众卡贷", "我的贷款", "我的投资", "我的还款")

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment {
        return createFinanceListFragment(titles[position])
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}

fun createPublicFinanceFragment(): PublicFinanceFragment {
    return PublicFinanceFragment()
}
