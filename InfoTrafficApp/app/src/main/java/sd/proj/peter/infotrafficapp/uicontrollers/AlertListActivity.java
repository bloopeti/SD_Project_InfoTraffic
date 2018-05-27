package sd.proj.peter.infotrafficapp.uicontrollers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.client.ClientBeta;
import sd.proj.peter.infotrafficapp.common.commands.serialization.DeserializeCommand;
import sd.proj.peter.infotrafficapp.common.commands.serialization.SerializeCommand;
import sd.proj.peter.infotrafficapp.common.model.Alert;
import sd.proj.peter.infotrafficapp.common.model.AlertList;
import sd.proj.peter.infotrafficapp.common.model.User;
import sd.proj.peter.infotrafficapp.uicontrollers.listAdapters.CustomListAdapter;

public class AlertListActivity extends AppCompatActivity {

    User currentUser;
    AlertList alerts;

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

        ClientBeta clientBeta = new ClientBeta(this);
        clientBeta.execute();

        //TODO: listView controller
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        final ListView alertListView = (ListView) findViewById(R.id.list_alert_list);

        if(alerts == null)
            Log.d("AlertListActivity stuff", "Alert list null!!!");
        else
            Log.d("AlertListActivity stuff", "Alert list NOT null");

        alertListView.setAdapter(new CustomListAdapter(this, 0, (alerts.getAlerts())));

        alertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = alertListView.getItemAtPosition(position);
                Alert fullObject = (Alert) o;
                goToAlertDetails(fullObject);
            }
        });
    }

    public void setAlerts(AlertList alerts) {
        this.alerts = alerts;
    }
}
