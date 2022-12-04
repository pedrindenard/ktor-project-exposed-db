package app.pdm.com.module.login.repository

import app.pdm.com.module.login.models.LoginReceive
import app.pdm.com.module.server.models.BaseResponse

interface LoginRepository {

    suspend fun doUserLogin(login: LoginReceive): BaseResponse<Any>

}