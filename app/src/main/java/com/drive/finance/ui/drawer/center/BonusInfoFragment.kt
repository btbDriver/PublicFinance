package com.drive.finance.ui.drawer.center

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

class BonusInfoFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val recyclerView by lazy {
        view?.findViewById(R.id.recyclerView) as RecyclerView
    }
    lateinit var date: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_bonus_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = BonusInfoAdapter(date)
    }
}

class BonusInfoAdapter(date: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val apiClient by lazy {
        APIClient()
    }
    var bonusArray: JSONArray?= null

    init {
        try {
            apiClient.requestBonusInfoData(date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonArray ->
                        bonusArray = jsonArray
                        notifyDataSetChanged()
                    }, {})
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_bonus_info_item, null, false)
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return BonusViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as BonusViewHolder).setItems(bonusArray!!.getJSONObject(position))
    }

    override fun getItemCount(): Int {
        if (bonusArray == null) {
            return 0
        } else {
            return bonusArray!!.length()
        }
    }
}

class BonusViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleText by lazy {
        itemView.findViewById(R.id.titleText) as TextView
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
    val infoText by lazy {
        itemView.findViewById(R.id.infoText) as TextView
    }
    val dateText by lazy {
        itemView.findViewById(R.id.dateText) as TextView
    }

    fun setItems(bonusObject: JSONObject) {
        if (bonusObject.getString("type") == "101") {
            titleText.text = "客户佣金"
        } else if (bonusObject.getString("type") == "102") {
            titleText.text = "日收益"
        } else if (bonusObject.getString("type") == "107") {
            titleText.text = "经理佣金"
        }
        allinText.text = bonusObject.getString("allin")
        gpText.text = bonusObject.getString("gp")
        rebuyText.text = bonusObject.getString("rebuy")
        infoText.text = bonusObject.getString("info")
        dateText.text = bonusObject.getString("date")
    }
}

fun createBonusInfoFragment(date: String): BonusInfoFragment {
    val bonusInfoFragment = BonusInfoFragment()
    bonusInfoFragment.date = date
    return bonusInfoFragment
}