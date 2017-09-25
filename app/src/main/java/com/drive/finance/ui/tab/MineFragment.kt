package com.drive.finance.ui.tab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.CreatePickFragmentEvent
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.widget.SimpleTitleBar
import com.drive.finance.widget.StatusLayout
import com.hwangjr.rxbus.RxBus
import org.jetbrains.anko.onClick
import org.json.JSONArray
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MineFragment : BaseFragment() {

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
    lateinit var mineListAdapter: MineListAdapter
    var isTitleBarVisible: Boolean = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isTitleBarVisible) {
            simpleTitleBar.visibility = View.VISIBLE
            simpleTitleBar.backLayout!!.onClick {
                pop()
            }
        } else {
            simpleTitleBar.visibility = View.GONE
        }

        containerLayout.errorLayout.onClick {
            refresh()
        }

        mineListAdapter = MineListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mineListAdapter

        refresh()
    }

    fun refresh() {
        apiClient.requestFinanceMineData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonArray ->
                    mineListAdapter.financeArray = jsonArray
                    mineListAdapter.notifyDataSetChanged()

                    if (mineListAdapter.financeArray == null || mineListAdapter.financeArray!!.length() == 0) {
                        containerLayout.showEmpty()
                    }
                }, {
                    containerLayout.showError()
                })
    }
}

class MineListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var financeArray: JSONArray ?= null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_mine_item, null, false)
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return MineListViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MineListViewHolder).setItems(financeArray!!.getJSONObject(position))
    }

    override fun getItemCount(): Int {
        if (financeArray == null) {
            return 0
        } else {
            return financeArray!!.length()
        }
    }
}

class MineListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleText by lazy {
        itemView.findViewById(R.id.titleText) as TextView
    }
    val typeText by lazy {
        itemView.findViewById(R.id.typeText) as TextView
    }
    val fbText by lazy {
        itemView.findViewById(R.id.fbText) as TextView
    }
    val openText by lazy {
        itemView.findViewById(R.id.openText) as TextView
    }
    val moneyText by lazy {
        itemView.findViewById(R.id.moneyText) as TextView
    }
    val dayIncomeText by lazy {
        itemView.findViewById(R.id.dayIncomeText) as TextView
    }
    val daysText by lazy {
        itemView.findViewById(R.id.daysText) as TextView
    }
    val incomeDaysText by lazy {
        itemView.findViewById(R.id.incomeDaysText) as TextView
    }
    val totalIncomeText by lazy {
        itemView.findViewById(R.id.totalIncomeText) as TextView
    }
    val submitText by lazy {
        itemView.findViewById(R.id.submitText) as TextView
    }

    fun setItems(financeObject: JSONObject) {
        titleText.text = financeObject.getString("title")
        if (financeObject.getString("type") == "1") {
            typeText.text = "定期"
            fbText.visibility = View.VISIBLE
        } else {
            typeText.text = "活期"
            fbText.visibility = View.GONE
        }
        if (financeObject.getString("open") == "1") {
            openText.text = "已支付"
        } else {
            openText.text = "未支付"
        }
        moneyText.text = financeObject.getString("money") + "$"
        dayIncomeText.text = financeObject.getString("interest") + "$"
        daysText.text = financeObject.getString("days")
        incomeDaysText.text = financeObject.getString("doday")
        totalIncomeText.text = financeObject.getString("total")

        submitText.onClick {
            RxBus.get().post(CreatePickFragmentEvent("0"))
        }
    }
}

fun createMineFragment(isTitleBarVisible: Boolean): MineFragment {
    val mineFragment = MineFragment()
    mineFragment.isTitleBarVisible = isTitleBarVisible
    return mineFragment
}
