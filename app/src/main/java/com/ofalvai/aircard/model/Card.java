package com.ofalvai.aircard.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.nio.charset.Charset;
import java.util.UUID;

public class Card {

    private static final Gson gson = new Gson();

    @NonNull
    private UUID uuid;

    private String name;

    private String phone;

    private String mail;

    private String address;

    private String url;

    private String note;

    private CardStyle cardStyle;

    private String color;

    /**
     * Card's local save time in miliseconds since the UNIX epoch
     */
    @Expose(serialize = false, deserialize = false)
    private long timestampSaved;

    public Card() {
        this.uuid = UUID.randomUUID();
    }

    public Card(@Nullable String uuid, String name, String phone, String mail, String address, String url, String note, CardStyle cardStyle, String color) {
        if (uuid == null) {
            // This happens when creating a new Card from the UI
            this.uuid = UUID.randomUUID();
        } else {
            // This happens when a Card is recreated from DB
            this.uuid = UUID.fromString(uuid);
        }
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
        Card card = new Card(null, name, tel, mail, address, url, note, cardStyle, color);

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

    public boolean isColorValid() {
        return getColor() != null && !getColor().isEmpty();
    }

    public long getTimestampSaved() {
        return timestampSaved;
    }

    public void setTimestampSaved(long timestampSaved) {
        this.timestampSaved = timestampSaved;
    }

    public void setTimestampSaved(String timestampString) {
        // SQLite's datetime format doesn't have "T" between date and time, and
        // JodaTime's parser would crash with a format like that
        DateTimeFormatter sqLiteDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = sqLiteDateTimeFormat.parseDateTime(timestampString);
        this.timestampSaved = dateTime.getMillis();
    }
}
