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
import net.rickiekarp.snakefx.util.PopupDialogHelper;
import net.rickiekarp.snakefx.view.FXMLFile;
import net.rickiekarp.snakefx.view.MainLayout;
import net.rickiekarp.snakefx.view.presenter.HighscorePresenter;
import net.rickiekarp.snakefx.view.presenter.MainPresenter;
import net.rickiekarp.snakefx.view.presenter.NewScoreEntryPresenter;
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

        setWinType((byte) 0);
        setMinWidth(700);
        setMinHeight(620);
        setWidth(700);
        setHeight(650);

        DependencyInjector dependencyInjector = new DependencyInjector();
        final ViewModel viewModel = dependencyInjector.get(ViewModel.class);

        FxmlFactory fxmlFactory = new FxmlFactory(dependencyInjector);

        final MainPresenter mainPresenter = new MainPresenter(dependencyInjector.get(Grid.class), dependencyInjector.get(NewGameFunction.class));
        final PanelPresenter panelPresenter = new PanelPresenter(viewModel, dependencyInjector.get(NewGameFunction.class));
        final HighscorePresenter highscorePresenter = new HighscorePresenter(viewModel, dependencyInjector.get(HighscoreManager.class));
        final NewScoreEntryPresenter newScoreEntryPresenter = new NewScoreEntryPresenter(dependencyInjector.get(HighscoreManager.class), viewModel);

        dependencyInjector.put(PanelPresenter.class, panelPresenter);
        dependencyInjector.put(HighscorePresenter.class, highscorePresenter);
        dependencyInjector.put(NewScoreEntryPresenter.class, newScoreEntryPresenter);

        mainPresenter.initialize();

        setLayout(new MainLayout(fxmlFactory, mainPresenter.getGridContainer()));
        setOnKeyPressedHandler(dependencyInjector.get(KeyboardHandler.class));

        PopupDialogHelper popupDialogHelper = new PopupDialogHelper(fxmlFactory);
        Stage highScoreStage = popupDialogHelper.createModalDialog(viewModel.highscoreWindowOpen, stage, FXMLFile.HIGHSCORE);
        popupDialogHelper.createModalDialog(viewModel.newHighscoreWindowOpen, highScoreStage, FXMLFile.NEW_HIGHSCORE);

        super.start(stage);
    }
}
