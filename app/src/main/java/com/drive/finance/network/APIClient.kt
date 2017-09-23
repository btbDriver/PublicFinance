package com.drive.finance.network

import com.drive.finance.network.model.*
import com.google.gson.Gson
import org.json.JSONArray
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
     * 发送短信验证码
     */
    fun sendMobileCode(mobile: String): Observable<ResultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("mobile", "18614030612")
        return netClient.doGetRequest(BaseUrl.USER_SEND_CODE_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ResultModel::class.java)
                }
    }

    /**
     * 更新用户个人信息
     */
    fun sendUpdateUser(userModel: UserModel) : Observable<ResultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("tel", userModel.myinfo.tel)
        paramsMap.put("email", userModel.myinfo.email)
        paramsMap.put("bank", userModel.myinfo.bank)
        paramsMap.put("bankaddress", userModel.myinfo.bankaddress)
        paramsMap.put("bankcard", userModel.myinfo.bankcard)
        paramsMap.put("p", userModel.myinfo.p)
        paramsMap.put("smscode", userModel.myinfo.smscode)
        return netClient.doGetRequest(BaseUrl.UPDATE_USER_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ResultModel::class.java)
                }
    }

    /**
     * 更新登录密码
     */
    fun sendUpdateLoginPass(oldPass: String, newPass: String, confirmPass: String): Observable<ResultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("op", oldPass)
        paramsMap.put("p", newPass)
        paramsMap.put("rp", confirmPass)
        return netClient.doGetRequest(BaseUrl.UPDATE_LOGIN_PASS_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ResultModel::class.java)
                }
    }

    /**
     * 更新交易密码
     */
    fun sendUpdateTreadPass(oldPass: String, newPass: String, confirmPass: String): Observable<ResultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("op", oldPass)
        paramsMap.put("p", newPass)
        paramsMap.put("rp", confirmPass)
        return netClient.doGetRequest(BaseUrl.UPDATE_TREAD_PASS_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ResultModel::class.java)
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
     * 投资理财
     */
    fun requestCenterFinanceData(): Observable<FinanceModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequest(BaseUrl.CENTER_FINANCE_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), FinanceModel::class.java)
                }
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