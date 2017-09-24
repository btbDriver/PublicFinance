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




    // ######################################################################
    /**
     * 团队管理-推荐列表请求接口
     */
    fun requestTeamRecommendData() : Observable<JSONArray> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequestArray(BaseUrl.TEAM_RECOMMEND_API, paramsMap)
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


    // ###############################################################
    /**
     * 财务中心-奖金列表接口
     */
    fun requestCenterBonusData() : Observable<JSONArray> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequestArray(BaseUrl.CENTER_BONUS_API, paramsMap)
    }

    /**
     * 财务中心-奖金详情
     */
    fun requestBonusInfoData(date: String) : Observable<JSONArray> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("date", date)
        return netClient.doGetRequestArray(BaseUrl.CENTER_BONUS_INFO_API, paramsMap)
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
     * 获取订单信息
     */
    fun requestPayInfoData(fid: String, number: String): Observable<PayModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("id", fid)
        paramsMap.put("number", number)
        return netClient.doGetRequest(BaseUrl.CENTER_PAY_INFO_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), PayModel::class.java)
                }
    }

    /**
     * 支付页面
     */
    fun requestPayData(payModel: PayModel) : Observable<JSONObject> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("goodsid", payModel.goodsId)
        paramsMap.put("order", payModel.id)
        paramsMap.put("paymoney", payModel.price.toString())
        paramsMap.put("attach", "大众财富")
        return netClient.doGetRequest(BaseUrl.CENTER_PAY_API, paramsMap)
    }

    /**
     * 获取银行卡列表
     */
    fun requestBankData() : Observable<JSONArray>{
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequestArray(BaseUrl.CENTER_BANK_API, paramsMap)
    }

    /**
     * 获取选择银行卡列表
     */
    fun requestSelectBankData(): Observable<JSONArray> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequestArray(BaseUrl.CENTER_SELECT_BANK_API, paramsMap)
    }

    /**
     * 添加银行卡信息
     */
    fun sendAddBank(bankModel: BankModel): Observable<ResultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("bank", bankModel.bank)
        paramsMap.put("bankuser", bankModel.bankuser)
        paramsMap.put("prvo", bankModel.prvo)
        paramsMap.put("city", bankModel.city)
        paramsMap.put("bankaddress", bankModel.bankaddress)
        paramsMap.put("tel", bankModel.tel)
        paramsMap.put("bankcard", bankModel.bankcard)
        return netClient.doGetRequest(BaseUrl.CENTER_ADD_BANK_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ResultModel::class.java)
                }
    }

    /**
     * 提现接口
     */
    fun sendPickmoney(bankId: String, treadPass: String, money: String, wallet: String): Observable<ResultModel> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        paramsMap.put("bankid", bankId)
        paramsMap.put("p", treadPass)
        paramsMap.put("money", money)
        paramsMap.put("wallet", wallet)
        return netClient.doGetRequest(BaseUrl.CENTER_PICK_API, paramsMap)
                .map { jsonObject ->
                    Gson().fromJson(jsonObject.toString(), ResultModel::class.java)
                }
    }

    // ###########################################################
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


    // ######################################################

    /**
     * 我的投资
     */
    fun requestFinanceMineData(): Observable<JSONArray> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequestArray(BaseUrl.FINANCE_MINE_API, paramsMap)
    }


    // #######################################################

    /**
     * 获取邀请链接
     */
    fun requestShareData(): Observable<JSONObject> {
        val paramsMap = HashMap<String, String>()
        paramsMap.put("uid", "1")
        return netClient.doGetRequest(BaseUrl.SHARE_URL_API, paramsMap)
    }
}