package com.drive.finance.network

/**
 * Created by aaron on 2017/9/22.
 */
class BaseUrl {
    companion object {
        val BASE_URL = "http://www.dazhongcf.com/Home/"
        // HOME页面请求API
        val Home_API = BASE_URL + "Index/indexApp?action=API"

        // 用户信息请求API
        val USER_API = BASE_URL + "User/indexApp?action=API"
        // 发送短信验证码
        val USER_SEND_CODE_API = BASE_URL + "User/regcode?action=API"
        // 更新个人信息
        val UPDATE_USER_API = BASE_URL + "User/insaveApp?action=API"
        // 更新登录密码
        val UPDATE_LOGIN_PASS_API = BASE_URL + "User/pasaveApp?action=API"
        // 更新交易密码
        val UPDATE_TREAD_PASS_API = BASE_URL + "User/patradesaveApp?action=API"

        /**
         * 团队管理
         */
        // 推荐列表
        val TEAM_RECOMMEND_API = BASE_URL + "User/myuserApp?action=API"
        // 业绩
        val TEAM_YJ_API = BASE_URL + "User/getYj?action=API"
        // 图谱
        val TREAM_CHART_API = BASE_URL + "member/recoApp?action=API"

        /**
         * 财务中心
         */
        // 奖金列表
        val CENTER_BONUS_API = BASE_URL + "money/indexApp?action=API"
        // 投资理财
        val CENTER_FINANCE_API = BASE_URL + "financial/indexApp?action=API"
        // 银行卡列表
        val CENTER_BANK_API = BASE_URL + "User/bankApp?action=API"
        // 选择银行卡
        val CENTER_SELECT_BANK_API = BASE_URL + "User/getBankList?action=API"
        // 添加银行卡
        val CENTER_ADD_BANK_API = BASE_URL + "User/addBankApp?action=API"
        // 提现接口
        val CENTER_PICK_API = BASE_URL + "Money/CashAppNew?action=API"

        /**
         * 公司资讯
         */
        val CONSULT_INFO_API = BASE_URL + "news/viewApp?action=API"

        /**
         * 邀请链接url
         */
        val SHARE_URL_API = BASE_URL + "User/promoteApp?action=API"

        /**
         * 大众理财
         */
        // 我的投资
        val FINANCE_MINE_API = BASE_URL + "Financial/myfinApp?action=API"
    }
}