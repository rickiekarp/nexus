package net.rickiekarp.sha1pass;

import net.rickiekarp.sha1pass.settings.AppConfiguration;
import net.rickiekarp.sha1pass.view.MainLayout;
import net.rickiekarp.api.HelloService;
import net.rickiekarp.core.AppStarter;
import net.rickiekarp.core.HelloFactory;

public class MainApp extends AppStarter {

    /**
     * Main Method
     * @param args Program arguments
     */
    public static void main(String[] args) {
        final HelloService service = HelloFactory.createService();
        System.out.println(service.hi("hello"));

        setMainClazz(MainApp.class);
        setConfigClazz(AppConfiguration.class);
        setLayout(new MainLayout());

        launch(args);

    }
}
