package sd.proj.peter.infotrafficapp.uicontrollers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.common.commands.serialization.DeserializeCommand;
import sd.proj.peter.infotrafficapp.common.commands.serialization.SerializeCommand;
import sd.proj.peter.infotrafficapp.common.model.Alert;
import sd.proj.peter.infotrafficapp.common.model.User;

public class AlertListActivity extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);

        String s = getIntent().getStringExtra("EXTRA_CURRENT_USER_SERIALIZED");
        currentUser = (User) new DeserializeCommand(s, new User()).execute();

        Button refreshAlertsButton = (Button) findViewById(R.id.refresh_button_alert_list);
        refreshAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAlerts();
            }
        });

        Button addAlertButton = (Button) findViewById(R.id.add_button_alert_list);
        addAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddAlert();
            }
        });

        //TODO: listView controller
    }

    private void goToAlertDetails(Alert selectedAlert) {
        Intent intent = new Intent(AlertListActivity.this, AddAlertActivity.class);

        Bundle extras = new Bundle();

        //intent.putExtra("EXTRA_CURRENT_USER_SERIALIZED", new SerializeCommand(currentUser).execute());
        //intent.putExtra("EXTRA_SELECTED_ALERT_SERIALIZED", new SerializeCommand(selectedAlert).execute());

        extras.putString("EXTRA_CURRENT_USER_SERIALIZED", new SerializeCommand(currentUser).execute());
        extras.putString("EXTRA_SELECTED_ALERT_SERIALIZED", new SerializeCommand(selectedAlert).execute());
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void goToAddAlert() {
        Intent intent = new Intent(AlertListActivity.this, AddAlertActivity.class);
        intent.putExtra("EXTRA_CURRENT_USER_SERIALIZED", new SerializeCommand(currentUser).execute());
        startActivity(intent);
    }

    private void refreshAlerts() {
    }
}
