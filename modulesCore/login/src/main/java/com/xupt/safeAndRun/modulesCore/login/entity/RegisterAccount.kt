package com.xupt.safeAndRun.modulesCore.login.entity

data class RegisterAccount(
    val tel: String,
    val verifyCode: String,
    val passwd: String,
    val name: String
)
