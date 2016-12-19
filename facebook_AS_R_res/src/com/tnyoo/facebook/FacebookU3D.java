package com.tnyoo.facebook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.AppInviteContent.Builder;
import com.facebook.share.model.*;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.widget.ShareDialog.Mode;
import com.unity3d.player.UnityPlayer;

public class FacebookU3D {// extends UnityPlayerActivity {

	public static final String TAG = "Facebook_AND";
	public final static String CALLBACK_INIT = "OnInitSuc"; // SDK初始化成功的回调方法名称和Unity中一致
	public final static String CALLBACK_LOGIN = "OnLoginSuc"; // SDK登录成功的回调方法名称和Unity中一致
	public final static String CALLBACK_LOGOUT = "OnLogoutSuc"; // SDK登出的回调方法名称和Unity中一致
	public final static String CALLBACK_SHARE = "OnShareLinkSuc"; // SDK分享回调方法名称和Unity中一致
	public final static String CALLBACK_INVITE = "OnAppInviteSuc"; // SDK邀请回调方法名称和Unity中一致
	public static final String[] permissionList = { "public_profile",
			"user_friends", "email" };
	public static CallbackManager callbackManager;
	private AccessTokenTracker accessTokenTracker;
	private Boolean sendLoginCallback = false;
	// private AccessToken accessToken;
	// private LoginButton loginButton;
	// private boolean isResumed = false;
	private static ShareDialog shareDialog;
	private static AppInviteDialog appInviteDialog;
	// private Activity activity;
	private static String CALLBACK_GAME_OBJ;

	private static FacebookU3D FacebookU3D;

	public static FacebookU3D getInstance() {
		if (FacebookU3D == null) {
			FacebookU3D = new FacebookU3D();
		}
		return FacebookU3D;
	}

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// activity = UnityPlayer.currentActivity;
	// Initialize the SDK before executing any other operations,
	// initFBSdk();// 一定要在setContentView前调用

	// setContentView(R.layout.activity_main);

	// loginButton = (LoginButton) findViewById(R.id.login_button);
	// loginButton.setReadPermissions(permissionList);

	// loginButton = new LoginButton(activity, null);
	// loginButton.setReadPermissions(permissionList);
	// LayoutParams params;
	//
	// activity.addContentView(loginButton, LayoutParams.WRAP_CONTENT);
	// }

	public static void debugLog(String string) {
		Log.i(TAG, "[FB AND] - " + string);
		// Toast.makeText(this.getBaseContext(), string, Toast.LENGTH_SHORT)
		// .show();
	}

	// 向Unity中发送消息
	public static void sendCallback(String name, String jsonParams) {
		if (jsonParams == null) {
			jsonParams = "";
		}
		UnityPlayer.UnitySendMessage(CALLBACK_GAME_OBJ, name, jsonParams);
	}

	public static void initFBSdk() {
		final Activity mActivity = (Activity) UnityPlayer.currentActivity;

		FacebookSdk.sdkInitialize(mActivity,
				new FacebookSdk.InitializeCallback() {

					@Override
					public void onInitialized() {
						debugLog("FB onInitialized Success");
					}
				});

		AppEventsLogger.activateApp(mActivity);
	}

	// 初始化-登陆
	public void InitFB(String gameObjName) { // 联盟为应用分配的应用ID

		CALLBACK_GAME_OBJ = gameObjName;
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					initFBSdk();// 初始化facebook sdk(必须在其他事件前)
					shareDialog = new ShareDialog(mActivity);
					appInviteDialog = new AppInviteDialog(mActivity);
					callbackManager = CallbackManager.Factory.create();

					// 注册登录、分享、邀请回调（必须在分享邀请Dialog和callbackManager创建之后）
					registerCallbacks();

					accessTokenTracker = new AccessTokenTracker() {
						@Override
						protected void onCurrentAccessTokenChanged(
								AccessToken oldAccessToken,
								AccessToken currentAccessToken) {
							if (currentAccessToken != null) {
								// 获取当前登录用户AccessToken，发送登录Callback给Unity
								String accessToken = currentAccessToken
										.getToken();
								String userId = currentAccessToken.getUserId();
								if (!sendLoginCallback) {
									sendCallback(CALLBACK_LOGIN,
											"{\"AccessToken\":" + accessToken
													+ ",\"UserId\":" + userId
													+ "}");
									sendLoginCallback = true;
								}

								debugLog("onCurrentAccessTokenChanged: User Already Login. AccessToken: "
										+ accessToken + ", UserId: " + userId);
							} else {
								debugLog("onCurrentAccessTokenChanged: currentAccessToken is null, pls Login. ");
								sendCallback(CALLBACK_LOGOUT,
										String.valueOf(!isLoggedIn()));
								sendLoginCallback = false;
							}
						}
					};

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
		}
	}

	/**
	 * Facebook Analytics（统计分析）
	 * 如果需要上报更多事件，详见：https://developers.facebook.com/docs/app-events/android
	 * Level Achieved (升级）.
	 * 
	 * @param levelNum
	 *            级别.
	 */
	public void levelAchieved(String levelNum) {
		AppEventsLogger logger = AppEventsLogger
				.newLogger(UnityPlayer.currentActivity);

		Bundle parameters = new Bundle();
		parameters.putString(AppEventsConstants.EVENT_PARAM_LEVEL, levelNum);
		logger.logEvent(AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL,
				parameters);

		debugLog("FB call levelAchieved. level: " + levelNum);
	}

	/**
	 * 登录sdk
	 */
	public void sdkLogin() {
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					LoginManager.getInstance().logInWithReadPermissions(
							mActivity, Arrays.asList(permissionList));

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
		}
	}

	public void sdkLogout() {
		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					LoginManager.getInstance().logOut();
					sendLoginCallback = false;
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
		}
	}

	/**
	 * 邀请
	 * 
	 * @param appLinkUrl
	 *            应用URL
	 * @param previewImageUrl
	 *            预览图URL
	 */
	public void sdkAppInvite(final String appLinkUrl,
			final String previewImageUrl) {

		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					initFBSdk();
					if (appInviteDialog.canShow()) {
						AppInviteContent.Builder contentBuilder = new AppInviteContent.Builder();
						if (appLinkUrl != null)
							contentBuilder.setApplinkUrl(appLinkUrl);
						else {
							Log.e(TAG,
									"Invite applink Url can't be null or \"\"");
							return;
						}

						if (previewImageUrl != "")
							contentBuilder.setPreviewImageUrl(previewImageUrl);

						appInviteDialog.show(mActivity, contentBuilder.build());
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
		}
	}

	/**
	 * 分享
	 * 
	 * @param contentUrl
	 *            要分享的链接
	 * @param contentTitle
	 *            表示链接中的内容的标题
	 * @param imageUrl
	 *            将在帖子中显示的缩略图的网址
	 * @param contentDescription
	 *            内容的详情，通常为 2-4 个句子
	 */
	public void sdkShareLink(final String contentUrl,
			final String contentTitle, final String imageUrl,
			final String contentDescription) {

		// debugLog("FB Share. contentUrl is null?: " + (contentUrl==null) +
		// ", contentTitle null?"
		// + (contentTitle==null) + ", imageUrl null?" + (imageUrl==null)
		// + ", contentDescription null?" + (contentDescription==null) );
		//
		// debugLog("FB Share. contentUrl is \"\"?: " + (contentUrl=="") +
		// ", contentTitle is \"\""
		// + (contentTitle=="") + ", imageUrl is \"\"" + (imageUrl=="")
		// + ", contentDescription is \"\"" + (contentDescription=="") );

		try {
			final Activity mActivity = (Activity) UnityPlayer.currentActivity;
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {

					initFBSdk();
					if (shareDialog.canShow(ShareLinkContent.class)) {
						ShareLinkContent.Builder linkContent = new ShareLinkContent.Builder();
						if (contentUrl != null) {
							linkContent.setContentUrl(Uri.parse(contentUrl));
						} else {
							Log.e(TAG,
									"Share content Url can't be null or \"\"");
							return;
						}

						if (contentTitle != null)
							linkContent.setContentTitle(contentTitle);
						if (imageUrl != null)
							linkContent.setImageUrl(Uri.parse(imageUrl));

						if (contentDescription != null)
							linkContent
									.setContentDescription(contentDescription);

						shareDialog.show(linkContent.build());
					}

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
		}
	}

	public void sdkShareImage(String url) {
		// Bitmap bitmap = BitmapFactory.decodeResource(
		// UnityPlayer.currentActivity.getBaseContext().getResources(),
		// R.drawable.testImage);

		initFBSdk();
		if (ShareDialog.canShow(ShareLinkContent.class)) {

			Bitmap bitmap = getBitmapFromUrl(url);

			SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap)
					.build();

			SharePhotoContent shareContent = new SharePhotoContent.Builder()
					.addPhoto(photo).build();

			shareDialog.show(shareContent, Mode.AUTOMATIC);
		}
	}

	/**
	 * 根据图片的url路径获得Bitmap对象
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromUrl(String url) {
		URL fileUrl = null;
		Bitmap bitmap = null;

		try {
			fileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) fileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;

	}

	private boolean isLoggedIn() {
		AccessToken accesstoken = AccessToken.getCurrentAccessToken();
		return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
	}

	private void registerCallbacks() {
		debugLog("registerCallbacks");
		if (callbackManager == null || appInviteDialog == null
				|| shareDialog == null) {
			Log.e(TAG,
					"calllbackManager or Dialog is null, you won't get any callbacks");
		}

		// 注册登录回调
		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {

					@Override
					public void onCancel() {
						// App code
						debugLog("Login onCancel");
					}

					@Override
					public void onError(FacebookException exception) {
						// App code
						debugLog("Login onError. exception: "
								+ exception.toString());
					}

					@Override
					public void onSuccess(LoginResult loginResult) {
						// 获取当前登录用户AccessToken，发送登录Callback给Unity
						String accessToken = loginResult.getAccessToken()
								.getToken();
						String userId = loginResult.getAccessToken()
								.getUserId();
						if (!sendLoginCallback) {
							sendCallback(CALLBACK_LOGIN, "{\"AccessToken\":"
									+ accessToken + ",\"UserId\":" + userId
									+ "}");
							sendLoginCallback = true;
						}
						debugLog("Login onSuccess. AccessToken: " + accessToken
								+ ", UserId: " + userId);
					}
				});

		appInviteDialog.registerCallback(callbackManager,
				new FacebookCallback<AppInviteDialog.Result>() {
					@Override
					public void onSuccess(AppInviteDialog.Result result) {
						debugLog("AppInvite onSuccess. result: "
								+ result.getData());
						sendCallback(
								CALLBACK_INVITE,
								"AppInvite onSuccess. result: "
										+ result.getData());
					}

					@Override
					public void onCancel() {
						debugLog("AppInvite onCancel");
					}

					@Override
					public void onError(FacebookException error) {
						debugLog("AppInvite onError. facebook Exception : "
								+ error.toString());
					}
				});

		// this part is optional
		shareDialog.registerCallback(callbackManager,
				new FacebookCallback<Sharer.Result>() {

					@Override
					public void onSuccess(Sharer.Result result) {
						debugLog("Share onSuccess. PostId: "
								+ result.getPostId());
						sendCallback(
								CALLBACK_INVITE,
								"Share onSuccess. PostId: "
										+ result.getPostId());
					}

					@Override
					public void onCancel() {
						debugLog("Share onCancel");
					}

					@Override
					public void onError(FacebookException error) {
						debugLog("Share onError. facebook Exception : "
								+ error.toString());
					}
				});

		// Callback registration
		// loginButton.registerCallback(callbackManager, new
		// FacebookCallback<LoginResult>() {
		//
		// @Override
		// public void onCancel() {
		// // App code
		// debugLog("Login onCancel");
		// }
		//
		// @Override
		// public void onError(FacebookException e) {
		// debugLog("Login onError. facebook Exception : " + e.toString());
		// }
		//
		// @Override
		// public void onSuccess(LoginResult loginResult) {
		// String accessToken = loginResult.getAccessToken().getToken();
		// String userId = loginResult.getAccessToken().getUserId();
		//
		// // 获取当前登录用户AccessToken
		// debugLog("Login onSuccess. AccessToken: "
		// + accessToken + ", UserId: " + userId);
		// }
		// });
	}

	// @Override
	// public void onResume() {
	// super.onResume();
	// // isResumed = true;
	// }
	//
	// @Override
	// public void onPause() {
	// super.onPause();
	// // isResumed = false;
	// }
	//
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (!FacebookSdk.isInitialized()) {
	// initFBSdk();
	// }
	// callbackManager.onActivityResult(requestCode, resultCode, data);
	// debugLog("onActivityResult. data: " + data.getDataString());
	// }

	// @Override
	// public void onDestroy() {
	// super.onDestroy();
	// accessTokenTracker.stopTracking();
	// }

	// public void Login(View view) {
	// sdkLogin();
	// }
	//
	// public void Logout(View view) {
	// sdkLogout();
	// }
	//
	// public void AppInvite(View view) {
	// String appLinkUrl, previewImageUrl;
	// appLinkUrl =
	// "https://play.google.com/store/apps/details?id=com.playpark.dot";
	// previewImageUrl =
	// "https://lh3.googleusercontent.com/n_TleFiN1Jbg-VZuL249EtZfstkh9kSDRnLP15XkYDd4e3ZbWwQzHNRW893dn7GsTpY=h900";
	// sdkAppInvite(appLinkUrl, previewImageUrl);
	// }
	//
	// public void ShareLink(View view) {
	// String contentTitle, contentDescription, contentUrl;
	//
	// contentTitle = "Hello Facebook";
	// contentDescription =
	// "The 'Hello Facebook' sample  showcases simple Facebook integration";
	// contentUrl =
	// "https://play.google.com/store/apps/details?id=com.playpark.dot";
	// sdkShareLink(contentTitle, contentDescription, contentUrl);
	// }

}
