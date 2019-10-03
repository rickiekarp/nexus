package net.rickiekarp.core.net;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A handler to add a layer of abstraction between network layer and implementation
 */
class ConnectionHandler {
	private static final String API_STRING							= "/api/";
	private static final MediaType MEDIA_TYPE_MARKDOWN              = MediaType.parse("application/json");
	private static final int MAX_CONNECTION_TIMEOUT_MILLISECONDS    = 15000;
	private static final int MAX_DATA_TRANSFER_TIMEOUT_MILLISECONDS = 40000;
	private static final int NO_PORT                                = -1;
	private static final String UTF_8                               = "UTF-8";
	private static final String HEADER_USER_AGENT                   = "User-Agent";
	private static final String HEADER_AUTHORIZATION                = "Authorization";
	private static final int RESPONSE_ERROR_CODE_SERVER_UNAVAILABLE = 503;
	private final OkHttpClient mHttpClient;

	ConnectionHandler() {
		mHttpClient = new OkHttpClient.Builder()
				.connectTimeout(MAX_CONNECTION_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
				.readTimeout(MAX_DATA_TRANSFER_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
				.build();
	}

	InputStream requestInputStream(final NetworkAction networkAction) {
		Response response = request(networkAction);
		if (response == null) {
			return null;
		}
		return response.body().byteStream();
	}

	Response request(final NetworkAction networkAction) {
		try {
			final URL hostUrl = new URL(Configuration.host + networkAction.getHostUrl());
			return performRequest(hostUrl, networkAction);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Response performRequest(final URL hostUrl, final NetworkAction action) throws IOException {
		String decodedUrl = decodeUrlToString(hostUrl) + API_STRING + action.getActionUrl();

		final Request.Builder builder = new Request.Builder();

		switch (action.getMethod()) {
			case "GET":
				decodedUrl += encodeParametersToString(action.getParameterMap());
				builder.get();
				break;
			case "POST":
				final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, encodeParametersToJson(action.getParameterMap()));
				builder.post(requestBody);
				break;
		}

		final URL composedUrl = new URL(decodedUrl);
		builder.url(composedUrl);

		addHeaders(action.getParameterMap(), builder);

		final Request request = builder.build();
		LogFileHandler.logger.info(request.method() + ": " + decodedUrl);
		//printRequestHeaders(request);
		//printRequestBody(request);

        try {
            final Response response = mHttpClient.newCall(request).execute();
            if (response.code() == RESPONSE_ERROR_CODE_SERVER_UNAVAILABLE) {
                throw new RuntimeException("Error");
            }
			//printResponseHeaders(response);
            return response;
        } catch (ConnectException e) {
            LogFileHandler.logger.severe(e.getMessage());
            return null;
        }
	}

	private void addHeaders(final Map<String, String> parameterMap, final Request.Builder builder) {
		builder.addHeader(HEADER_USER_AGENT, AppContext.getContext().getContextIdentifier() + "/" + AppContext.getContext().getInternalVersion());

		if (AppContext.getContext().getAccountManager().getAccount() != null) {
			if (AppContext.getContext().getAccountManager().getAccount().getAccessToken() != null) {
				builder.addHeader(HEADER_AUTHORIZATION, "Basic " + AppContext.getContext().getAccountManager().getAccount().getAccessToken());
			}
		}
	}

	/**
	 * Puts the parameters of the given parameter map into a JSON Object and returns its string representation
	 * @param params Parameter map to encode
	 * @return JSON String of the parameter map
	 */
	private String encodeParametersToJson(final Map<String, String> params) {
		JSONObject resultJson = new JSONObject();
		for (final Map.Entry<String, String> pair : params.entrySet()) {
			resultJson.put(pair.getKey(), pair.getValue());
		}
		return resultJson.toString();
	}

	/**
	 * Encodes the given parameter map to a URL parameter representation
	 * @param params Parameter map to encode
	 * @return URL representation of the parameter map
	 * @throws UnsupportedEncodingException UnsupportedEncodingException
	 */
	private String encodeParametersToString(final Map<String, String> params) throws UnsupportedEncodingException {
		final StringBuilder result = new StringBuilder();
		boolean isFirst = true;

		for (final Map.Entry<String, String> pair : params.entrySet()) {
			if (isFirst) {
				isFirst = false;
				result.append('?');
			} else {
				result.append('&');
			}

			result.append(URLEncoder.encode(pair.getKey(), UTF_8));
			result.append('=');
			result.append(URLEncoder.encode(pair.getValue(), UTF_8));
		}
		return result.toString();
	}

	private String decodeUrlToString(final URL url) {
		final String port;
		final int portInt = url.getPort();
		if (portInt != NO_PORT) {
			port = ":" + portInt;
		} else {
			port = "";
		}
		return url.getProtocol() + "://" + url.getHost() + port + url.getPath();
	}

	private String bodyToString(final Request request) {
//		try {
//			final Request copy = request.newBuilder().build();
//			final Buffer buffer = new Buffer();
//			copy.body().writeTo(buffer);
//			return buffer.readUtf8();
//		} catch (final IOException e) {
//			e.printStackTrace();
//			return null;
//		}
		return null;
	}

	private void printRequestBody(Request request) {
		if (request.method().equals("POST")) {
			System.out.println();
			System.out.println("Request body:");
			System.out.println(bodyToString(request));
			System.out.println();
		}
	}

	private void printRequestHeaders(Request request) {
		System.out.println();
		System.out.println("Request Headers:");
		for (int i = 0; i < request.headers().size(); i++) {
			System.out.println(request.headers().name(i) + ": " + request.headers().value(i));
		}
		System.out.println();
	}

	private void printResponseHeaders(Response response) {
		System.out.println();
		System.out.println("Response Headers:");
		for (int i = 0; i < response.headers().size(); i++) {
			System.out.println(response.headers().name(i) + ": " + response.headers().value(i));
		}
		System.out.println();
	}
}
