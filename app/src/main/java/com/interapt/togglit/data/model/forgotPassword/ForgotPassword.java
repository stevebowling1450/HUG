package com.interapt.togglit.data.model.forgotPassword;

/**
 * Created by miller.barrera on 16/11/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ForgotPassword {

    @SerializedName("email")
    @Expose
    private String email;


    public ForgotPassword(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}