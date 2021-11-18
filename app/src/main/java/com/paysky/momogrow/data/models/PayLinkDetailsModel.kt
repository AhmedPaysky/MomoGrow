package com.paysky.momogrow.data.models

import java.io.Serializable

data class PayLinkDetailsModel(
    var name: String,
    var orderId: String,
    var terminalId: String,
    var merchantId: String,
    var mobileNumer: String,
    var dateExpire: String,
    var dateCreated: String,
    var amount: String
) : Serializable