package com.ofalvai.aircard.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.UUID;

public class Card  implements Serializable {

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

    private CardColor color;

    /**
     * Card's local save time in miliseconds since the UNIX epoch
     */
    @Expose(serialize = false, deserialize = false)
    private long timestampSaved;

    public Card() {
        // Empty constructor required for deserialization
        // Note: this uuid is overwritten during deserialization
        this.uuid = UUID.randomUUID();
    }

    public Card(@Nullable String uuid, String name, String phone, String mail, String address, String url, String note, CardStyle cardStyle, CardColor color) {
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
                                           String url, String note, CardStyle cardStyle, CardColor color) {
        Card card = new Card(null, name, tel, mail, address, url, note, cardStyle, color);

        return new Message(gson.toJson(card).getBytes(Charset.forName("UTF-8")));
    }

    public static Message newNearbyMessage(Card card) {
        return new Message(gson.toJson(card).getBytes(Charset.forName("UTF-8")));
    }

    @NonNull
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

    public CardColor getColor() {
        return color;
    }

    public void setUuid(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public long getTimestampSaved() {
        return timestampSaved;
    }

    public void setTimestampSaved(long timestampSavedMillis) {
        this.timestampSaved = timestampSavedMillis;
    }

    public void setTimestampSaved(String timestampString) {
        // SQLite's datetime format doesn't have "T" between date and time, and
        // JodaTime's parser would crash with a format like that
        DateTimeFormatter sqLiteDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = sqLiteDateTimeFormat.parseDateTime(timestampString);
        this.timestampSaved = dateTime.getMillis();
    }

    public void setCardStyle(CardStyle cardStyle) {
        this.cardStyle = cardStyle;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
