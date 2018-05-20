package sd.proj.peter.infotrafficapp.uicontrollers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.common.commands.serialization.SerializeCommand;
import sd.proj.peter.infotrafficapp.common.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText EmailFieldLogin;
    private EditText PassFieldLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up
        EmailFieldLogin = (EditText) findViewById(R.id.email_field_login);
        PassFieldLogin = (EditText) findViewById(R.id.pass_field_login);

        Button FinalizeButtonLogin = (Button) findViewById(R.id.finalize_button_login);
        FinalizeButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button CreateaccButtonLogin = (Button) findViewById(R.id.createacc_button_login);
        CreateaccButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateAcc();
            }
        });
    }

    private void attemptLogin()
    {
        //Reset errors
        EmailFieldLogin.setError(null);
        PassFieldLogin.setError(null);

        //Store values
        String email = EmailFieldLogin.getText().toString();
        String password = PassFieldLogin.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(password)) {
            PassFieldLogin.setError(getString(R.string.error_field_required));
            focusView = PassFieldLogin;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            PassFieldLogin.setError(getString(R.string.error_invalid_password));
            focusView = PassFieldLogin;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            EmailFieldLogin.setError(getString(R.string.error_field_required));
            focusView = EmailFieldLogin;
            cancel = true;
        } else if (!isEmailValid(email)) {
            EmailFieldLogin.setError(getString(R.string.error_invalid_email));
            focusView = EmailFieldLogin;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // attempt login
            // send email & pass to server, request ok/ not ok +reason
            User currentUser = new User(email, email, password);
            System.out.println(new SerializeCommand(currentUser).execute());

            Toast.makeText(LoginActivity.this, getString(R.string.toast_login),
                    Toast.LENGTH_SHORT).show();
            goToAlertList(currentUser);
        }
    }

    private void goToAlertList(User currentUser)
    {
        Intent intent = new Intent(LoginActivity.this, AlertListActivity.class);
        intent.putExtra("EXTRA_CURRENT_USER_SERIALIZED", new SerializeCommand(currentUser).execute());
        startActivity(intent);
    }

    private void goToCreateAcc()
    {
        Intent intent = new Intent(LoginActivity.this, AddAlertActivity.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        return password.length() > 4;
    }
}
