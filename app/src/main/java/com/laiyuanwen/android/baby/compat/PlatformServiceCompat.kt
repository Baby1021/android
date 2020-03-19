import android.app.Application
import android.app.Notification
import android.content.Context
import android.os.Handler
import android.os.Looper.getMainLooper
import android.util.Log
import com.laiyuanwen.android.baby.BabyApplication.Companion.getApplicationContext
import com.laiyuanwen.android.baby.platform.PlatformService
import com.laiyuanwen.android.baby.service.UmengNotificationService
import com.laiyuanwen.android.baby.util.savePushToken
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UTrack
import com.umeng.message.UmengMessageHandler
import com.umeng.message.entity.UMessage
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar


/**
 * 友盟推送
 */
fun initPush(application: Application) {
    UMConfigure.init(application, PlatformService.UMENG_APP_KEY, PlatformService.APP_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, PlatformService.UMENG_MESSAGE_SECRET)

    val mPushAgent = PushAgent.getInstance(application)

    mPushAgent.register(object : IUmengRegisterCallback {
        override fun onSuccess(deviceToken: String) {
            Log.i("laiyuanwen_debug", "token注册成功：deviceToken：-------->  $deviceToken")
            savePushToken(deviceToken)
        }

        override fun onFailure(p0: String?, p1: String?) {
            Log.d("laiyuanwen_debug","token注册失败,${p0},${p1}")
        }
    })
    val messageHandler: UmengMessageHandler = object : UmengMessageHandler() {
        override fun dealWithCustomMessage(context: Context?, msg: UMessage) {
            Log.d("laiyuanwen_debug","自定义消息回调：${msg.custom}")
            Handler(getMainLooper()).post(Runnable {
                // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
                val isClickOrDismissed = true
                if (isClickOrDismissed) { //自定义消息的点击统计
                    UTrack.getInstance(getApplicationContext()).trackMsgClick(msg)
                } else { //自定义消息的忽略统计
                    UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg)
                }
            })
        }
    }
    mPushAgent.messageHandler = messageHandler
    mPushAgent.setPushIntentServiceClass(UmengNotificationService::class.java)
    mPushAgent.onAppStart()

    HuaWeiRegister.register(application)
    MiPushRegistar.register(application, PlatformService.MI_PUSH_APP_ID, PlatformService.MI_PUSH_APP_KEY)
}