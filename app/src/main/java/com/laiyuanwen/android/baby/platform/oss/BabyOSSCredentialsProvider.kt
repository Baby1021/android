package com.laiyuanwen.android.baby.platform.oss

import android.content.Context
import androidx.core.content.edit
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.common.OSSConstants
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken
import com.alibaba.sdk.android.oss.common.utils.DateUtil
import com.alibaba.sdk.android.oss.common.utils.IOUtils
import com.laiyuanwen.android.baby.util.Provider
import com.laiyuanwen.android.baby.util.getSp
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by laiyuanwen on 2020/7/21.
 */
class BabyOSSCredentialsProvider(
        private val context: Context,
        private val mAuthServerUrl: String
) : OSSFederationCredentialProvider() {

    override fun getFederationToken(): OSSFederationToken {
        val token = Provider.getGson().fromJson(getSp(this.context).getString("oss-token", "{}"), OSSFederationToken::class.java)

        if (token != null && DateUtil.getFixedSkewedTimeMillis() / 1000 < token.expiration - 5 * 60) {
            return token
        }

        val authToken: OSSFederationToken
        return try {
            val stsUrl = URL(mAuthServerUrl)
            val conn = stsUrl.openConnection() as HttpURLConnection
            conn.connectTimeout = 10000
            val input = conn.inputStream
            val authData = IOUtils.readStreamAsString(input, OSSConstants.DEFAULT_CHARSET_NAME)
            val jsonObj = JSONObject(authData)
            val statusCode = jsonObj.getInt("StatusCode")
            authToken = if (statusCode == 200) {
                val ak = jsonObj.getString("AccessKeyId")
                val sk = jsonObj.getString("AccessKeySecret")
                val token = jsonObj.getString("SecurityToken")
                val expiration = jsonObj.getString("Expiration")
                OSSFederationToken(ak, sk, token, expiration)
            } else {
                val errorCode = jsonObj.getString("ErrorCode")
                val errorMessage = jsonObj.getString("ErrorMessage")
                throw ClientException("ErrorCode: $errorCode| ErrorMessage: $errorMessage")
            }

            getSp(this.context).edit {
                putString("oss-token", Provider.getGson().toJson(authToken))
            }

            authToken
        } catch (e: Exception) {
            throw ClientException(e)
        }
    }
}