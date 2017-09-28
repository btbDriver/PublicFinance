package com.drive.finance.ui.drawer.team

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
import com.drive.finance.ui.tab.MineListAdapter
import com.drive.finance.widget.SimpleTitleBar
import com.drive.finance.widget.StatusLayout
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.json.JSONArray
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 团队管理-推荐列表
 */
class RecommendFragment : BaseFragment() {

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
    lateinit var recommendAdapter: RecommendAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleTitleBar.backLayout!!.onClick {
            pop()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recommendAdapter = RecommendAdapter()
        recyclerView.adapter = recommendAdapter

        refresh()
    }

    fun refresh() {
        try {
            apiClient.requestTeamRecommendData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ jsonArray ->
                        recommendAdapter.recommendArray = jsonArray
                        recommendAdapter.notifyDataSetChanged()

                        if (jsonArray == null || jsonArray.length() == 0) {
                            containerLayout.showEmpty()
                        }
                    }, {})
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


class RecommendAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var recommendArray: JSONArray ?= null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_recommend_item, null, false)
        rootView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return RecommendViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as RecommendViewHolder).setItems(recommendArray!!.getJSONObject(position))
    }

    override fun getItemCount(): Int {
        if (recommendArray == null) {
            return 0
        } else {
            return recommendArray!!.length()
        }
    }
}

class RecommendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val userNumText by lazy {
        itemView.findViewById(R.id.userNumText) as TextView
    }
    val realNameText by lazy {
        itemView.findViewById(R.id.realNameText) as TextView
    }
    val telText by lazy {
        itemView.findViewById(R.id.telText) as TextView
    }
    val renumText by lazy {
        itemView.findViewById(R.id.renumText) as TextView
    }
    val registerTimeText by lazy {
        itemView.findViewById(R.id.registerTimeText) as TextView
    }
    val loginTimeText by lazy {
        itemView.findViewById(R.id.loginTimeText) as TextView
    }
    val statusText by lazy {
        itemView.findViewById(R.id.statusText) as TextView
    }
    val yjText by lazy {
        itemView.findViewById(R.id.yjText) as TextView
    }

    fun setItems(recommendObject: JSONObject) {
        try {
            userNumText.text = recommendObject.getString("username")
            realNameText.text = recommendObject.getString("realname")
            telText.text = recommendObject.getString("tel")
            renumText.text = recommendObject.getString("renum")
            registerTimeText.text = recommendObject.getString("regdate")
            loginTimeText.text = recommendObject.getString("logindate")
            yjText.text = recommendObject.getString("yeji") + "$"
            if (recommendObject.getString("status") == "1") {
                statusText.text = "是"
            } else {
                statusText.text = "否"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun createRecommendFragment(): RecommendFragment {
    return RecommendFragment()
}
