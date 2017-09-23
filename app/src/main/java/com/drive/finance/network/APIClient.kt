package com.drive.finance.network

import com.drive.finance.network.model.*
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import rx.Observable

/**
 * Created by aaron on 2017/9/22.
 */
class APIClient {
    val netClient: NetClient = NetClient()

    /**
     * 主页面请求接口
     */
    fun requestHomeData() : Observable<HomeModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequest(BaseUrl.Home_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), HomeModel::class.java)
                }

    }

    /**
     * 个人信息请求接口
     */
    fun requestUserData() : Observable<UserModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequest(BaseUrl.USER_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), UserModel::class.java)
                }
    }

    /**
     * 团队管理-推荐列表请求接口
     */
    fun requestTeamRecommendData() : Observable<RecommendModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequest(BaseUrl.TEAM_RECOMMEND_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), RecommendModel::class.java)
                }
    }

    /**
     * 团队管理-业绩请求接口
     */
    fun requestTeamYjData() : Observable<YJModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequest(BaseUrl.TEAM_YJ_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), YJModel::class.java)
                }
    }

    /**
     * 财务中心-奖金列表接口
     */
    fun requestCenterBonusData() : Observable<JSONArray> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequestArray(BaseUrl.CENTER_BONUS_API, paramsMap)
    }


    /**
     * 公司简介
     */
    fun requestConsultInfoData(): Observable<ConsultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("id", "68")
        return netClient.doGetRequest(BaseUrl.CONSULT_INFO_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ConsultModel::class.java)
                }

    }

    /**
     * 公司简介
     */
    fun requestConsultFinanceData(): Observable<ConsultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("id", "67")
        return netClient.doGetRequest(BaseUrl.CONSULT_INFO_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ConsultModel::class.java)
                }

    }
}