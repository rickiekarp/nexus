package net.rickiekarp.snakefx.view.presenter;

import net.rickiekarp.core.view.AboutScene;
import net.rickiekarp.snakefx.core.SpeedLevel;
import net.rickiekarp.snakefx.view.FXMLFile;
import net.rickiekarp.snakefx.view.ViewModel;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.function.Consumer;

/**
 * UI-Controller class for the fxml file {@link FXMLFile#PANEL}. This presenter
 * handles the actions of the side panel.
 */
@Deprecated
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
	public void showAbout() {
		new AboutScene();
	}

	private final ViewModel viewModel;

	public PanelPresenter(final ViewModel viewModel, final Consumer<?> newGameFunction) {
		this.viewModel = viewModel;
		this.newGameFunction = newGameFunction;
	}

	@FXML
	public void initialize() {
		speed.itemsProperty().get().addAll(SpeedLevel.values());

		points.textProperty().bind(viewModel.getPoints().asString());
		speed.getSelectionModel().selectFirst();

		speed.valueProperty().bindBidirectional(viewModel.getSpeed());

		playPause.disableProperty().bind(viewModel.getCollision());

		viewModel.getGameloopStatus().addListener((observable, oldStatus, newStatus) -> {
            if (Status.STOPPED.equals(newStatus)) {
                playPause.textProperty().set(LABEL_START);
            }
        });
	}

	@FXML
	public void togglePlayPause() {
		final Status status = viewModel.getGameloopStatus().get();
		switch (status) {
		case PAUSED:
			playPause.textProperty().set(LABEL_PAUSE);
			viewModel.getGameloopStatus().set(Status.RUNNING);
			break;
		case RUNNING:
			playPause.textProperty().set(LABEL_RESUME);
			viewModel.getGameloopStatus().set(Status.PAUSED);
			break;
		case STOPPED:
			playPause.textProperty().set(LABEL_PAUSE);
			viewModel.getGameloopStatus().set(Status.RUNNING);
			break;
		}
	}
}
