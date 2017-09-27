package com.example.utaipei.meetingmanager.http.Model;

import java.util.List;

/**
 * Created by cindy on 2017/9/15.
 */

public class MemberModel {
    private String member_password;
    private String member_email;
    private String member_name;
    private String member_department;
    private String member_phone;
    private int gender;
    private List<PositionModel> position;
    private List<CheckinModel> checkin;


    public void setMemberPassword(String member_password) {
        this.member_password = member_password;
    }
    public String getMemberPassword() {
        return member_password;
    }

    public void setMemberEmail(String member_email) {
        this.member_email = member_email;
    }
    public String getMemberEmail() {
        return member_email;
    }

    public void setMemberName(String member_name) {
        this.member_name = member_name;
    }
    public String getMemberName() {
        return member_name;
    }

    public void setMemberDepartment(String member_department) {
        this.member_department = member_department;
    }
    public String getMemberDepartment() {
        return member_department;
    }

    public void setMemberPhone(String member_phone) {
        this.member_phone = member_phone;
    }
    public String getMemberPhone() {
        return member_phone;
    }

    public void setMemberGender(int gender) {
        this.gender = gender;
    }
    public int getMemberGender() {
        return gender;
    }

    public void setPosition(List<PositionModel> position) {
        this.position = position;
    }
    public List<PositionModel> getPosition() {
        return position;
    }

    public void setCheckin(List<CheckinModel> checkin) {
        this.checkin = checkin;
    }
    public List<CheckinModel> getCheckin() {
        return checkin;
    }

}
