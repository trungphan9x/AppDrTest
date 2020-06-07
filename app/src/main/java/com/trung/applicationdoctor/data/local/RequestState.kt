package com.trung.applicationdoctor.data.local

import com.trung.applicationdoctor.data.enum.RequestStateType

data class RequestState (
    var stateType: RequestStateType? = null,
    var stateMessage: String? = null,
    var data: Any? = null
)