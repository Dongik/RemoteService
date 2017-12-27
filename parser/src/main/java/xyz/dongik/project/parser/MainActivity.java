package xyz.dongik.project.parser;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import xyz.dongik.project.downloader.IDownloaderInterface;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    Button button;
    TextView logView;
    EditText editUrl;
    ScrollView scrollView;
    String URL = "https://m.naver.com";
    Context mContext = this;


    IDownloaderInterface mDownloader;
    boolean isServiceConnected = false;
    boolean isParsed = false;
    List<String> imgUrls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViews();
        mLog("View set");
        startDownloader();
    }

    private void performSendingUrls(List<String> urls){
        mLog("Requesting file listing...");


    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mLog("Service binded!\n");
            mDownloader = IDownloaderInterface.Stub.asInterface(service);
            isServiceConnected = true;
//            performSendingUrls(urls);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mDownloader = null;
            isServiceConnected = false;
            // This method is only invoked when the service quits from the other end or gets killed
            // Invoking exit() from the AIDL interface makes the Service kill itself, thus invoking this.
            mLog("Service disconnected.\n");
        }
    };

    private void startDownloader(){
        Intent serviceIntent = new Intent()
                .setComponent(new ComponentName(
                        "xyz.dongik.project.downloader",
                        "xyz.dongik.project.downloader.DownloadService"));
        mLog("Starting service…\n");
        startService(serviceIntent);
        mLog("Binding service…\n");
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }


    private void setViews(){
        setContentView(R.layout.activity_parse);
        button = findViewById(R.id.button);
        button.setText("Parse");
        scrollView = findViewById(R.id.scrollView);
        logView = findViewById(R.id.logView);
        logView.setText("");
        editUrl = findViewById(R.id.editUrl);
        editUrl.setText(URL);
        setParseMode();

    }

    private boolean setDownloadMode(){
        mLog("setDownloadMode");
        if(!isParsed){
            mLog("Its not Parsed!");
            return false;
        }
        button.setText("Download");
        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isServiceConnected){
                    mLog("send download intent!");
                    for(String imgUrl:imgUrls) {
                        try {
                            mDownloader.enqueueUrl(imgUrl);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        mDownloader.download();
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }else {
                    mLog("No connection!");
                }
            }
        });
        return true;
    }

    private void setParseMode(){
        button.setText("Parse");
        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button.setEnabled(false);
                String url = editUrl.getText().toString();
                mLog("Parse started, URL is "+url);
                if(null!=imgUrls) {
                    imgUrls.clear();
                }
                new ParseTask().execute(url);
            }
        });
    }

    private void mLog(String str){
        Log.d(TAG,str);
//        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        logView.append("\n"+str);
//        scrollView.scrollTo(0,20);
    }


    private class ParseTask extends AsyncTask<String, String, List<String>> {
        protected List<String> doInBackground(String... urls) {
            try {
                String xml = new HttpsClient().getXml(urls[0]);
                publishProgress("XML downloaded! length = "+xml.length());
//                xml.replace()
                imgUrls = new CustomParser().parseImgUrls(xml);
                publishProgress("XML parsed! size = "+imgUrls.size());
//                publishProgress(xml);
                for(String imgUrl:imgUrls){
                    publishProgress(imgUrl);
                }
//                if(isServiceConnected){
//                    for(String imgUrl:imgUrls) {
//                        try {
//                            mDownloader.enqueueUrl(imgUrl);
//                        }catch (RemoteException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                publishProgress("");
            }catch(IOException e){
                e.printStackTrace();
            }
//            button.setEnabled(true);
            return imgUrls;
        }

        protected void onProgressUpdate(String... strs) {
            super.onProgressUpdate(strs);
            mLog(strs[0]);
         }
        @Override
        protected void onPostExecute(List<String> result) {
//            super.onPostExecute(result);
            mLog("Parse finished");
            button.setEnabled(true);
            isParsed = true;
            setDownloadMode();
        }
    }
}
