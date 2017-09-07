package com.drive.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drive.finance.base.BaseFragment

class TeamAtlasFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_team_atlas, container, false)
    }

}

fun createTeamAtlasFragment() : TeamAtlasFragment {
    return TeamAtlasFragment()
}
