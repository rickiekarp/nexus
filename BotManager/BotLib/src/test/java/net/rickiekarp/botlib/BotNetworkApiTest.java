package net.rickiekarp.botlib;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.account.Account;
import net.rickiekarp.core.net.NetResponse;
import net.rickiekarp.core.net.NetworkAction;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.botlib.model.PluginData;
import net.rickiekarp.botlib.net.BotNetworkApi;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class BotNetworkApiTest {
    private PluginData plugin;

    @Before
    public void setBefore() {
        Configuration.config = new Configuration("config.xml", this.getClass());
        AppContext.create("bot-manager", new BotNetworkApi());
        plugin = new PluginData(null, "PluginOne", null, null, null);
        Configuration.host = LoadSave.host;
        AppContext.getContext().getAccountManager().setAccount(new Account("a", "a"));
        AppContext.getContext().getAccountManager().updateAccessToken();
    }

    @Test
    public void testPlugin() {
        NetworkAction validationAction = BotNetworkApi.requestValidation(plugin);
        InputStream inputStream = AppContext.getContext().getNetworkApi().runNetworkAction(validationAction);
        System.out.println(NetResponse.getResponseString(inputStream));
    }

    @Test
    public void testPluginData() {
        NetworkAction validationAction = BotNetworkApi.requestLoginData();
        InputStream inputStream = AppContext.getContext().getNetworkApi().runNetworkAction(validationAction);
        System.out.println(NetResponse.getResponseString(inputStream));
    }

}
