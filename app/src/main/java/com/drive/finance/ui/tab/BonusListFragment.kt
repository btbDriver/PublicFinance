package com.drive.finance.ui.tab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.CreateBonusInfoFragmentEvent

import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.ui.drawer.center.createBonusInfoFragment
import com.drive.finance.widget.SimpleTitleBar
import com.drive.finance.widget.StatusLayout
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
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
    val containerLayout by lazy {
        view?.findViewById(R.id.containerLayout) as StatusLayout
    }
    val apiClient by lazy {
        APIClient()
    }
    lateinit var bonusListAdapter: BonusListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_bonus_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        bonusListAdapter = BonusListAdapter()
        recyclerView.adapter = bonusListAdapter

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        try {
            apiClient.requestCenterBonusData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonArray ->
                        bonusListAdapter.dataArray = jsonArray
                        bonusListAdapter.notifyDataSetChanged()

                        if (jsonArray == null || jsonArray.length() == 0) {
                            containerLayout.showEmpty()
                        }
                    }, {})
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Subscribe
    fun onCreateBonusInfoFragmentEvent(event: CreateBonusInfoFragmentEvent) {
        start(createBonusInfoFragment(event.date))
    }
}

class BonusListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataArray = JSONArray()
    init {

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
    val detailText by lazy {
        itemView.findViewById(R.id.detailText) as TextView
    }

    fun setItem(dataObject: JSONObject) {
        bonusData.text = dataObject.getString("date")
        userBonusText.text = dataObject.getDouble("101").toString() + "$"
        dayIncomeText.text = dataObject.getDouble("102").toString() + "$"
        managerBonusText.text = dataObject.getDouble("107").toString() + "$"
        allinText.text = dataObject.getDouble("allin").toString() + "$"
        gpText.text = dataObject.getDouble("gp").toString() + "$"
        rebuyText.text = dataObject.getDouble("rebuy").toString()

        detailText.onClick {
            RxBus.get().post(CreateBonusInfoFragmentEvent(dataObject.getString("date")))
        }
    }
}

fun createBonusListFragment() : BonusListFragment {
    return BonusListFragment()
}