package sd.proj.peter.infotrafficapp.uicontrollers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.common.commands.serialization.DeserializeCommand;
import sd.proj.peter.infotrafficapp.common.commands.serialization.SerializeCommand;
import sd.proj.peter.infotrafficapp.common.model.Alert;
import sd.proj.peter.infotrafficapp.common.model.User;

public class AddAlertActivity extends AppCompatActivity {

    User currentUser;
    Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);

        String s = getIntent().getStringExtra("EXTRA_CURRENT_USER_SERIALIZED");
        currentUser = (User) new DeserializeCommand(s, new User()).execute();

        typeSpinner = (Spinner) findViewById(R.id.type_spinner_add_alert);

        Button FinalizeButtonAddAlert = (Button) findViewById(R.id.finalize_button_add_alert);
        FinalizeButtonAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAlertCreation();
            }
        });

        Button BackButton = (Button) findViewById(R.id.back_button_add_alert);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToAlertList();
            }
        });

    }

    private void goBackToAlertList() {
        finish();
    }

    private void attemptAlertCreation() {
        String type = String.valueOf(typeSpinner.getSelectedItem());
        // TODO: Get location of phone
        String location = "48.067605, 12.862216"; //Fucking, Austria
        String time = new Time(System.currentTimeMillis()).toString(); // JDBC Time format
        String status = "active";
        int user = currentUser.getId();
        Alert newAlert = new Alert(type, location, time, status, user);

        System.out.println(new SerializeCommand(newAlert).execute());

        //TODO: send data to server

        Toast.makeText(AddAlertActivity.this, getString(R.string.toast_alert_created),
                Toast.LENGTH_SHORT).show();
        //finish activity
    }
}
