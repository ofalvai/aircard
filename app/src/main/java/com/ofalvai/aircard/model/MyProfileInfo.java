package com.ofalvai.aircard.model;

import android.support.annotation.Nullable;

/**
 * Relevant attributes which can be obtained from the Contacts content provider about the user
 */
public class MyProfileInfo {

    @Nullable
    public String name;

    @Nullable
    public String mail;

    @Nullable
    public String phone;

    @Nullable
    public String address;
}
