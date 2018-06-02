package com.rkarp.botter.botservice;

import com.rkarp.appcore.AppContext;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.net.NetResponse;
import com.rkarp.appcore.settings.Configuration;
import com.rkarp.appcore.ui.tray.ToolTrayIcon;
import com.rkarp.appcore.view.MainScene;
import com.rkarp.botlib.BotLauncher;
import com.rkarp.botlib.PluginConfig;
import com.rkarp.botlib.model.PluginData;
import com.rkarp.botlib.net.BotNetworkApi;
import com.rkarp.botter.settings.AppConfiguration;
import com.rkarp.botter.view.MainLayout;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.json.JSONObject;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class BotTask extends Task<Void> {
    private Timer timer;
    private TimerTask timerTask;
    private boolean isTimerTaskRunning;
    private int countdownInMillis;

    private BotLauncher launcher;
    private PluginData plugin;

    @Override
    protected Void call() {
        if (canExecutePlugin(plugin)) {
            try {
                launcher.startAppiumService();
                if (launcher.isServiceRunning()) {
                    switch (plugin.getPluginType()) {
                        case WEB:
                            switch (PluginConfig.botType) {
                                case FIREFOX:
                                    launcher.launch(plugin);
                                    break;
                                case CHROME:
                                    launcher.launch(plugin);
                                    break;
                            }
                            break;
                        case ANDROID:
                            launcher.launch(plugin);
                            break;
                    }
                } else {
                    System.out.println("There was a problem while starting the service!");
                    this.cancel();
                }
            } catch (UnreachableBrowserException e) {
                this.cancel();
            } catch (Exception e) {
                System.out.println("Something went wrong! Check the logs for more details!");
                LogFileHandler.logger.warning(ExceptionHandler.getExceptionString(e));
                this.cancel();
            }
        } else {
            this.cancel();
        }
        return null;
    }

    public BotTask(BotLauncher botLauncher, PluginData plugin) {
        this.launcher = botLauncher;
        this.plugin = plugin;
        isTimerTaskRunning = false;

        if (AppConfiguration.canBotRunPeriodical) {
            countdownInMillis = AppConfiguration.runInterval * 1000 * 60;
        }

        // add an exceptionProperty ChangeListener to the task to throw exceptions from the Task.call() method
//        if (DebugHelper.DEBUGVERSION) {
//            botTask.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
//                if(newValue != null) {
//                    Exception ex = (Exception) newValue;
//                    ex.printStackTrace();
//                }
//            });
//        }

        Thread listThread = new Thread(this);
        listThread.setDaemon(true);

        this.setOnSucceeded(event1 -> {
            System.out.println("Bot finished!");
            LogFileHandler.logger.info("Bot finished!");
            MainLayout.mainLayout.setStatus("success", "Finished");
            MainLayout.mainLayout.setLoadBarVisible(false);
            MainLayout.mainLayout.switchMode();
            launcher.stopAppiumService();
            DebugHelper.profile("stop", "BotAction");
            launcher = null;

            if (AppConfiguration.canBotRunPeriodical) {
                LogFileHandler.logger.info("Starting bot timer! Countdown: " + countdownInMillis + "ms");
                MainLayout.mainLayout.switchTimeBox();
                timer = new Timer();
                timer.schedule(
                        timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                isTimerTaskRunning = true;
                                if (!AppConfiguration.canBotRunPeriodical) {
                                    this.cancel();
                                    timer.cancel();
                                    MainLayout.mainLayout.switchTimeBox();
                                    isTimerTaskRunning = false;
                                }

                                if (!MainScene.mainScene.getWindowScene().getWin().getWindowStage().getStage().isShowing() && !Configuration.showTrayIcon) {
                                    this.cancel();
                                    timer.cancel();
                                }

                                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(countdownInMillis),
                                        TimeUnit.MILLISECONDS.toMinutes(countdownInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(countdownInMillis)),
                                        TimeUnit.MILLISECONDS.toSeconds(countdownInMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countdownInMillis)));

                                Platform.runLater(() -> {
                                    //System.out.println("Timer: " + hms);
                                    MainLayout.mainLayout.updateCountdown("" + hms);
                                });

                                if (Configuration.showTrayIcon && countdownInMillis == 15000) {
                                    ToolTrayIcon.icon.displayTrayMessage();
                                }

                                if (countdownInMillis == 0) {
                                    this.cancel();
                                    timer.cancel();
                                    isTimerTaskRunning = false;
                                    Platform.runLater(() -> {
                                        MainLayout.mainLayout.switchTimeBox();
                                        MainLayout.mainLayout.loadBot();
                                    });
                                }
                                countdownInMillis -= 1000;
                            }
                        }, 0, 1000);
            }
        });

        this.setOnCancelled(event1 -> {
            LogFileHandler.logger.info("Bot stopped!");
            MainLayout.mainLayout.setStatus("neutral", "Cancelled");
            MainLayout.mainLayout.setLoadBarVisible(false);
            MainLayout.mainLayout.switchMode();

            if (timer != null) {
                timer.cancel();
                MainLayout.mainLayout.switchTimeBox();
            }
            launcher.stopAppiumService();
            DebugHelper.profile("stop", "BotAction");
            launcher = null;
        });

        this.setOnFailed(event -> {
            LogFileHandler.logger.warning("Bot failed!");
            MainLayout.mainLayout.setStatus("fail", "Failed");
            MainLayout.mainLayout.setLoadBarVisible(false);
            MainLayout.mainLayout.switchMode();
            launcher.stopAppiumService();
            DebugHelper.profile("stop", "BotAction");
            launcher = null;
        });

        MainLayout.mainLayout.setStatus("neutral", "Running");
        DebugHelper.profile("start", "BotAction");

        //start the task in a new thread
        listThread.start();
    }

    public void resetTimer() {
        if (isTimerTaskRunning) {
            timerTask.cancel();
            timer.cancel();
            MainLayout.mainLayout.switchTimeBox();
        }
    }

    /**
     * Whether the bot is allowed to execute the currently selected plugin
     * @param plugin Plugin to validate
     * @return true, if the plugin is allowed by the server
     */
    private boolean canExecutePlugin(PluginData plugin) {
        JSONObject in = NetResponse.getResponseJson(AppContext.getContext().getNetworkApi().runNetworkAction(BotNetworkApi.requestValidation(plugin)));
        return in.getBoolean("result");
    }
}
