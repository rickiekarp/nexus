package net.rickiekarp.core.net.update;

import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

// This class downloads a file from a URL.
public class FileDownloader extends Observable implements Runnable {

    private ArrayList<String> filesToDownload = new ArrayList<>();

    // Max size of download buffer.
    private static final int MAX_BUFFER_SIZE = 1024;

    // These are the status names.
    public static final String STATUSES[] = {"Paused", "Downloading",
            "Complete", "Cancelled", "Error"};

    // These are the status codes.
    public static final int PAUSED = 0;
    public static final int DOWNLOADING = 1;
    public static final int COMPLETE = 2;
    public static final int CANCELLED = 3;
    public static final int ERROR = 4;

    private URL url; // download URL
    private int size; // size of download in bytes
    private int downloaded; // number of bytes downloaded
    private int status; // current status of download

    // Constructor for Download.
    public FileDownloader(URL url) {
        this.url = url;
        this.filesToDownload.add(getFileName(url));
        begin();
    }

    // Constructor for Download.
    public FileDownloader(URL url, ArrayList<String> downloadList) {
        this.url = url;
        this.filesToDownload = downloadList;
        begin();
    }

    // Get this download's URL.
    public String getUrl() {
        return url.toString();
    }

    // Get this download's size.
    public int getSize() {
        return size;
    }

    // Get this download's progress.
    public float getProgress() {
        return ((float) downloaded / size);
    }

    // Get this download's status.
    public int getStatus() {
        return status;
    }

    // Get download list
    public ArrayList<String> getDownloadList() {
        return filesToDownload;
    }

    // Pause this download.
    private void begin() {
        size = -1;
        downloaded = 0;
        status = PAUSED;

        if (!Configuration.config.getUpdatesDirFile().exists()) { Configuration.config.getUpdatesDirFile().mkdirs(); }

        status = DOWNLOADING;
        stateChanged();

        //start the download
        download();
    }

    // Pause this download.
    public void pause() {
        status = PAUSED;
        stateChanged();
    }

    // Resume this download.
    public void resume() {
        status = DOWNLOADING;
        stateChanged();
        download();
    }

    // Cancel this download.
    public void cancel() {
        status = CANCELLED;
        stateChanged();
    }

    // Mark this download as having an error.
    private void error() {
        status = ERROR;
        stateChanged();
        LogFileHandler.logger.warning("An error occured when trying to update!");
    }

    // Start or resume downloading.
    private void download() {
        Thread thread = new Thread(this);
        thread.start();
    }

    // Download file.
    public void run() {
        RandomAccessFile file = null;
        InputStream stream = null;
        URL downloadURL;
        try {
            downloadURL = new URL(getHostString(url) + "/" + filesToDownload.get(0));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        LogFileHandler.logger.info("Connecting to: " + downloadURL);
        try {
            // Open connection to URL.
            HttpURLConnection connection = (HttpURLConnection) downloadURL.openConnection();

            // Specify what portion of file to download.
            connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

            // Connect to server.
            connection.connect();

            // Make sure response code is in the 200 range.
            if (connection.getResponseCode() / 100 != 2) {
                LogFileHandler.logger.info("error");
                error();
            }

            LogFileHandler.logger.info("Success! Starting download...");

            // Check for valid content length.
            int contentLength = connection.getContentLength();
            LogFileHandler.logger.info("File size: " + contentLength);
            if (contentLength < 1) {
                error();
            }

  /* Set the size for this download if it
     hasn't been already set. */
            if (size == -1) {
                size = contentLength;
                stateChanged();
            }

            // Open file and seek to the end of it.
            file = new RandomAccessFile(Configuration.config.getUpdatesDirFile() + File.separator + getFileName(downloadURL), "rw");
            LogFileHandler.logger.info("Filepointer: " + file.getFilePointer());
            file.seek(downloaded);

            stream = connection.getInputStream();

            while (status == DOWNLOADING) {
    /* Size buffer according to how much of the
       file is left to download. */
                byte buffer[];
                if (size - downloaded > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[size - downloaded];
                }

                // Read from server into buffer.
                int read = stream.read(buffer);
                if (read == -1) { break; }

                // Write buffer to file.
                file.write(buffer, 0, read);
                downloaded += read;
                stateChanged();
            }

  /* Change status to complete if this point was
     reached because downloading has finished. */
            if (status == DOWNLOADING) {
                LogFileHandler.logger.info("Download complete!");

                //remove just downloaded file from the list and check if there are more files to load
                filesToDownload.remove(0);
                if (filesToDownload.size() >= 1) {
                    begin();
                } else {
                    status = COMPLETE;
                    stateChanged();
                    LogFileHandler.logger.info("All done!");
                }
            }
        } catch (IOException e1) {
            // there was some connection problem, or the file did not exist on the server,
            // or your URL was not in the right format.
            LogFileHandler.logger.warning("Can not connect to " + downloadURL);
        } catch (Exception e1) {
            error();
        } finally {
            // Close file.
            if (file != null) {
                try {
                    file.close();
                } catch (Exception e) {
                    System.out.println("file close error");
                }
            }

            // Close connection to server.
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    System.out.println("stream close error");
                }
            }
        }
    }

    // Notify observers that this download's status has changed.
    private void stateChanged() {
        setChanged();
        notifyObservers();
    }

    // Get file name portion of URL.
    private String getFileName(URL url) {
        return url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
    }

    // Get host url portion of URL.
    private String getHostString(URL url) {
        return url.toString().substring(0, url.toString().lastIndexOf("/"));
    }
}