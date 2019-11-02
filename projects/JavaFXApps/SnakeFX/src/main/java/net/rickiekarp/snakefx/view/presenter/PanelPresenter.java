package net.rickiekarp.snakefx.view.presenter;

import net.rickiekarp.core.view.AboutScene;
import net.rickiekarp.snakefx.core.SpeedLevel;
import net.rickiekarp.snakefx.view.FXMLFile;
import net.rickiekarp.snakefx.viewmodel.ViewModel;
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
