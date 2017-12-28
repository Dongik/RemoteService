package xyz.dongik.project.downloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.dongik.project.parser.IParserInterface;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    private DatabaseHelper myDb;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("good");

        Result result = new Result();
        result.setFail(10);
        result.setSuccess(10);
        result.setUrl("https://m.naver.com");

        myDb = new DatabaseHelper(this);
//
        for(int i = 0;i<10;i++) {
            result.setSuccess(i);
            if(myDb.insertResult(result)){
                Log.d(TAG,"insert success");
            }else{
                Log.d(TAG,"insert fail");
            }
        }
        ArrayList<Result> list= myDb.getAllTrials();
//
        tv.append("\nhi size = "+list.size()+"\n");
        for(Result res:list){
            tv.append(res.getUrl()+"||"+res.getFail()+"||"+res.getSuccess()+"||"+res.getTotal()+"\n");
        }

    }
}
