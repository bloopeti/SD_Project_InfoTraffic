package sd.proj.peter.infotrafficapp.uicontrollers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.common.commands.serialization.SerializeCommand;
import sd.proj.peter.infotrafficapp.common.model.User;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText EmailFieldCreateacc;
    private EditText PassFieldCreateacc;
    private EditText PassRepeatFieldCreateacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        EmailFieldCreateacc = (EditText) findViewById(R.id.email_field_createacc);
        PassFieldCreateacc = (EditText) findViewById(R.id.pass_field_createacc);
        PassRepeatFieldCreateacc = (EditText) findViewById(R.id.pass_repeat_field_createacc);

        Button FinalizeButtonCreateacc = (Button) findViewById(R.id.finalize_button_createacc);
        FinalizeButtonCreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAccountCreation();
            }
        });

        Button BackButtonCreateacc = (Button) findViewById(R.id.back_button_createacc);
        BackButtonCreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToLogin();
            }
        });
    }

    private void attemptAccountCreation() {//Reset errors
        EmailFieldCreateacc.setError(null);
        PassFieldCreateacc.setError(null);
        PassRepeatFieldCreateacc.setError(null);

        //Store values
        String email = EmailFieldCreateacc.getText().toString();
        String password = PassFieldCreateacc.getText().toString();
        String passwordRe = PassRepeatFieldCreateacc.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(!password.equals(passwordRe))
        {
            PassRepeatFieldCreateacc.setError(getString(R.string.error_pass_mismatch));
            focusView = PassFieldCreateacc;
            cancel = true;
        }

        if(TextUtils.isEmpty(password)) {
            PassFieldCreateacc.setError(getString(R.string.error_field_required));
            focusView = PassFieldCreateacc;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            PassFieldCreateacc.setError(getString(R.string.error_invalid_password));
            focusView = PassFieldCreateacc;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            EmailFieldCreateacc.setError(getString(R.string.error_field_required));
            focusView = EmailFieldCreateacc;
            cancel = true;
        } else if (!isEmailValid(email)) {
            EmailFieldCreateacc.setError(getString(R.string.error_invalid_email));
            focusView = EmailFieldCreateacc;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // attempt login
            // send email & pass to server to create new row in db
            User newUser = new User(email, email, password);
            System.out.println(new SerializeCommand(newUser).execute());

            //send data to server TODO

            Toast.makeText(CreateAccountActivity.this, getString(R.string.toast_acc_created),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void goBackToLogin()
    {
        finish();
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
