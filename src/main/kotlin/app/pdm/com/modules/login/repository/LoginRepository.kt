package app.pdm.com.modules.login.repository

import app.pdm.com.modules.login.models.LoginReceive
import app.pdm.com.modules.server.models.BaseResponse

interface LoginRepository {

    suspend fun doUserLogin(login: LoginReceive): BaseResponse<Any>

}