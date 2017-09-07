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

class ConsultFragment : BaseFragment() {

    val consultTabLayout by lazy {
        view?.findViewById(R.id.consultTabLayout) as TabLayout
    }
    val viewPager by lazy {
        view?.findViewById(R.id.viewPager) as ViewPager
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_consult, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consultTabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = ConsultPageAdapter(childFragmentManager)
    }
}

class ConsultPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val titles = arrayOf("系统公告", "理财政策", "公司简介")

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return createTeamRecommendFragment()
        } else {
            return createTeamAtlasFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}

fun createConsultFragment() : ConsultFragment {
    return ConsultFragment()
}