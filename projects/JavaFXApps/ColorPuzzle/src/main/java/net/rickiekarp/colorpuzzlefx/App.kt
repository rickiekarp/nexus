package net.rickiekarp.colorpuzzlefx;

import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import net.rickiekarp.colorpuzzlefx.ai.BogoSolver;
import net.rickiekarp.colorpuzzlefx.ai.Solver;
import net.rickiekarp.colorpuzzlefx.view.MainView;
import net.rickiekarp.colorpuzzlefx.view.MainViewModel;

public class App extends Application {

    public static void main(String...args){
        App.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("ColorPuzzleFX");

        EasyDI context = new EasyDI();
        context.bindInterface(Solver.class, BogoSolver.class);

        MvvmFX.setCustomDependencyInjector(context::getInstance);


        final ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();

        stage.setScene(new Scene(viewTuple.getView()));
        stage.sizeToScene();
        stage.show();

    }
}
