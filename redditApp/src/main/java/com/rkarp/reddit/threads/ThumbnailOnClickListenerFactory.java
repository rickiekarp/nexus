package com.rkarp.reddit.threads;

import com.rkarp.reddit.things.ThingInfo;

import android.app.Activity;
import android.view.View.OnClickListener;

public interface ThumbnailOnClickListenerFactory {
	OnClickListener getThumbnailOnClickListener(ThingInfo threadThingInfo, Activity activity);
}