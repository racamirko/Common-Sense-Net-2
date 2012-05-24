package com.commonsensenet.realfarm.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class PathBuilder {
	protected static Uri.Builder sBuilder = new Uri.Builder();
	protected static Context sCtx;
	
	public static void init(Context ctx){
		sCtx = ctx;
	}
	
	public static Uri getSound(String filename){
		return sBuilder.encodedPath(sCtx.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()+"/"+filename).build();
	}
	
	public static Uri getPicture(String filename){
		return sBuilder.encodedPath(sCtx.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+filename).build();
	}
	
}
