package com.ofalvai.aircard.model;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.nio.charset.Charset;
import java.util.UUID;

public class Card {

    private static final Gson gson = new Gson();

    private UUID uuid;

    private String name;

    private String phone;

    private String mail;

    private String address;

    private String url;

    private String note;

    private CardStyle cardStyle;

    private String color;

    @Expose(serialize = false, deserialize = false)
    private long timestampSaved;

    public Card() {
        this.uuid = UUID.randomUUID();
    }

    public Card(String name, String phone, String mail, String address, String url, String note, CardStyle cardStyle, String color) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.url = url;
        this.note = note;
        this.cardStyle = cardStyle;
        this.color = color;
    }

    public static Card fromNearbyMessage(Message message) {
        String nearbyMessageString = new String(message.getContent()).trim();

        return gson.fromJson(
                (new String(nearbyMessageString.getBytes(Charset.forName("UTF-8")))),
                Card.class);
    }

    public static Message newNearbyMessage(String name, String tel, String mail, String address,
                                           String url, String note, CardStyle cardStyle, String color) {
        Card card = new Card(name, tel, mail, address, url, note, cardStyle, color);

        return new Message(gson.toJson(card).getBytes(Charset.forName("UTF-8")));
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public String getNote() {
        return note;
    }

    public CardStyle getCardStyle() {
        return cardStyle;
    }

    public String getColor() {
        return color;
    }

    public long getTimestampSaved() {
        return timestampSaved;
    }

    public void setTimestampSaved(long timestampSaved) {
        this.timestampSaved = timestampSaved;
    }
}
