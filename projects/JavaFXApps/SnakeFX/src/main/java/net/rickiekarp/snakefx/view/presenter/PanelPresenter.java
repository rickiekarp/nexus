package net.rickiekarp.snakefx.view.presenter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.net.NetResponse;
import net.rickiekarp.core.view.AboutScene;
import net.rickiekarp.snakefx.core.SpeedLevel;
import net.rickiekarp.snakefx.net.SnakeNetworkApi;
import net.rickiekarp.snakefx.util.PopupDialogHelper;
import net.rickiekarp.snakefx.view.FXMLFile;
import net.rickiekarp.snakefx.viewmodel.ViewModel;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import okhttp3.Response;

import java.util.function.Consumer;

/**
 * UI-Controller class for the fxml file {@link FXMLFile#PANEL}. This presenter
 * handles the actions of the side panel.
 */
public class PanelPresenter {

	private static final String LABEL_START = "Start";
	private static final String LABEL_RESUME = "Resume";
	private static final String LABEL_PAUSE = "Pause";
	private final Consumer<?> newGameFunction;

	@FXML
	private Label points;

	@FXML
	private ChoiceBox<SpeedLevel> speed;

	@FXML
	private Button playPause;

	@FXML
	public void newGame() {
		newGameFunction.accept(null);
	}

	@FXML
	public void showHighscores() {
		viewModel.highscoreWindowOpen.set(true);


		String response = NetResponse.Companion.getResponseString(AppContext.Companion.getContext().getNetworkApi().runNetworkAction(SnakeNetworkApi.Companion.requestRanking()));
		System.out.println(response);
	}

	@FXML
	public void showAbout() {
		new AboutScene();
	}

	@FXML
	public void about() {
		viewModel.aboutWindowOpen.set(true);
	}

	private final ViewModel viewModel;

	public PanelPresenter(final ViewModel viewModel, final Consumer<?> newGameFunction) {
		this.viewModel = viewModel;
		this.newGameFunction = newGameFunction;
	}

	@FXML
	public void initialize() {
		speed.itemsProperty().get().addAll(SpeedLevel.values());

		points.textProperty().bind(viewModel.points.asString());
		speed.getSelectionModel().selectFirst();

		speed.valueProperty().bindBidirectional(viewModel.speed);

		playPause.disableProperty().bind(viewModel.collision);

		viewModel.gameloopStatus.addListener((observable, oldStatus, newStatus) -> {
            if (Status.STOPPED.equals(newStatus)) {
                playPause.textProperty().set(LABEL_START);
            }
        });
	}

	@FXML
	public void togglePlayPause() {
		final Status status = viewModel.gameloopStatus.get();
		switch (status) {
		case PAUSED:
			playPause.textProperty().set(LABEL_PAUSE);
			viewModel.gameloopStatus.set(Status.RUNNING);
			break;
		case RUNNING:
			playPause.textProperty().set(LABEL_RESUME);
			viewModel.gameloopStatus.set(Status.PAUSED);
			break;
		case STOPPED:
			playPause.textProperty().set(LABEL_PAUSE);
			viewModel.gameloopStatus.set(Status.RUNNING);
			break;
		}
	}
}
