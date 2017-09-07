package com.drive.finance

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.base.BaseFragment

class TeamFragment : BaseFragment() {

    val teamTabLayout by lazy {
        view?.findViewById(R.id.teamTabLayout) as TabLayout
    }
    val viewPager by lazy {
        view?.findViewById(R.id.viewPager) as ViewPager
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamTabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = TeamPageAdapter(childFragmentManager)
    }

}

class TeamPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val titles = arrayOf("推荐列表", "推荐图谱")

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return createTeamRecommendFragment()
        } else {
            return createTeamAtlasFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}

fun createTeamFragment() : TeamFragment {
    return TeamFragment()
}
