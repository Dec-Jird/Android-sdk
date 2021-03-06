package com.tnyoo.appsflyer;

import java.util.HashMap;
import java.util.Map;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tnyoo.facebook.FacebookU3D;
//import com.tnyoo.webview.WebViewActivity;
import com.unity3d.player.UnityPlayerActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppsFlyerActivity extends UnityPlayerActivity {// UnityPlayerActivity
	/** 下面开始为AppsFlyer的内容 **/
	public static final String LOG_TAG = "AppsFlyer";
	private boolean debugLog;
	// public static final String APPSFLYER_KEY = "bwkqJusb5BwTXTqgvNjNAC";
	// private String gameObjectName = "";
	public static Context context;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		googlePayActivity = this;// GooglePay要用的Activity
		context = this;
		log("AppsFlyerActivity", "onCreate");
		
		/** --------------------- Facebook邀请，获取邀请者信息 ---------------------- **/
		FacebookU3D.getInvitedPlayerMsgForGame(this);
		/** --------------------- Facebook邀请，获取邀请者信息 ---------------------- **/
	}

	// String gameObj
	public void initAppsFlyerSDK(String appsFlyerKey) {// String gameObj,
		debugLog = true;
		// this.gameObjectName = gameObj;
		// This API enables AppsFlyer to detect installations, sessions, and updates. 
		AppsFlyerLib.setAppsFlyerKey(appsFlyerKey);
		AppsFlyerLib.sendTracking(this);// sendTracking必须在第一个启动的Activity中调用

		AppsFlyerLib.setCurrencyCode("CNY");// 设置默认货币种类
		log("initAppsFlyerSDK gameObj", " key = " + appsFlyerKey);
	}

	/*-------------------- Tracker事件开始 -------------------*/
	// /**
	// * Completed Registration
	// *
	// * @param registrationMethod
	// * 注册方式(Facebook, Email, QQ, etc.)
	// */
	// public void completedRegistration(String registrationMethod) {
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	// eventValues.put(AFInAppEventParameterName.REGSITRATION_METHOD,
	// registrationMethod);
	//
	// AppsFlyerLib.trackEvent(context,
	// AFInAppEventType.COMPLETE_REGISTRATION, eventValues);
	// }
	//
	// /**
	// * Spent Credit（游戏内虚拟币消费）
	// *
	// * @param contentType
	// * 消费商品（如：道具，英雄）
	// * @param contentId
	// * 消费商品ID
	// * @param price
	// * 消费金额
	// */
	// public void spentCredit(String contentType, String contentId, float
	// price) {
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	// eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, contentType);
	// eventValues.put(AFInAppEventParameterName.CONTENT_ID, contentId);
	// eventValues.put(AFInAppEventParameterName.PRICE, price);
	//
	// AppsFlyerLib.trackEvent(context, AFInAppEventType.SPENT_CREDIT,
	// eventValues);
	// }

	/**
	 * Level Achieved
	 * 
	 * @param level
	 *            级别
	 * @param score
	 *            分数
	 */
	public void levelAchieved(int level, int score) {
		Map<String, Object> eventValues = new HashMap<String, Object>();
		eventValues.put(AFInAppEventParameterName.LEVEL, level);
		eventValues.put(AFInAppEventParameterName.SCORE, score);

		AppsFlyerLib.trackEvent(context, AFInAppEventType.LEVEL_ACHIEVED,
				eventValues);
	}

	//
	// /**
	// * Achievement Unlocked（解锁成就）
	// *
	// * @param description
	// * 描述
	// */
	// public void achievementUnlocked(String description) {
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	// eventValues.put(AFInAppEventParameterName.DESCRIPTION, description);
	//
	// AppsFlyerLib.trackEvent(context, AFInAppEventType.ACHIEVEMENT_UNLOCKED,
	// eventValues);
	// }
	//
	// /**
	// * Completed Tutorial（完成教程）
	// *
	// * @param success
	// * 是否完成(true or false)
	// * @param contentId
	// * 教程Id
	// */
	// public void completedTutorial(boolean success, String contentId) {
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	// eventValues.put(AFInAppEventParameterName.SUCCESS, success);
	// eventValues.put(AFInAppEventParameterName.CONTENT_ID, contentId);
	//
	// AppsFlyerLib.trackEvent(context, AFInAppEventType.TUTORIAL_COMPLETION,
	// eventValues);
	// }
	//
	// /**
	// * addPaymentInfo 添加支付信息（用于跟踪付款信息配置状态。）
	// * @param success
	// */
	// public void addPaymentInfo(boolean success){
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	// eventValues.put(AFInAppEventParameterName.SUCCESS, success);
	// AppsFlyerLib.trackEvent(context, AFInAppEventType.ADD_PAYMENT_INFO,
	// eventValues);
	// }
	//
	// /**
	// * Initiate Checkout（开始结帐）
	// *
	// * @param contentType
	// * 购买商品
	// * @param contentId
	// * 商品ID
	// * @param quantity
	// * 购买数量
	// * @param paymentInfoAvailable
	// * 支付信息是否可用(true or false)
	// * @param price
	// * 商品价格
	// */
	// public void initiateCheckout(String contentType, String contentId,
	// int quantity, boolean paymentInfoAvailable, float price) {
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	//
	// eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, contentType);
	// eventValues.put(AFInAppEventParameterName.CONTENT_ID, contentId);
	// eventValues.put(AFInAppEventParameterName.QUANTITY, quantity);
	// eventValues.put(AFInAppEventParameterName.PAYMENT_INFO_AVAILIBLE,
	// paymentInfoAvailable);
	// eventValues.put(AFInAppEventParameterName.PRICE, price);
	//
	// AppsFlyerLib.trackEvent(context, AFInAppEventType.INITIATED_CHECKOUT,
	// eventValues);
	// }
	//
	// /**
	// * Purchase（购买，支付）
	// *
	// * @param contentType
	// * 购买商品
	// * @param contentId
	// * 商品ID
	// * @param quantity
	// * 购买数量
	// * @param currency
	// * 货币种类
	// * @param revenue
	// * 付款金额
	// */
	// public void purchase(String contentType, String contentId, int quantity,
	// String currency, float revenue) {
	// Map<String, Object> eventValues = new HashMap<String, Object>();
	//
	// eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, contentType);
	// eventValues.put(AFInAppEventParameterName.CONTENT_ID, contentId);
	// eventValues.put(AFInAppEventParameterName.QUANTITY, quantity);
	// eventValues.put(AFInAppEventParameterName.CURRENCY, currency);
	// eventValues.put(AFInAppEventParameterName.REVENUE, revenue);
	//
	// AppsFlyerLib
	// .trackEvent(context, AFInAppEventType.PURCHASE, eventValues);
	// }

	/**
	 * 登陆
	 * 
	 * @param userId
	 *            用户Id
	 */
	public void login(String userId) {
		Map<String, Object> eventValues = new HashMap<String, Object>();
		eventValues.put(AFInAppEventParameterName.CUSTOMER_USER_ID, userId);
		AppsFlyerLib.trackEvent(context, AFInAppEventType.LOGIN, eventValues);
		AppsFlyerLib.setCustomerUserId(userId);// 设置UserID
	}

	/*-------------------- Tracker事件结束 -------------------*/

	// 为了能持续追踪用户会话时间，必须复写下面方法
	@Override
	public void onResume() {
		super.onResume();
		AppsFlyerLib.onActivityResume(this);
		// facebook 分析
		AppEventsLogger.activateApp(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		AppsFlyerLib.onActivityPause(this);
		// facebook 分析
		AppEventsLogger.deactivateApp(this);
	}

	private void log(String method, String str) {
		if (debugLog)
			Log.i(LOG_TAG, method + ": " + str);
	}

	/** -------------- Google Pay要使用的回调接口开始（借用MainActivity实现） ----------------　 **/
	public interface cbEvent {
		public boolean cbEvent(int requestCode, int resultCode, Intent data);
	}

	public static final String GOOGLE_TAG = "IAB";
	protected cbEvent googlepayCBEvent;
	static protected AppsFlyerActivity googlePayActivity;/* GooglePay的回调Activity */

	@Override
	public void onDestroy() {
		super.onDestroy();
		googlePayActivity = null;
		// Log.d(GOOGLE_TAG, "[AppsFlyerActivity] googlePay onDestroy called!");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		boolean ret = false;

		/** --------------------- Facebook ---------------------- **/
		if (FacebookU3D.callbackManager != null) {

			if (!FacebookSdk.isInitialized())
				FacebookU3D.initFBSdk();

			FacebookU3D.callbackManager.onActivityResult(requestCode,
					resultCode, data);
			Log.d("Facebook_SDK",
					"onActivityResult. data: " + data.getDataString());
		}
		/** --------------------- Facebook ---------------------- **/

		/** --------------------- Google Pay ---------------------- **/
		if (debugLog)
			Log.d(GOOGLE_TAG,
					"[AppsFlyerActivity] googlePay onActivityResult called!");

		if (googlepayCBEvent != null) {
			try {
				ret = googlepayCBEvent.cbEvent(requestCode, resultCode, data);
			} catch (Exception e) {
				ret = false;
			}
		}

		if (ret == false) {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	static public void registerOnActivityResultCBFunc(final cbEvent pcbfunc) {
		// Log.d(GOOGLE_TAG,
		// "[AppsFlyerActivity] googlePay registerOnActivityResultCBFunc called.");
		if (googlePayActivity != null)
			googlePayActivity.googlepayCBEvent = pcbfunc;
	}

	/** -------------- Google Pay要使用的回调接口结束（借用MainActivity实现） ----------------　 **/

	/**
	 * --------------
	 * AsiaSoft支付Activity启动（越南VN单独版本的支付）--------------------------------
	 **/
	// Unity中会调用这个方法，从而开打WebView
	// @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	// public void openWebViewPOST(String url, String postData) {
	// Intent intent = new Intent(this, WebViewActivity.class);
	// intent.putExtra("type", "POST");
	// intent.putExtra("url", url);
	// intent.putExtra("postData", postData);
	// if (debugLog)
	// Log.d("PAY", "[OpenWebViewActivity] openWebViewPOST called!");
	// this.startActivity(intent);
	// }

	/**
	 * --------------
	 * PlayMail支付Activity启动（緬甸MM单独版本的支付）--------------------------------
	 **/
	// // Unity中会调用这个方法，从而开打WebView
	// @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	// public void openWebViewGET(String url)
	// // public void openWebViewGET(String url, String getData)
	// {
	// Intent intent = new Intent(this, WebViewActivity.class);
	// intent.putExtra("type", "GET");
	// intent.putExtra("url", url);
	// // intent.putExtra("getData", getData);
	// if (debugLog)
	// Log.d("PAY", "[OpenWebViewActivity] openWebViewGET called!");
	// this.startActivity(intent);
	// }

	// ----------------测试用UI界面的所有Button onClick事件-----------------
	/*
	 * public void login(View v) { initAppsFlyerSDK("gameObj",
	 * "bwkqJusb5BwTXTqgvNjNAC"); login("0032290032"); }
	 * 
	 * public void completedRegistration(View v) { completedRegistration("手机号");
	 * }
	 * 
	 * public void spentCredit(View v) { spentCredit("英雄 黑猫警长", "078", 6300); }
	 * 
	 * public void levelAchieved(View v) { levelAchieved(22, 5396); }
	 * 
	 * public void achievementUnlocked(View v) { achievementUnlocked("铂金段位"); }
	 * 
	 * public void completedTutorial(View v) { completedTutorial(true,
	 * "003newguyturorial"); }
	 * 
	 * public void initiateCheckout(View v) { initiateCheckout("水娃冰川之心皮肤",
	 * "swicep110", 1, true, 79.9f); }
	 * 
	 * public void purchase(View v) { purchase("火娃烈焰之心皮肤", "hwfirep112", 1,
	 * "CNY", 99.5f); } //
	 */
}
