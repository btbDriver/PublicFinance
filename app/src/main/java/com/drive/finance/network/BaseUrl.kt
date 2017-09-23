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
    }
}