package com.napier.coursework;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private String name;
    private int iconId;
    private String address;
    private String openingTimes;
    private String info;

    public String getName() { return name; }
    public int getIconId() { return iconId; }
    public String getAddress() { return address; }
    public String getOpeningTimes() { return openingTimes; }
    public String getInfo() { return info; }

    public void setName(String name) { this.name = name; }
    public void setIconId(int iconId) { this.iconId = iconId; }
    public void setAddress(String address) { this.address = address; }
    public void setOpeningTimes(String openingTimes) { this.address = openingTimes; }
    public void setInfo(String info) { this.info = info; }

    public Location(String name, int iconId, String address, String openingTimes, String info) {
        this.name = name;
        this.iconId = iconId;
        this.address = address;
        this.openingTimes = openingTimes;
        this.info = info;
    }

    public Location() { }

    public Location(Parcel in) {
        this.name = in.readString();
        this.iconId = in.readInt();
        this.address = in.readString();
        this.openingTimes = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<Location> CREATOR =
            new Parcelable.Creator<Location>(){
                public Location createFromParcel(Parcel in) {
                    return new Location(in);
                }

                public Location[] newArray(int size) {
                    return new Location[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.iconId);
        dest.writeString(this.address);
        dest.writeString(this.openingTimes);
        dest.writeString(this.info);
    }
}
