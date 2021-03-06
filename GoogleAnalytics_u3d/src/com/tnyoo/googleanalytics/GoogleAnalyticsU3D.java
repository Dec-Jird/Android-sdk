package com.tnyoo.googleanalytics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tnyoo.googleanalytics.GAAnalyticsApplication.TrackerName;
import android.util.Log;

public class GoogleAnalyticsU3D {// "UA-70050428-1"

	private static final String TAG = "Google";
	// public String gameObject;
	private static GoogleAnalyticsU3D googleAnalyticsU3D;
	private GAAnalyticsApplication GAAnalyticsApp;
	private boolean debugLog;

	public static GoogleAnalyticsU3D getInstance() {
		if (googleAnalyticsU3D == null) {
			googleAnalyticsU3D = new GoogleAnalyticsU3D();
		}
		return googleAnalyticsU3D;
	}

	public void initGoogleAnalytics(String propertyId) {// String gameObj,
		debugLog = true;
		log("call initGoogleAnalytics", " propertyId = " + propertyId);
		// gameObject = gameObj;
		GAAnalyticsApp = new GAAnalyticsApplication();
		GAAnalyticsApp.initGoogleApp(propertyId);
	}

	/**
	 * 用户计时：原生的时间衡量方式。比如，衡量资源加载时间（用户计时数据主要可在“用户计时”报告中找到。）。
	 * 
	 * @param category
	 *            计时事件的类别。
	 * @param value
	 *            以毫秒表示的计时值（时间间隔1000ms = 1s）。
	 * @param name
	 *            计时事件的名称。
	 * @param label
	 *            计时事件的标签。
	 */
	public void timing(String category, long value, String name, String label) {
		Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
		// Build and send timing.
		appTracker.send(new HitBuilders.TimingBuilder().setCategory(category)
				.setValue(value).setVariable(name).setLabel(label).build());
		log("timing", name);
	}

	/**
	 * 发送社交互动
	 * 
	 * @param socialNetwork
	 *            用户与之互动的社交网络（例如 Facebook、Google+、Twitter 等）。
	 * @param socialAction
	 *            进行的社交操作（例如赞、分享、+1 等）。
	 * @param socialTarget
	 *            发生社交操作的内容（例如某篇文章或某段视频）。
	 */
	public void sendSocialInteraction(String socialNetwork,
			String socialAction, String socialTarget) {
		Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
		// Build and send social interaction.
		appTracker.send(new HitBuilders.SocialBuilder()
				.setNetwork(socialNetwork).setAction(socialAction)
				.setTarget(socialTarget).build());
		log("SocialInteraction screenName", socialAction);
	}

	// /**
	// * 构建并发送普通事件
	// *
	// * @param screenName
	// * @param category
	// * 事件类别
	// * @param action
	// * 事件操作
	// * @param label
	// * 事件标签
	// * @param value
	// * 事件值
	// */
	// public void setupEvent(String screenName, String category, String action,
	// String label, long value) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	//
	// appTracker.setScreenName(screenName);
	// appTracker.send(new HitBuilders.EventBuilder()
	// .setCategory(category)
	// .setAction(action)
	// .setLabel(label)
	// .setValue(value)
	// .build());
	// appTracker.setScreenName(null);
	// log("setupEvent EventCategory", category);
	// }
	//
	// /**
	// * 构建并发送普通事件
	// *
	// * @param screenName
	// * @param category
	// * 事件类别
	// * @param action
	// * 事件操作
	// */
	// public void simpleEvent(String screenName, String category, String
	// action) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	//
	// appTracker.setScreenName(screenName);
	// appTracker.send(new HitBuilders.EventBuilder()
	// .setCategory(category)
	// .setAction(action).build());
	// appTracker.setScreenName(null);
	// log("setupEvent EventCategory", category);
	// }
	//
	// /**
	// * 用户id An example method called when a user signs in to an authentication
	// * system.
	// *
	// * @param userId
	// */
	// public void onUserSignIn(String userId) {
	// // Be careful when creating new trackers -- it is possible to create
	// // multiple trackers for the same tracking Id.
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// // You only need to set User ID on a tracker once. By setting it on the
	// // tracker, the ID will be sent with all subsequent hits.
	// appTracker.set("&uid", userId);
	// // This hit will be sent with the User ID value and be visible in
	// // User-ID-enabled views (profiles).
	// appTracker.send(new HitBuilders.EventBuilder().setCategory("Login")
	// .setAction("User Sign In").build());
	// log("onUserSignIn userId", userId);
	// }
	//
	// // 电子商务：向 GoogleAnalytics（分析）发送应用内购买和销售数据，
	// /**
	// * 发送交易数据（交易数据和商品数据由共同的交易ID相关联）
	// *
	// * @param transactionId
	// * 代表某次交易的唯一 ID。此 ID 不应与其他交易 ID 重复。
	// * @param affiliation
	// * 此次交易关联的实体（例如某家商店）。
	// * @param revenue
	// * 交易的总收入，含税费和运费。
	// * @param tax
	// * 交易的总税费。
	// * @param shipping
	// * 交易的总运费。
	// * @param currencyCode
	// * 交易的本地货币。为null时取默认值为此次交易对应的数据视图（配置文件）的货币。
	// */
	// public void sendTransactionData(String transactionId, String affiliation,
	// double revenue, double tax, double shipping, String currencyCode) {
	// TransactionBuilder transaction = new HitBuilders.TransactionBuilder()
	// .setTransactionId(transactionId)
	// .setAffiliation(affiliation)
	// .setRevenue(revenue)
	// .setTax(tax)
	// .setShipping(shipping);
	// if (currencyCode != null)
	// transaction.setCurrencyCode(currencyCode);
	//
	// sendDataToTwoTrackers(transaction.build());
	// }
	//
	// /**
	// * 发送商品数据（交易数据和商品数据由共同的交易ID相关联）
	// *
	// * @param transactionId
	// * 该商品关联的交易ID。
	// * @param name
	// * 产品名称。
	// * @param SKU
	// * 产品库存量。
	// * @param category
	// * 产品所属的类别。
	// * @param price
	// * 产品的价格。
	// * @param quantity
	// * 产品的数量。
	// * @param currencyCode
	// * 交易的本地货币。为null时取默认值为此次交易对应的数据视图（配置文件）的货币。
	// */
	// public void sendProductData(String transactionId, String name, String
	// SKU,
	// String category, double price, long quantity, String currencyCode) {
	// ItemBuilder product = new HitBuilders.ItemBuilder()
	// .setTransactionId(transactionId)
	// .setName(name)
	// .setSku(SKU)
	// .setCategory(category)
	// .setPrice(price)
	// .setQuantity(quantity);
	// if (currencyCode != null)
	// product.setCurrencyCode(currencyCode);
	//
	// sendDataToTwoTrackers(product.build());
	// }
	//
	// // Sends the ecommerce data.
	// private void sendDataToTwoTrackers(Map<String, String> params) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// Tracker ecommerceTracker = GAAnalyticsApp
	// .getTracker(TrackerName.ECOMMERCE_TRACKER);
	//
	// appTracker.send(params);
	// ecommerceTracker.send(params);
	// log("sendDataToTwoTrackers", params.toString());
	// }
	//
	// // 增强型电子商务
	//
	// // 屏幕：衡量屏幕浏览量以及用户如何在屏幕之间跳转
	// public void sendScreenView(String screenName) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// appTracker.setScreenName(screenName);
	// // Send a screen view.
	// appTracker.send(new HitBuilders.ScreenViewBuilder().build());
	// log("sendScreenView screenName", screenName);
	// }
	//
	// // 会话
	// public void setNewSession(String screenName) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// appTracker.setScreenName(screenName);
	// // Start a new session with the hit.
	// appTracker.send(new HitBuilders.ScreenViewBuilder()
	// .setNewSession()
	// .build());
	// log("setNewSession screenName", screenName);
	// }
	//
	// /**
	// * 发送异常
	// *
	// * @param exceptionMethod
	// * 发生异常的方法
	// * @param exceptionLocation
	// * 发生异常位置
	// * @param exceptionFatal
	// * 异常是否严重，true 代表严重。
	// */
	// public void sendException(String exceptionMethod, String
	// exceptionLocation,
	// boolean exceptionFatal) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// // Build and send exception.
	// appTracker.send(new HitBuilders.ExceptionBuilder()
	// .setDescription(exceptionMethod + ": " + exceptionLocation)// 异常描述
	// .setFatal(exceptionFatal).build());
	// log("sendException", exceptionMethod + " - " + exceptionLocation);
	// }
	//
	// //
	// 自定义维度和指标（报表中显示行为维度列为指标），如：自定义维度为Vip，则指标有1(vip1),2(vip2)....，自定义维度为级别(level)，则指标有1级,2级....。
	// /**
	// * 设置自定义维度
	// *
	// * @param screenName
	// * @param index
	// * 自定义维度或指标的索引。此索引值以 1 起始（表格行索引，一行一维度）。
	// * @param dimension
	// * 自定义维度或指标的值。对于指标而言，此值会被解析为整数（如果指标被配置为货币类型，则值会解析为固定位数的小数值）。
	// */
	// public void customDimension(String screenName, int index, String
	// dimension) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// appTracker.setScreenName(screenName);
	//
	// // Send the custom dimension value with a screen view.
	// // Note that the value only needs to be sent once.
	// appTracker.send(new HitBuilders.ScreenViewBuilder()
	// .setCustomDimension(index, dimension)// (1, "premiumUser")
	// .build());
	// appTracker.setScreenName(null);
	// log("customDimension dimension", index + " - " + dimension);
	// }
	//
	// /**
	// * 设置自定义指标
	// *
	// * @param screenName
	// * @param index
	// * 自定义维度或指标的索引。此索引值以 1 起始（表格列索引，一列一指标）。
	// * @param metric
	// * 自定义维度或指标的值。对于指标而言，此值会被解析为整数（如果指标被配置为货币类型，则值会解析为固定位数的小数值）。
	// */
	// public void customMetric(String screenName, int index, float metric) {
	// Tracker appTracker = GAAnalyticsApp.getTracker(TrackerName.APP_TRACKER);
	// appTracker.setScreenName(screenName);
	//
	// // Send the custom dimension value with a screen view.
	// // Note that the value only needs to be sent once.
	// appTracker.send(new HitBuilders.ScreenViewBuilder()
	// .setCustomMetric(index, metric)// (1, 5)
	// .build());
	// appTracker.setScreenName(null);
	// log("customMetric metric", index + " - " + metric);
	// }

	private void log(String method, String str) {
		if (debugLog)
			Log.i(TAG, method + ": " + str);
	}
}
