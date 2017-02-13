package com.victor.wolves.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by victor on 2017/1/24.
 * 角色model
 */

public class Role implements Parcelable {

    private String name;
    private boolean isSelected;
    private boolean isAlive;
    private int dieType;
    private boolean lover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getDieType() {
        return dieType;
    }

    public void setDieTye(int dieType) {
        this.dieType = dieType;
    }

    public String getDieTypeStr() {
        if (dieType == DieType.Null.status()) {
            return DieType.Null.type();
        }else if (dieType == DieType.Poison.status()){
            return DieType.Poison.type();
        }else if (dieType == DieType.Exile.status()){
            return DieType.Exile.type();
        }else if (dieType == DieType.Kill.status()){
            return DieType.Kill.type();
        }else if (dieType == DieType.Hunt.status()){
            return DieType.Hunt.type();
        }else if (dieType == DieType.DieForLove.status()){
            return DieType.DieForLove.type();
        }else {
            return "";
        }
    }

    public boolean isLover() {
        return lover;
    }

    public void setLover(boolean lover) {
        this.lover = lover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAlive ? (byte) 1 : (byte) 0);
        dest.writeInt(this.dieType);
        dest.writeByte(this.lover ? (byte) 1 : (byte) 0);
    }

    public Role() {
    }

    protected Role(Parcel in) {
        this.name = in.readString();
        this.isSelected = in.readByte() != 0;
        this.isAlive = in.readByte() != 0;
        this.dieType = in.readInt();
        this.lover = in.readByte() != 0;
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel source) {
            return new Role(source);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };
}
