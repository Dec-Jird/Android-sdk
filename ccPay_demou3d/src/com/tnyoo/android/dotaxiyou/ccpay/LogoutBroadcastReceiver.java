package com.tnyoo.android.dotaxiyou.ccpay;

import com.unity3d.player.UnityPlayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 登出广播
 */
public class LogoutBroadcastReceiver extends BroadcastReceiver {

	public static final String LOGINOUT_ACTION = "CCPAY_LOGINOUT_ACTION";
	private final static String LogoutNotifyMethod = "LogoutNotify";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		UnityPlayer.UnitySendMessage(U3DApi.m_CCPayGameObj,
				LogoutNotifyMethod, "logout success");//注销成功
		
//		Toast.makeText(context, "注销登录", Toast.LENGTH_SHORT).show();
		Log.i("PAY", "注销登录成功...");
	}
}
