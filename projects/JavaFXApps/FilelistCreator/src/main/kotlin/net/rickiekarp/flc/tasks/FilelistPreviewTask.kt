package net.rickiekarp.flc.tasks

import net.rickiekarp.core.controller.LanguageController
import net.rickiekarp.core.debug.DebugHelper
import net.rickiekarp.core.debug.LogFileHandler
import net.rickiekarp.core.view.MessageDialog
import net.rickiekarp.flc.controller.FilelistController
import javafx.concurrent.Task
import net.rickiekarp.flc.view.layout.MainLayout

class FilelistPreviewTask : Task<Void>() {
    private var listStr: String? = null

    @Throws(Exception::class)
    override fun call(): Void? {
        listStr = FilelistController.flController!!.list
        return null
    }

    init {
        this.setOnRunning { event -> MainLayout.mainLayout.setStatus("neutral", LanguageController.getString("status_build_fileList")) }

        this.setOnSucceeded { event1 ->
            MainLayout.previewTA.text = listStr
            DebugHelper.profile("stop", "FilelistPreviewTask")
            MainLayout.mainLayout.setStatus("neutral", LanguageController.getString("ready"))
            listStr = null
            System.gc()
        }

        this.setOnFailed { event ->
            DebugHelper.profile("stop", "FilelistPreviewTask")
            listStr = null
            System.gc()
            MessageDialog(0, LanguageController.getString("unknownError"), 450, 220)
            LogFileHandler.logger.info("filePreview.failed")
        }

        MainLayout.previewTA.clear()
        DebugHelper.profile("start", "FilelistPreviewTask")

        //        System.out.println("availableProcessors" + Runtime.getRuntime().availableProcessors());
        //        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
        //        for (int i = 0; i < threads.length; i++) {
        //            threads[i] = new Thread(new Runnable() {
        //                public void run() {
        //                    // some code to run in parallel
        //                }
        //            });
        //            threads[i].start();
        //        }

        //start the task in a new thread
        val previewThread = Thread(this)
        previewThread.isDaemon = true
        previewThread.start()
    }

}
