import android.app.Application


/**
 * 友盟推送
 */
fun initPush(application: Application) {
//    UMConfigure.init(application, PlatformService.UMENG_APP_KEY, PlatformService.APP_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, PlatformService.UMENG_MESSAGE_SECRET)
//
//    val mPushAgent = PushAgent.getInstance(application)
//
//    mPushAgent.register(object : IUmengRegisterCallback {
//        override fun onSuccess(deviceToken: String) {
//            Log.i("Umeng", "注册成功：deviceToken：-------->  $deviceToken")
//            savePushToken(deviceToken)
//        }
//
//        override fun onFailure(p0: String?, p1: String?) {
//        }
//    })
//    mPushAgent.onAppStart()
//
//    HuaWeiRegister.register(application)
//    MiPushRegistar.register(application, PlatformService.MI_PUSH_APP_ID, PlatformService.MI_PUSH_APP_KEY)
}