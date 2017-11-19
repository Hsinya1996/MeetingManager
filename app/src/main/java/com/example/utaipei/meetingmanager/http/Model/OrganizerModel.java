package com.example.utaipei.meetingmanager.http.Model;

/**
 * Created by cindy on 2017/11/19.
 */

public class OrganizerModel {
    private String organizer_email;
    private String organizer_password;
    private String organizer_department;
    private String organizer_phone;

    public void setOrganizerEmail(String organizer_email) {
        this.organizer_email = organizer_email;
    }
    public String getOrganizerEmail() {
        return organizer_email;
    }

    public void setOrganizerPassword(String organizer_password) {
        this.organizer_password = organizer_password;
    }
    public String getOrganizerPassword() {
        return organizer_password;
    }

    public void setOrganizerDepartment(String organizer_department) {
        this.organizer_department = organizer_department;
    }
    public String getOrganizerDepartment() {
        return organizer_department;
    }

    public void setOrganizerPhone(String organizer_phone) {
        this.organizer_phone = organizer_phone;
    }
    public String getOrganizerPhone() {
        return organizer_phone;
    }
}
