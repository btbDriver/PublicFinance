package com.drive.finance.ui.tab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.widget.SimpleTitleBar
import org.jetbrains.anko.onClick
import org.json.JSONArray
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class BonusListFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val recyclerView by lazy {
        view?.findViewById(R.id.recyclerView) as RecyclerView
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_bonus_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        recyclerView.adapter = BonusListAdapter()

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }
    }
}

class BonusListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val apiClient by lazy {
        APIClient()
    }
    var dataArray = JSONArray()
    init {
        apiClient.requestCenterBonusData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonArray ->
                    dataArray = jsonArray
                    notifyDataSetChanged()
                }, {})
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_bonus_item, null, false)
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return BonusListViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as BonusListViewHolder).setItem(dataArray.getJSONObject(position))
    }

    override fun getItemCount(): Int {
        return dataArray.length()
    }
}

class BonusListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val bonusData by lazy {
        itemView.findViewById(R.id.bonusData) as TextView
    }
    val userBonusText by lazy {
        itemView.findViewById(R.id.userBonusText) as TextView
    }
    val dayIncomeText by lazy {
        itemView.findViewById(R.id.dayIncomeText) as TextView
    }
    val managerBonusText by lazy {
        itemView.findViewById(R.id.managerBonusText) as TextView
    }
    val allinText by lazy {
        itemView.findViewById(R.id.allinText) as TextView
    }
    val gpText by lazy {
        itemView.findViewById(R.id.gpText) as TextView
    }
    val rebuyText by lazy {
        itemView.findViewById(R.id.rebuyText) as TextView
    }

    fun setItem(dataObject: JSONObject) {
        bonusData.text = dataObject.getString("date")
        userBonusText.text = dataObject.getDouble("101").toString() + "$"
        dayIncomeText.text = dataObject.getDouble("102").toString() + "$"
        managerBonusText.text = dataObject.getDouble("107").toString() + "$"
        allinText.text = dataObject.getDouble("allin").toString() + "$"
        gpText.text = dataObject.getDouble("gp").toString() + "$"
        rebuyText.text = dataObject.getDouble("rebuy").toString()
    }
}

fun createBonusListFragment() : BonusListFragment {
    return BonusListFragment()
}