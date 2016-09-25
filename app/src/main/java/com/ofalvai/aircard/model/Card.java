package com.ofalvai.aircard.model;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Card {

    private static final Gson gson = new Gson();

    public static final int TYPEFACE_NORMAL = 1;

    public static final int TYPEFACE_MONOSPACE = 2;

    public static final int TYPEFACE_SERIF = 3;

    private String name;

    private String tel;

    private String mail;

    private String address;

    private String coordinates;

    private String url;

    private String note;

    private List<CustomField> customFields;

    private int typeface;

    private String color;

    public Card() {
        this.customFields = new ArrayList<>();
    }

    public Card(String name, String tel, String mail, String address, String coordinates, String url, String note, List<CustomField> customFields, int typeface, String color) {
        this.name = name;
        this.tel = tel;
        this.mail = mail;
        this.address = address;
        this.coordinates = coordinates;
        this.url = url;
        this.note = note;
        this.customFields = customFields;
        this.typeface = typeface;
        this.color = color;
    }

    public static Card fromNearbyMessage(Message message) {
        String nearbyMessageString = new String(message.getContent()).trim();

        return gson.fromJson(
                (new String(nearbyMessageString.getBytes(Charset.forName("UTF-8")))),
                Card.class);
    }

    public static Message newNearbyMessage(String name, String tel, String mail, String address,
                                           String coordinates, String url, String note,
                                           List<CustomField> customFields, int typeface, String color) {
        Card card = new Card(name, tel, mail, address, coordinates, url, note, customFields, typeface, color);

        return new Message(gson.toJson(card).getBytes(Charset.forName("UTF-8")));
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getUrl() {
        return url;
    }

    public String getNote() {
        return note;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public int getTypeface() {
        return typeface;
    }

    public String getColor() {
        return color;
    }
}