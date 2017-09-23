package com.drive.finance.network.model

/**
 * Created by aaron on 2017/9/23.
 */
class UserModel {
    var myinfo = MyInfo()
    var banklist = ArrayList<BackInfo>()
}

class MyInfo {
    var username: String = ""
    var realname: String = ""
    var tel: String = ""
    var email: String = ""
    var bankaddress: String = ""
    var bankcard: String = ""
    var bank: String = ""
    var p: String = ""
    var smscode: String = ""
}

class BackInfo {
    val id: String = ""
    val bank: String = ""
    val status: String = ""
    val re: String = ""
    val sort: String = ""
    val rate: String = ""
}