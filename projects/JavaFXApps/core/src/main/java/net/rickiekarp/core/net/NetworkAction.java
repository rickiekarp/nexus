package net.rickiekarp.core.net;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import javafx.collections.ObservableList;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NetworkAction {
    static final String LOGINSERVER = "LoginServer";
    public static final String DATASERVER = "AppServer";
    private String mMethod;
    private final Map<String, String> parameterMap;
    private String mHostUrl;
    private final String actionUrl;

    @Deprecated
    private String file;

    @Deprecated
    public static ObservableList<String> hostList;

    NetworkAction(Builder builder) {
        this.mHostUrl = builder.mHostURL;
        this.mMethod = builder.mMethod;
        this.actionUrl = builder.mDomain + "/" + builder.mAction;
        this.parameterMap = builder.mParameters != null ? builder.mParameters.getParameters() : new HashMap<>(0);
        file = builder.mFile;
    }

    public interface IParameterProvider {
        Map<String, String> getParameters();
    }

    @Deprecated
    String getFile() {
        return file;
    }

    String getHostUrl() {
        return mHostUrl;
    }

    String getActionUrl() {
        return actionUrl;
    }

    Map<String, String> getParameterMap() {
        return parameterMap;
    }

    String getMethod() {
        return mMethod;
    }

    /**
     * @return Use ConnectionHandler class instead
     */
    @Deprecated
    public String requestStringFromResponse() {
        URL programURL = null;
            try {
                programURL = new URL(Configuration.host + "files/apps/" + AppContext.getContext().getContextIdentifier() + "/" + file);

                HttpURLConnection con = (HttpURLConnection) programURL.openConnection();
                con.setDoOutput(false);
                con.setConnectTimeout(5000);
                con.setReadTimeout(20000);
                con.setRequestProperty("User-Agent", AppContext.getContext().getContextIdentifier() + "-" + AppContext.getContext().getInternalVersion());
                con.setRequestMethod(mMethod);

                BufferedInputStream in = new BufferedInputStream(con.getInputStream());
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    LogFileHandler.logger.info("Server connection response: " + responseCode + " (OK)");
                } else {
                    LogFileHandler.logger.info("Server connection response: " + responseCode);
                }

                StringBuilder builder = new StringBuilder();
                int chars_read;
                while ((chars_read = in.read()) != -1) {
                    char g = (char) chars_read;
                    builder.append(g);
                }
                final String xmlSource = builder.toString();
                con.disconnect();

                return xmlSource;
            } catch (SocketTimeoutException e) {
                LogFileHandler.logger.warning("Connection timed out!");
                if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); }
                else { LogFileHandler.logger.warning(ExceptionHandler.getExceptionString(e)); }
            } catch (IOException e1) {
                LogFileHandler.logger.warning("Can not connect to: " + programURL);
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); }
                else { LogFileHandler.logger.warning(ExceptionHandler.getExceptionString(e1)); }
                return "file_not_found";
            }
        return "no_connection";
    }

    /**
     * Checks one of the listed servers for a new program version
     * If the connection to one server fails, another one is tried
     * @return Response as a string
     * @deprecated Use ConnectionHandler class instead
     */
    @Deprecated
    public static String sendRequest(String filename) {
        URL programURL = null;
        for (int i = 0; i < hostList.size(); i++) {
            try {
                programURL = new URL(hostList.get(i) + filename);

                HttpURLConnection con = (HttpURLConnection) programURL.openConnection();
                con.setDoOutput(false);
                con.setConnectTimeout(5000);
                con.setReadTimeout(20000);
                con.setRequestProperty("User-Agent", AppContext.getContext().getContextIdentifier() + "-" + AppContext.getContext().getInternalVersion());
                con.setRequestMethod("GET");

                BufferedInputStream in = new BufferedInputStream(con.getInputStream());
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    LogFileHandler.logger.info("Server connection response: " + responseCode + " (OK)");
                } else {
                    LogFileHandler.logger.info("Server connection response: " + responseCode);
                }

                StringBuilder builder = new StringBuilder();
                int chars_read;
                while ((chars_read = in.read()) != -1) {
                    char g = (char) chars_read;
                    builder.append(g);
                }
                final String xmlSource = builder.toString();
                con.disconnect();

                //set the host server to download the updates from
                //hostToUse = i;

                return xmlSource;
            } catch (SocketTimeoutException e) {
                LogFileHandler.logger.warning("Connection timed out!");
                if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); }
                else { LogFileHandler.logger.warning(ExceptionHandler.getExceptionString(e)); }
            } catch (IOException e1) {
                LogFileHandler.logger.warning("Can not connect to: " + programURL);
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); }
                else { LogFileHandler.logger.warning(ExceptionHandler.getExceptionString(e1)); }
                if (i++ == hostList.size()) { return "file_not_found"; }
            }
        }
        return "no_connection";
    }

    /**
     * General NetworkAction Builder class
     */
    public static final class Builder {
        private String   			mHostURL;
        private String   			mDomain;
        private String             	mAction;
        private IParameterProvider 	mParameters;
        private String   			mMethod;

        @Deprecated
        private String   			mFile = null;

        public static Builder create() {
            return new Builder();
        }

        public Builder setHost(final String host) {
            mHostURL = host;
            return this;
        }

        public Builder setDomain(final String domain) {
            mDomain = domain;
            return this;
        }

        public Builder setAction(final String action) {
            mAction = action;
            return this;
        }

        public Builder setParameters(final IParameterProvider parameterProvider) {
            mParameters = parameterProvider;
            return this;
        }

        public Builder setMethod(final String method) {
            mMethod = method;
            return this;
        }

        @Deprecated
        public Builder setFile(final String file) {
            mFile = file;
            return this;
        }

        public NetworkAction build() {
            if (mHostURL == null) {
                LogFileHandler.logger.warning("No host was found, trying default: " + LoadSave.host);
                mHostURL = LoadSave.host;
            }
            return new NetworkAction(this);
        }
    }
}
