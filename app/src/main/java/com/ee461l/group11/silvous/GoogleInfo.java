package com.ee461l.group11.silvous;

/**
 * Created by hotsauce on 11/29/16.
 */

public class GoogleInfo {
    private String userID;
    private String userEmail;

    public GoogleInfo(String ID, String email)
    {
        this.userID = ID;
        this.userEmail = email;
    }

    public String getUserID()
    {
        return this.userID;
    }

    public String getUserEmail()
    {
        return this.userEmail;
    }
}
