package com.drive.finance.ui.tab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.drive.finance.R
import com.drive.finance.base.BaseFragment

class BonusListFragment : BaseFragment() {

    val recyclerView by lazy {
        view?.findViewById(R.id.recyclerView) as RecyclerView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_bonus_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = BonusListAdapter()
    }
}

class BonusListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_bonus_item, null, false)
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return BonusListViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }
}

class BonusListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

fun createBonusListFragment() : BonusListFragment {
    return BonusListFragment()
}