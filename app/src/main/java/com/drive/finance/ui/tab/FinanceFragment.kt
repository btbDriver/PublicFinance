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

class FinanceFragment : BaseFragment() {

    val recyclerView by lazy {
        view?.findViewById(R.id.recyclerView) as RecyclerView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FinanceAdapter()
    }
}

class FinanceAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 1
        } else if (position < 5) {
            return 2
        } else if (position == 5) {
            return 3
        } else if (position > 5) {
            return 4
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView: View
        if (viewType == 1) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item1, null, false)
        } else if (viewType == 2) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item2, null, false)
        } else if (viewType == 3) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item3, null, false)
        } else {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item2, null, false)
        }
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return BonusListViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }
}

class FinanceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


fun createFinanceFragment(): FinanceFragment {
    return FinanceFragment()
}
