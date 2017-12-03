package com.rkarp.reddit;

import android.app.Application;

public class RedditApplication extends Application {
	private static RedditApplication application;
	
	public RedditApplication(){
		application = this;
	}
	
	public static RedditApplication getApplication(){
		return application;
	}
}
