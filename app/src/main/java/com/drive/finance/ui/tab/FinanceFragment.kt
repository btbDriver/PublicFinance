package com.drive.finance.ui.tab

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.drive.finance.CreatePayFragmentEvent
import com.drive.finance.R
import com.drive.finance.base.BaseFragment
import com.drive.finance.network.APIClient
import com.drive.finance.network.model.Finance
import com.drive.finance.network.model.FinanceModel
import com.drive.finance.widget.SimpleTitleBar
import com.hwangjr.rxbus.RxBus
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FinanceFragment : BaseFragment() {

    val simpleTitleBar by lazy {
        view?.findViewById(R.id.simpleTitleBar) as SimpleTitleBar
    }
    val recyclerView by lazy {
        view?.findViewById(R.id.recyclerView) as RecyclerView
    }
    val apiClient by lazy {
        APIClient()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val financeAdapter = FinanceAdapter()
        recyclerView.adapter = financeAdapter

        if (arguments.getBoolean("isTitleBarVisible")) {
            simpleTitleBar.visibility = View.VISIBLE

            simpleTitleBar.backLayout!!.onClick {
                pop()
            }
        } else {
            simpleTitleBar.visibility = View.GONE
        }

        try {
            apiClient.requestCenterFinanceData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ financemodel ->
                        financeAdapter.financeModel = financemodel
                        financeAdapter.notifyDataSetChanged()
                    }, {})
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

class FinanceAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var financeModel: FinanceModel ?= null

    override fun getItemViewType(position: Int): Int {
        if (financeModel == null) {
            if (position == 0) {
                return 1
            } else if (position == 1) {
                return 3
            }
        } else {
            if (position == 0) {
                return 1
            } else if (position <= financeModel!!.fin.size) {
                return 2
            } else if (position == financeModel!!.fin.size + 1) {
                return 3
            } else if (position > financeModel!!.fin.size + 1) {
                return 4
            }
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView: View
        if (viewType == 1) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item1, null, false)
            rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return FinanceViewHolder1(rootView)
        } else if (viewType == 2) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item2, null, false)
            rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return FinanceViewHolder2(rootView)
        } else if (viewType == 3) {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item3, null, false)
            rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return FinanceViewHolder1(rootView)
        } else {
            rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_finance_item2, null, false)
            rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            return FinanceViewHolder2(rootView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is FinanceViewHolder2) {
            if (position <= financeModel!!.fin.size) {
                (holder as FinanceViewHolder2).setItems(financeModel!!.fin[position - 1])
            } else if (position > financeModel!!.fin.size + 1) {
                (holder as FinanceViewHolder2).setItems(financeModel!!.fin1[position - financeModel!!.fin.size - 2])
            }
        }
    }

    override fun getItemCount(): Int {
        if (financeModel == null) {
            return 2
        } else {
            return 2 + financeModel!!.fin.size + financeModel!!.fin1.size
        }
    }
}

class FinanceViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView)

class FinanceViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView) {
    val partText by lazy {
        itemView.findViewById(R.id.partText) as TextView
    }
    val titleText by lazy {
        itemView.findViewById(R.id.titleText) as TextView
    }
    val statusText by lazy {
        itemView.findViewById(R.id.statusText) as TextView
    }
    val dayIncomeText by lazy {
        itemView.findViewById(R.id.dayIncomeText) as TextView
    }

    val apiClient by lazy {
        APIClient()
    }

    fun setItems(finance: Finance) {

        titleText.text = finance.title
        if (finance.status == "0") {
            statusText.text = "OPEN"
        } else {
            statusText.text = "CLOSE"
        }
        dayIncomeText.text = finance.interest

        partText.onClick {
            val builder = AlertDialog.Builder(itemView.context)
            val rootView = LayoutInflater.from(itemView.context).inflate(R.layout.finance_part_layout, null, false)
            (rootView.findViewById(R.id.nameText) as TextView).text = finance.title
            var dialog: AlertDialog ?= null
            rootView.findViewById(R.id.closeDialog).onClick {
                dialog?.dismiss()
            }
            rootView.findViewById(R.id.submitDialog).onClick {
                try {
                    val money = (rootView.findViewById(R.id.moneyEdit) as EditText).text.toString()
                    if (TextUtils.isEmpty(money)) {
                        Toast.makeText(partText.context, "请输入金额", Toast.LENGTH_SHORT).show()
                        return@onClick
                    }
                    apiClient.requestPayInfoData(finance.id, money)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ payModel ->
                                dialog?.dismiss()
                                payModel.goodsId = finance.id
                                RxBus.get().post(CreatePayFragmentEvent(payModel))
                            }, {})
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            builder.setView(rootView)

            dialog = builder.show()
        }
    }
}


fun createFinanceFragment(isTitleBarVisible: Boolean): FinanceFragment {
    val financeFragment = FinanceFragment()
    financeFragment.arguments = Bundle()
    financeFragment.arguments.putBoolean("isTitleBarVisible", isTitleBarVisible)
    return financeFragment
}
