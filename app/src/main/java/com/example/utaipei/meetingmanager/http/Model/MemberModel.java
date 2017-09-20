package com.example.utaipei.meetingmanager.http.Model;

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

    public String getMemberPassword() {
        return member_password;
    }

    public String getMemberEmail() {
        return member_email;
    }

    public String getMemberName() {
        return member_name;
    }

    public String getMemberDepartment() {
        return member_department;
    }

    public String getMemberPhone() {
        return member_phone;
    }

    public int getMemberGender() {
        return gender;
    }
}
