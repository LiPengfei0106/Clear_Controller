package com.cleartv.controller.localserver;

import android.os.Handler;

import com.cleartv.controller.ControllerManager;
import com.cleartv.controller.common.util.Logger;

import java.util.Map;

public class ControllerServer extends NanoHTTPD {
	public final static String TAG = "ControllerServer";
	private static int mPort = 40106;
	private static ControllerServer mLocalServer = null;

	private Handler mHandler = new Handler();
	private Runnable startRunnable = new Runnable() {

		@Override
		public void run() {
			mPort++;
			if(mPort > 40206){
				mPort = 40106;
				return;
			}
			Logger.i(TAG,"port:" + mPort);
			mLocalServer = new ControllerServer(mPort);
			mLocalServer.startWork();
		}
	};

	private ControllerServer(int port) {
		super(port);

	}

	public int getPort() {
		return mPort;
	}

	public static ControllerServer getInstance() {
		if (mLocalServer == null) {
			mLocalServer = new ControllerServer(mPort);
		}
		return mLocalServer;
	}

	public void startWork() {
		try {
		    if(!isAlive())
			    start();
		} catch (Exception e) {
			Logger.e(TAG, "[" + e.getMessage() + "]");
			mHandler.postDelayed(startRunnable, 1000);
		}
	}

	public void stopWork() {
		mLocalServer.stop();
	}

//	@Override
//	public Response serve(IHTTPSession session) {
//		Method method = session.getMethod();
//		String uri = session.getUri();
//		Map<String, String> headers = session.getHeaders();
//		Map<String, String> params = session.getParms();
//		String queryParameterString = session.getQueryParameterString();
//		Logger.i(TAG,uri);
//		Logger.i(TAG,method.toString());
//		Logger.i(TAG,headers.toString());
//		Logger.i(TAG,params.toString());
//		Logger.i(TAG,queryParameterString);
//		return new Response("Not support");
//	}

	@Override
	public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> params, Map<String, String> files) {
		Logger.i(TAG,uri);
		Logger.i(TAG,method.toString());
		Logger.i(TAG,headers.toString());
		Logger.i(TAG,params.toString());
		Logger.i(TAG,files.toString());

//		Logger.i(TAG,uri);
		switch (method){
			case GET:

				break;
			case POST:
				String content = params.get("NanoHttpd.QUERY_STRING");
				ControllerManager.HandleMsg(content);
				break;
		}
	    return new Response("OK");
	}

}