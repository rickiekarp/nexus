package net.rickiekarp.core.net;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.account.Account;
import net.rickiekarp.core.net.provider.NetworkParameterProvider;
import okhttp3.Response;

import java.io.InputStream;

public class NetworkApi {
    protected static String ACCOUNT_DOMAIN = "account";
    protected static String INFO_DOMAIN = "info";
    private static String TOKEN_ACTION = "authorize";
    private static String REGISTER_ACTION = "create";
    private static String LOGIN_ACTION = "login";
    private static String UPDATE_ACTION = "update";
    private static String CHANGELOG_ACTION = "changelog";

    private final ConnectionHandler mConnectionHandler;
    public NetworkApi() {
        mConnectionHandler = new ConnectionHandler();
    }
    public InputStream runNetworkAction(final NetworkAction networkAction) {
        return mConnectionHandler.requestInputStream(networkAction);
    }
    public Response requestResponse(final NetworkAction networkAction) {
        return mConnectionHandler.request(networkAction);
    }

    public static NetworkAction requestAccessToken(Account account) {
        NetworkParameterProvider provider = NetworkParameterProvider.create();
        provider.put("username", account.getUser());
        provider.put("password", account.getPassword());
        return NetworkAction.Builder.create().setHost(NetworkAction.LOGINSERVER).setDomain(ACCOUNT_DOMAIN).setAction(TOKEN_ACTION).setParameters(provider).setMethod("POST").build();
    }


    public static NetworkAction requestLoginData() {
        return NetworkAction.Builder.create().setHost(NetworkAction.LOGINSERVER).setDomain(ACCOUNT_DOMAIN).setAction(LOGIN_ACTION).setMethod("POST").build();
    }

    public static NetworkAction requestCreateAccount(Account account) {
        NetworkParameterProvider provider = NetworkParameterProvider.create();
        provider.put("username", account.getUser());
        provider.put("password", account.getPassword());
        return NetworkAction.Builder.create().setHost(NetworkAction.LOGINSERVER).setDomain(ACCOUNT_DOMAIN).setAction(REGISTER_ACTION).setParameters(provider).setMethod("POST").build();
    }

    public static NetworkAction requestVersionInfo(int updateChannel) {
        NetworkParameterProvider provider = NetworkParameterProvider.create();
        provider.put("identifier", AppContext.getContext().getContextIdentifier());
        provider.put("channel", updateChannel);
        return NetworkAction.Builder.create().setHost(NetworkAction.DATASERVER).setDomain(INFO_DOMAIN).setAction(UPDATE_ACTION).setParameters(provider).setMethod("GET").build();
    }

    public static NetworkAction requestChangelog() {
        NetworkParameterProvider provider = NetworkParameterProvider.create();
        provider.put("identifier", AppContext.getContext().getContextIdentifier());
        return NetworkAction.Builder.create().setHost(NetworkAction.DATASERVER).setDomain(INFO_DOMAIN).setAction(CHANGELOG_ACTION).setParameters(provider).setMethod("GET").build();
    }
}
