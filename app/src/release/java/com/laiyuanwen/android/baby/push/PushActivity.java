package com.laiyuanwen.android.baby.push;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.laiyuanwen.android.baby.LoginActivity;
import com.laiyuanwen.android.baby.ui.MainActivity;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

import static com.laiyuanwen.android.baby.util.UserCenterKt.isLogin;

/**
 * Created by laiyuanwen on 2019-02-07.
 */
public class PushActivity extends UmengNotifyClickActivity {

    private static String TAG = PushActivity.class.getName();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isLogin(this)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.i(TAG, body);
    }
}
