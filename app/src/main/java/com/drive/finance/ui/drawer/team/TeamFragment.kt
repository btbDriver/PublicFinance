package com.drive.finance.ui.drawer.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick

class TeamFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val teamSuggestLayout by lazy {
        view?.findViewById(R.id.teamSuggestLayout) as RelativeLayout
    }
    val teamChartLayout by lazy {
        view?.findViewById(R.id.teamChartLayout) as RelativeLayout
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        /**
         * 推荐列表
         */
        teamSuggestLayout.onClick {
            start(createRecommendFragment())
        }

        teamChartLayout.onClick {
            start(createChartFragment())
        }
    }

}

fun createTeamFragment() : TeamFragment {
    return TeamFragment()
}
