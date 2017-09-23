package com.drive.finance.network.model

/**
 * Created by aaron on 2017/9/23.
 */
class UserModel {
    val myinfo = MyInfo()
    val banklist = ArrayList<BackInfo>()
}

class MyInfo {
    val username: String = ""
    val realname: String = ""
    val tel: String = ""
    val email: String = ""
    val bankaddress: String = ""
    val bankcard: String = ""
}

class BackInfo {
    val id: String = ""
    val bank: String = ""
    val status: String = ""
    val re: String = ""
    val sort: String = ""
    val rate: String = ""
}