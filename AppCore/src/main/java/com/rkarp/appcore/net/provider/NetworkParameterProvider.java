package com.rkarp.appcore.net.provider;

import com.rkarp.appcore.net.NetworkAction;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class NetworkParameterProvider implements NetworkAction.IParameterProvider {
	private final LinkedHashMap<String, String> mParameterMap = new LinkedHashMap<>();

	private NetworkParameterProvider() {}

	public static NetworkParameterProvider create() {
		return new NetworkParameterProvider();
	}

	public NetworkParameterProvider put(final String key, final String parameter) {
		if (parameter != null) {
			mParameterMap.put(key, String.valueOf(parameter));
		}
		return this;
	}

	public NetworkParameterProvider put(final String key, final boolean parameter) {
		mParameterMap.put(key, String.valueOf(parameter));
		return this;
	}

	public NetworkParameterProvider put(final String key, final int parameter) {
		mParameterMap.put(key, String.valueOf(parameter));
		return this;
	}

	public NetworkParameterProvider put(final String key, final Locale parameter) {
		mParameterMap.put(key, parameter.getLanguage() + "_" + parameter.getCountry());
		return this;
	}

	public NetworkParameterProvider putAll(final Map<String, String> map) {
		if (map != null) {
			mParameterMap.putAll(map);
		}
		return this;
	}

	public NetworkParameterProvider remove(final String key) {
		if (key != null) {
			mParameterMap.remove(key);
		}
		return this;
	}

	@Override
	public Map<String, String> getParameters() {
		return mParameterMap;
	}
}
