package com.example.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://3.139.81.205:5000/";  // aws public IPv4
    ImageButton btn_test,btn_part;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_test = (ImageButton)findViewById(R.id.test_start_button);
        btn_part = (ImageButton)findViewById(R.id.prac_start_button);

        btn_test.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MockTestActivity.class);
            startActivity(intent);
        });

        btn_part.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // 안드로이드폰 고유 ID
        String idByANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(HomeActivity.this, idByANDROID_ID, Toast.LENGTH_SHORT).show();



        // flask 통신
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        // POST TEST
//        RequestBody formbody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("android_id",idByANDROID_ID)
//                .addFormDataPart("url", "Url test")
//                .build();
//
//        Request req = new Request.Builder()
//                .url(BASE_URL + "post")
//                .post(formbody)
//                .build();
//
//        okHttpClient.newCall(req).enqueue(new Callback() {
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                System.out.println(response.body().string());
//                System.out.println("여기야여기");
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                System.out.println("fail");
//                System.out.println("실패했나");
//
//            }
//        });

        // GET TEST
//        Request request = new Request.Builder().url(BASE_URL).build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                System.out.println(response.body().string());
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                System.out.println("get 요청 fail");
//                System.out.println(e.toString());
//            }
//
//        });
    }



}