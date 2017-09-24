package com.drive.finance.ui.tab

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drive.finance.CreateFinanceBankFragmentEvent
import com.drive.finance.CreateFinancePickFragmentEvent
import com.drive.finance.R
import com.drive.finance.RefreshBankListEvent
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.ui.drawer.center.createFinanceBankFragment
import com.drive.finance.ui.drawer.center.createFinancePickFragment
import com.drive.finance.widget.SimpleTitleBar
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import org.jetbrains.anko.onClick
import org.json.JSONArray
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PickFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val recyclerView by lazy {
        view?.findViewById(R.id.recyclerView) as RecyclerView
    }

    lateinit var sender: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_pick, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = BankAdapter()
        recyclerView.addItemDecoration(SpaceItemDecoration(10))
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            (recyclerView.adapter as BankAdapter).refresh()
        }
    }

    @Subscribe
    fun onCreateFinanceBankFragmentEvent(event: CreateFinanceBankFragmentEvent) {
        startForResult(createFinanceBankFragment(), 101)
    }

    @Subscribe
    fun onCreateFinancePickFragmentEvent(event: CreateFinancePickFragmentEvent) {
        startForResult(createFinancePickFragment(event.jsonObject, sender), 101)
    }

    @Subscribe
    fun onRefreshBankListEvent(event: RefreshBankListEvent) {
        (recyclerView.adapter as BankAdapter).refresh()
    }
}

class SpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space
    }
}

class BankAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val apiClient by lazy {
        APIClient()
    }
    var bankArray: JSONArray ?= null

    init {
        refresh()
    }

    fun refresh() {
        apiClient.requestBankData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonArray ->
                    bankArray = jsonArray
                    notifyDataSetChanged()
                }, {})
    }

    override fun getItemViewType(position: Int): Int {
        if (bankArray == null || bankArray!!.length() == 0) {
            return 2
        } else {
            if (position == bankArray!!.length()) {
                return 2
            } else {
                return 1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView: View
        if (viewType == 1) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_bank_item1, null, false)
            rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return BankViewHolder1(rootView)
        } else {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_bank_item2, null, false)
            rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return BankViewHolder2(rootView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is BankViewHolder1) {
            (holder as BankViewHolder1).setItems(bankArray!!.getJSONObject(position))
        } else {
            (holder as BankViewHolder2).setItems()
        }
    }

    override fun getItemCount(): Int {
        if (bankArray == null || bankArray!!.length() == 0) {
            return 1
        } else {
            return bankArray!!.length() + 1
        }
    }
}

class BankViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView) {

    val bankText by lazy {
        itemView.findViewById(R.id.bankText) as TextView
    }
    val bankUserText by lazy {
        itemView.findViewById(R.id.bankUserText) as TextView
    }
    val bankCardText by lazy {
        itemView.findViewById(R.id.bankCardText) as TextView
    }
    val delImageView by lazy {
        itemView.findViewById(R.id.delImageView) as ImageView
    }
    val apiClient by lazy {
        APIClient()
    }

    fun setItems(bankObject: JSONObject) {
        bankText.text = bankObject.getString("bank")
        bankUserText.text = bankObject.getString("bankuser")
        bankCardText.text = bankObject.getString("bankcard")

        itemView.onClick {
            RxBus.get().post(CreateFinancePickFragmentEvent(bankObject))
        }

        delImageView.onClick {
            apiClient.sendDelBank(bankObject.getString("id"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ resultModel ->
                        RxBus.get().post(RefreshBankListEvent(""))
                    }, {})
        }
    }
}

class BankViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun setItems() {
        itemView.onClick {
            RxBus.get().post(CreateFinanceBankFragmentEvent(""))
        }
    }
}


fun createPickFragment(sender: String): PickFragment {
    val pickFragment = PickFragment()
    pickFragment.sender = sender
    return pickFragment
}