package com.fefeyo.android.comicmarkertalerter;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class MainActivity extends ActionBarActivity {

    private static final String URL = "http://archetypenova.sakura.ne.jp/comike.json";

    private ProgressDialog mDialog;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private TextView mTitle, mCount_down;

    private void initialize(){
        mPreferences = getSharedPreferences("comike", MODE_PRIVATE);
        mTitle = (TextView)findViewById(R.id.title);
        mCount_down = (TextView)findViewById(R.id.count_down);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        getDate();
    }

    private void getDate() {
        final AsyncHttpClient client = new AsyncHttpClient();
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("読み込み中");
        mDialog.show();
        client.get(
                getApplicationContext(),
                URL,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String result;
                        try {

                            result = new String(responseBody, "UTF-8");
                            final JSONObject base = new JSONObject(result);
                            final JSONObject json = base.getJSONObject("c88");
                            mEditor = mPreferences.edit();
                            mEditor.putInt("year", json.getInt("year"));
                            mEditor.putInt("month", json.getInt("month"));
                            mEditor.putInt("day", json.getInt("day"));
                            mEditor.apply();

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }

                    @Override
                    public void onFinish() {
                        mDialog.dismiss();
                        mTitle.setText("c88の日程は"+mPreferences.getInt("year", 0)+"/"+mPreferences.getInt("month", 0)+"/"+mPreferences.getInt("day", 0));
                    }
                }
        );
    }


}
