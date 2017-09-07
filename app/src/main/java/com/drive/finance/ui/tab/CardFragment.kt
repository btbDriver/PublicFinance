package com.drive.finance.ui.tab

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

class CardFragment : BaseFragment() {

    val cardTabLayout by lazy {
        view?.findViewById(R.id.cardTabLayout) as TabLayout
    }
    val viewPager by lazy {
        view?.findViewById(R.id.viewPager) as ViewPager
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardTabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = CardPageAdapter(childFragmentManager)
    }
}

class CardPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val titles = arrayOf("关于众卡贷", "我的众卡贷")

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return createCardAboutFragment()
        } else {
            return createCardListFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}

fun createCardFragment() : CardFragment {
    return CardFragment()
}
