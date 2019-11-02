package net.rickiekarp.snakefx;

import javafx.stage.Stage;
import net.rickiekarp.core.AppStarter;
import net.rickiekarp.snakefx.core.Grid;
import net.rickiekarp.snakefx.core.NewGameFunction;
import net.rickiekarp.snakefx.highscore.HighscoreManager;
import net.rickiekarp.snakefx.inject.DependencyInjector;
import net.rickiekarp.snakefx.settings.AppConfiguration;
import net.rickiekarp.snakefx.util.FxmlFactory;
import net.rickiekarp.snakefx.util.KeyboardHandler;
import net.rickiekarp.snakefx.view.MainLayout;
import net.rickiekarp.snakefx.view.presenter.MainPresenter;
import net.rickiekarp.snakefx.view.presenter.PanelPresenter;
import net.rickiekarp.snakefx.viewmodel.ViewModel;

public class MainApp extends AppStarter {

    /**
     * Main Method
     * @param args Program arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setMainClazz(MainApp.class);
        setConfigClazz(AppConfiguration.class);

        setWinType((byte) 1);
        setMinWidth(700);
        setMinHeight(550);
        setWidth(850);
        setHeight(600);

        DependencyInjector dependencyInjector = new DependencyInjector();
        final ViewModel viewModel = dependencyInjector.get(ViewModel.class);

        FxmlFactory fxmlFactory = new FxmlFactory(dependencyInjector);

        final MainPresenter mainPresenter = new MainPresenter(dependencyInjector.get(Grid.class), dependencyInjector.get(NewGameFunction.class));
        final PanelPresenter panelPresenter = new PanelPresenter(viewModel, dependencyInjector.get(NewGameFunction.class));

        dependencyInjector.put(PanelPresenter.class, panelPresenter);

        mainPresenter.initialize();

        setLayout(new MainLayout(fxmlFactory, mainPresenter.getGridContainer(), viewModel, new HighscoreManager()));
        setOnKeyPressedHandler(dependencyInjector.get(KeyboardHandler.class));

        super.start(stage);
    }
}
