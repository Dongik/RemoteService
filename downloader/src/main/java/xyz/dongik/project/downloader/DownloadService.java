package xyz.dongik.project.downloader;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongik on 17. 12. 27.
 */

public class DownloadService extends Service {
    private List<String> imgUrls  = new ArrayList<>();

    private void log(String message) {
        Log.v("DownloadService", message);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("Received start command.");
//        imgUrls = new ArrayList<>();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("Received binding.");
        return mBinder;
    }

    private void startDownload(){
        log("Download Started");

    }

    private final IDownloaderInterface.Stub mBinder = new IDownloaderInterface.Stub(){
        @Override
        public void enqueueUrl(String url){
            imgUrls.add(url);
        }

        @Override
        public void download(){
            startDownload();
        }
    };
}
