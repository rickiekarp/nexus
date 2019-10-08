package net.rickiekarp.botlib;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.account.Account;
import net.rickiekarp.core.net.NetResponse;
import net.rickiekarp.core.net.NetworkAction;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.botlib.model.PluginData;
import net.rickiekarp.botlib.net.BotNetworkApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

class BotNetworkApiTest {
    private static PluginData plugin;

    @BeforeAll
    static void setBefore() {
        BotNetworkApi api = new BotNetworkApi();
        AppContext.create("bot-manager", api);
        Configuration.config = new Configuration("config.xml", api.getClass());
        plugin = new PluginData(null, "PluginOne", null, null, null);
        Configuration.host = LoadSave.host;
        AppContext.getContext().initAccountManager();
        AppContext.getContext().getAccountManager().setAccount(new Account("a", "a"));
        Assertions.assertTrue(AppContext.getContext().getAccountManager().updateAccessToken());
    }

    @Test
    void testPlugin() {
        NetworkAction validationAction = BotNetworkApi.requestValidation(plugin);
        InputStream inputStream = AppContext.getContext().getNetworkApi().runNetworkAction(validationAction);
        System.out.println(NetResponse.getResponseString(inputStream));
    }

    @Test
    void testPluginData() {
        NetworkAction validationAction = BotNetworkApi.requestLoginData();
        InputStream inputStream = AppContext.getContext().getNetworkApi().runNetworkAction(validationAction);
        System.out.println(NetResponse.getResponseString(inputStream));
    }

}
