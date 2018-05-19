import common.model.Alert;
import dao.daos.AlertDAO;
import dao.daos.LikesDAO;

import java.util.List;

public class tester {
    public static void main(String[] args) {
        //Alert a = new Alert("police", "45.000000,55.000000", "31.01.1997; 21:00", "deleted", 4);
        Alert a = AlertDAO.findById(1);
        //System.out.println(LikesDAO.count(a));
        List<Alert> alerts = AlertDAO.findAllActive();
        for(Alert s : alerts)
        {
            System.out.println(s.toString());
        }
        AlertDAO.delete(a);

        alerts = AlertDAO.findAllActive();
        for(Alert s : alerts)
        {
            System.out.println(s.toString());
        }
    }
}
