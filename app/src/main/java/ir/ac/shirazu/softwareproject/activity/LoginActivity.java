package ir.ac.shirazu.softwareproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ir.ac.shirazu.softwareproject.R;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameET, passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.password);
    }

    public void login(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        // onLoginSuccess();
                        // onLoginFailed();
                        String username = usernameET.getText().toString();
                        String password = passwordET.getText().toString();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        if (isVerified(username, password))
                            startActivity(intent);
                        else {
                            Toast.makeText(getApplicationContext(), getString(R.string.not_verified), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private boolean isVerified(String username, String password) {
        // ToDo: Implement the Authenticating operation here!
        return true;
    }

}
