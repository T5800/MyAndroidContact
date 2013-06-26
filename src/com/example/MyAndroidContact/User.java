package com.example.MyAndroidContact;

/**
 * Created with IntelliJ IDEA.
 * User: hijack
 * Date: 13-6-25
 * Time: 上午9:44
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private int imageid;
    private String name;

    private String mobile;
    private String email;

    User(int imageid, String name, String mobile, String email) {
        this.imageid = imageid;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
