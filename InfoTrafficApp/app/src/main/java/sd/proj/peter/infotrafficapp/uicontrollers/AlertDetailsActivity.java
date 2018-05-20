package sd.proj.peter.infotrafficapp.uicontrollers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.common.commands.serialization.DeserializeCommand;
import sd.proj.peter.infotrafficapp.common.model.Alert;
import sd.proj.peter.infotrafficapp.common.model.Like;
import sd.proj.peter.infotrafficapp.common.model.User;

public class AlertDetailsActivity extends AppCompatActivity {

    User currentUser;
    Alert toDisplay;
    TextView type;
    TextView location;
    TextView time;
    TextView submitter;
    TextView likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
/*
        String user_string = getIntent().getStringExtra("EXTRA_CURRENT_USER_SERIALIZED");
        currentUser = (User) new DeserializeCommand(user_string, new User()).execute();
        String alert_string = getIntent().getStringExtra("EXTRA_SELECTED_ALERT_SERIALIZED");
        toDisplay = (Alert) new DeserializeCommand(alert_string, new Alert()).execute();
*/
        Bundle extras = getIntent().getExtras();
        String user_string = extras.getString("EXTRA_CURRENT_USER_SERIALIZED");
        currentUser = (User) new DeserializeCommand(user_string, new User()).execute();
        String alert_string = extras.getString("EXTRA_SELECTED_ALERT_SERIALIZED");
        toDisplay = (Alert) new DeserializeCommand(alert_string, new Alert()).execute();

        setupFields();
        setValues();

        Button likeButton = (Button) findViewById(R.id.like_button_alert_details);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAlert();
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button_add_alert);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToAlertList();
            }
        });
    }

    private void setupFields()
    {
        type = (TextView) findViewById(R.id.type_field_alert_details);
        location = (TextView) findViewById(R.id.location_field_alert_details);
        time = (TextView) findViewById(R.id.time_field_alert_details);
        submitter = (TextView) findViewById(R.id.submitter_field_alert_details);
        likes = (TextView) findViewById(R.id.likes_field_alert_details);
    }

    private void setValues()
    {
        String typeS = getString(R.string.prompt_alert_type) + ": " + toDisplay.getType();
        String locationS = getString(R.string.prompt_alert_location) + ": " + toDisplay.getLocation();
        String timeS = getString(R.string.prompt_alert_time) + ": " + toDisplay.getSubmission_time();
        String submitterS = getString(R.string.prompt_alert_submitter) + ": " + toDisplay.getSubmitting_user();
        String likeS = getString(R.string.prompt_alert_likes) + ": " + "TODO likes nr";
        type.setText(typeS);
        location.setText(locationS);
        time.setText(timeS);
        submitter.setText(submitterS); // TODO: get user by id, display username
        likes.setText(likeS); // TODO: get alert like count
    }

    private void goBackToAlertList() {
        finish();
    }

    private void likeAlert() {
        Like newLike = new Like( currentUser.getId(), toDisplay.getId());
        //TODO: submit data to server
        finish();
    }
}
