package com.drive.finance

import org.json.JSONObject

/**
 * Created by aaron on 2017/9/11.
 */
data class CreateBonusListFragmentEvent(val sender: String)

data class CreatePickFragmentEvent(val sender: String)

data class CreateUserInfoFragmentEvent(val sender: String)

data class CreateInviteFragmentEvent(val sender: String)

data class CreateSuggestFragmentEvent(val sender: String)

data class CreateFinanceBankFragmentEvent(val sender: String)

data class CreateFinancePickFragmentEvent(val jsonObject: JSONObject)