package net.rickiekarp.flc.tasks;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.flc.model.Filelist;
import net.rickiekarp.flc.settings.AppConfiguration;
import net.rickiekarp.flc.view.layout.MainLayout;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.File;

public class FileSizeTask extends Task<Void> {

    @Override
    protected Void call() throws Exception {

        //calculate new file amount
        for (int i = 0; i < AppConfiguration.fileData.size(); i++) {
            final Filelist flist = AppConfiguration.fileData.get(i);
            final File file = new File(flist.getFilepath() + File.separator + AppConfiguration.fileData.get(i).getFilename());
            final long fileSize = Filelist.calcFileSize(file);

            final int finalI = i;
            Platform.runLater(() -> AppConfiguration.fileData.set(finalI, flist).setSize(fileSize));
        }
        return null;
    }

    public FileSizeTask() {

        this.setOnRunning(event1 -> {
            MainLayout.mainLayout.setStatus("neutral", LanguageController.getString("status_fileSizeUnitChange"));
        });

        this.setOnSucceeded(event1 -> {
            DebugHelper.profile("stop", "FileSizeTask");
            new FilelistPreviewTask();
        });

        this.setOnFailed(event -> {
            DebugHelper.profile("stop", "FileSizeTask");
            new MessageDialog(0, LanguageController.getString("unknownError"), 450, 220);
            LogFileHandler.logger.info("fileSizeTask.failed");
        });

        DebugHelper.profile("start", "FileSizeTask");

        //start the task in a new thread
        Thread sizeThread = new Thread(this);
        sizeThread.setDaemon(true);
        sizeThread.start();
    }
}
