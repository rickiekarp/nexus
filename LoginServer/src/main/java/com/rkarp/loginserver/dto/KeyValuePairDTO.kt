package com.rkarp.loginserver.dto

class KeyValuePairDTO {
    var key: String? = null
    var value: String? = null

    constructor() {
        //do not delete. used for deserialization
    }

    constructor(username: String, password: String) {
        this.key = username
        this.value = password
    }
}
