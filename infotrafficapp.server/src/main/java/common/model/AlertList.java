package common.model;

import common.model.Alert;

import java.util.ArrayList;
import java.util.List;

public class AlertList {
    private List<Alert> alerts = new ArrayList<Alert>();

    public AlertList() {}

    public AlertList(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }
}
