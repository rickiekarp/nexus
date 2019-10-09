package net.rickiekarp.flc.tasks;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.util.FileUtil;
import net.rickiekarp.core.view.MessageDialog;
import net.rickiekarp.flc.controller.FilelistController;
import net.rickiekarp.flc.model.Directorylist;
import net.rickiekarp.flc.model.Filelist;
import net.rickiekarp.flc.settings.AppConfiguration;
import net.rickiekarp.flc.view.dialogs.ProgressDialog;
import net.rickiekarp.flc.view.layout.MainLayout;
import javafx.concurrent.Task;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListTask extends Task<Void> {

    public static ListTask listTask;
    private File selectedDirectory;
    private int listIdx = 0;

    @Override
    protected Void call() throws Exception {

        //list all files in start directory
        File[] listOfFiles = FileUtil.getListOfFiles(selectedDirectory);

        if (listOfFiles != null) {

            //iterate through start directory
            for (File file : listOfFiles) {

                //is it possible to read the file/folder?
                if (file.canRead()) {

                    //is it a file?
                    if (file.isFile()) {

                        AppConfiguration.INSTANCE.getDirData().get(listIdx).setFilesInDir(1); //increase file count
                        AppConfiguration.INSTANCE.getDirData().get(listIdx).setFileSizeInDir(file.length());

                        BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

                        AppConfiguration.INSTANCE.getFileData().add(new Filelist(
                                        file.getName(),
                                        Filelist.Companion.getExtension(file.getName()),
                                        file.getParentFile().getPath(),
                                        Filelist.Companion.calcFileSize(file),
                                        new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(attrs.creationTime().toMillis()),
                                        new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date(file.lastModified())),
                                        new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(attrs.lastAccessTime().toMillis()),
                                        file.isHidden()
                                )
                        );
                    }

                    //is it a folder?
                    else if (file.isDirectory()) {
                        AppConfiguration.INSTANCE.getDirData().get(listIdx).setFoldersInDir(1); //increase foler count
                        AppConfiguration.INSTANCE.getDirData().add(new Directorylist(file.getPath(), 0, 0, 0, 0)); //add new directory
                    }
                } else {
                    LogFileHandler.logger.warning("Couldn't read files in directory: " + file.getPath());
                }
            }
        }

        int filesTotal = AppConfiguration.INSTANCE.getDirData().get(listIdx).getFilesInDir() + AppConfiguration.INSTANCE.getDirData().get(listIdx).getFoldersInDir();
        AppConfiguration.INSTANCE.getDirData().get(listIdx).setFilesTotal(filesTotal);

        if (AppConfiguration.INSTANCE.getSubFolderCheck()) {
            listIdx++;
            if (listIdx < AppConfiguration.INSTANCE.getDirData().size()) {
                selectedDirectory = new File(AppConfiguration.INSTANCE.getDirData().get(listIdx).getDir());
                call();
            }
        }
        return null;
    }

    public ListTask(File selDir) {

        //create ProgressDialog
        ProgressDialog progressDialog = new ProgressDialog();

        //set variables for list task
        selectedDirectory = selDir;

        this.setOnSucceeded(event1 -> {
            DebugHelper.profile("stop", "ListTask");

            //set items to fileTable
            MainLayout.fileTable.setItems(AppConfiguration.INSTANCE.getFileData());
            MainLayout.dirTable.setItems(AppConfiguration.INSTANCE.getDirData());
            MainLayout.fileControls.getChildren().add(MainLayout.btn_removeAll);
            MainLayout.saveControls.getChildren().addAll(MainLayout.cbox_saveFormat, MainLayout.btn_saveFileList);

            //calculate text length for TextArea
            FilelistController.flController.calcColumnLenght();

            new FilelistPreviewTask();

            progressDialog.close();
            LogFileHandler.logger.info("close.progressDialog");

            MainLayout.mainLayout.setStatus("neutral", LanguageController.getString("ready"));

            LogFileHandler.logger.info("fileScan.COMPLETE - " + AppConfiguration.INSTANCE.getFileData().size() + " files / " + AppConfiguration.INSTANCE.getDirData().size() + " folders scanned!");

        });

        this.setOnCancelled(event1 -> {
            DebugHelper.profile("stop", "ListTask");

            //delete already scanned data
            deleteData();

            //make sure to set listIdx to 0 when cancelling the task
            //fixes a bug where listIdx > 0 when starting the task after cancelling
            resetTask();

            progressDialog.close();
            LogFileHandler.logger.info("close.progressDialog");

            MainLayout.mainLayout.setStatus("neutral", LanguageController.getString("filescan_cancelled"));
            LogFileHandler.logger.info("fileScan.cancelled");
        });

        this.setOnFailed(event -> {
            DebugHelper.profile("stop", "ListTask");
            progressDialog.close();
            new MessageDialog(0, LanguageController.getString("unknownError"), 450, 220);
            LogFileHandler.logger.info("fileScan.failed");
        });

        listTask = this;
        FilelistController.flController = new FilelistController();

        DebugHelper.profile("start", "ListTask");

        //start the task in a new thread
        Thread listThread = new Thread(listTask);
        listThread.setDaemon(true);
        listThread.start();
    }

    public void resetTask() {
        listIdx = 0;
    }

    public void deleteData() {
        //delete already scanned data
        AppConfiguration.INSTANCE.getFileData().clear();
        AppConfiguration.INSTANCE.getDirData().clear();
        MainLayout.fileTable.setItems(null);
        MainLayout.dirTable.setItems(null);
    }
}
