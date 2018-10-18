package com.example.user.qr_try_lokal;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.qr_try_lokal.Helper.ClientServices;
import com.example.user.qr_try_lokal.Model.LoginPost;
import com.example.user.qr_try_lokal.Model.UserPut;
import com.example.user.qr_try_lokal.Rest.ServiceGenerator;
import com.squareup.okhttp.OkHttpClient;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSIONS_REQUEST = 1;
    private Button btScan,bt_login;
    private TextView tvScanResult,tv_username,tv_password;
    OkHttpClient client = new OkHttpClient();
    UserPut user_edit_respon=new UserPut();
    LoginPost loginPost = new LoginPost();
    Retrofit retrofit_2;
    ClientServices services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Testing On Local");

        ///Button
        btScan = (Button) findViewById(R.id.bt_scan);
        bt_login = (Button) findViewById(R.id.login);
        ///Textview
        tvScanResult = (TextView) findViewById(R.id.tv_scanresult);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_password = (TextView) findViewById(R.id.tv_password);
        ///Api
        services= ServiceGenerator.cretaeService(ClientServices.class);
        ///Action Camera
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent capture = new Intent(MainActivity.this, CaptureActivity.class);
                    CaptureActivityIntents.setPromptMessage(capture,"Scanning...");
                    startActivityForResult(capture,0);

            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog= new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading....");
                progressDialog.show();

                String value=tvScanResult.getText().toString();
                String[] value_split=value.split("_");
                String get_username=value_split[0];

                final UserPut userEditReq = new UserPut(get_username,"1");
                Call<UserPut> reqEdit = services.edituser(userEditReq);
                reqEdit.enqueue(new Callback<UserPut>() {
                    @Override
                    public void onResponse(Call<UserPut> call, Response<UserPut> response) {
                        progressDialog.dismiss();
                        user_edit_respon = response.body();
                        System.out.println("Respon OK from Retrofit");
                        Intent in = new Intent(MainActivity.this,Setelah.class);
                        startActivity(in);
                    }

                    @Override
                    public void onFailure(Call<UserPut> call, Throwable t) {
                        progressDialog.dismiss();
                        System.out.println("Respon False from Retrofit");
                        Toast.makeText(MainActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                        System.out.println("false"+t);
                    }
                });
/*
                String user=tv_username.getText().toString();
                String pass=tv_password.getText().toString();

                final UserPut userEditReq = new UserPut(user,"1");
                final LoginPost log_pos=new LoginPost(user,pass);

                Call<LoginPost>post_login = services.logPost(log_pos);
                post_login.enqueue(new Callback<LoginPost>() {
                    @Override
                    public void onResponse(Call<LoginPost> call, Response<LoginPost> response) {
                        progressDialog.dismiss();
                        loginPost = response.body();
                        System.out.println("Respon Login OK from Retrofit");

                        Call<UserPut> reqEdit = services.edituser(userEditReq);
                        reqEdit.enqueue(new Callback<UserPut>() {
                            @Override
                            public void onResponse(Call<UserPut> call, Response<UserPut> response) {
                                progressDialog.dismiss();
                                user_edit_respon = response.body();
                                System.out.println("Respon OK from Retrofit");
                                Intent in = new Intent(MainActivity.this,Setelah.class);
                                startActivity(in);
                            }

                            @Override
                            public void onFailure(Call<UserPut> call, Throwable t) {
                                progressDialog.dismiss();
                                System.out.println("Respon False from Retrofit");
                                Toast.makeText(MainActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                                System.out.println("false"+t);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<LoginPost> call, Throwable t) {
                        progressDialog.dismiss();
                        System.out.println("Respon login False from Retrofit");
                        Toast.makeText(MainActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
                        System.out.println("false"+t);
                    }
                });
                */
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                /*String[] value_split=value.split("_");
                tv_username.setText(value_split[0]);
                tv_password.setText(value_split[1]);*/
                tvScanResult.setText(value);
                ///tvScanResult.setText("Berhasil Mendekripsi Data");
            } else if (resultCode == Activity.RESULT_CANCELED) {
                /*tv_username.setText("-");
                tv_password.setText("-");*/
                tvScanResult.setText("Scanning Gagal, mohon coba lagi.");
            }
        } else {
            Toast.makeText(MainActivity.this,"Cannot decrypt QR",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
