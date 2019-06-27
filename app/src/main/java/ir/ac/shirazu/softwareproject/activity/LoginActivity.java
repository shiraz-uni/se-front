package ir.ac.shirazu.softwareproject.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ir.ac.shirazu.softwareproject.R;
import ir.ac.shirazu.softwareproject.server_api.Meal.MyKit;


public class LoginActivity extends AppCompatActivity implements LoginCallBack{

    private EditText usernameET, passwordET;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);



        if (android.os.Build.VERSION.SDK_INT > 9)  ////These loop is for network security
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


    }

    public void login(View view) {

        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        if (username.isEmpty() || password.isEmpty() ){
            Toast.makeText(this,"شماره دانشجویی یا پسوورد خود را وارد کنید.",Toast.LENGTH_LONG).show();
            return;
        }


        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(!(netInfo != null && netInfo.isConnectedOrConnecting())) {
            Toast.makeText(this,"به اینترنت متصل شوید.",Toast.LENGTH_LONG).show();
            Log.d("nett",!(netInfo != null && netInfo.isConnectedOrConnecting())+"");
        }
        else {


            MyKit myKit = new MyKit();
            myKit.setLoginCallBack(this);
            myKit.doLogin(username, password);

        }


    }



    public void startProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("در حال ارتباط با سرور ...");
        progressDialog.show();

    }


    public void finishProgressDialog() {
        progressDialog.dismiss();

    }

    public void makeFailToaste(){
        Toast.makeText(this,R.string.not_verified,Toast.LENGTH_LONG).show();
    }



    @Override
    public void onPreExecute() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startProgressDialog();
            }
        });

    }

    @Override
    public void onPostExecute() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(MyKit.student == null) {
                    finishProgressDialog();
                    makeFailToaste();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    finishProgressDialog();
                    Log.d("tokentoken",MyKit.student.getUser_token());
                }
            }
        });
    }
}
