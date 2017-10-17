package com.arnas.shoper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arnaspetrauskas on 17/09/2017.
 */

public class Pricer implements Parcelable {

    public int openHeadId;
    public int itemId;
    public float price;
    public String itemPicture;
    public String pricerLink;

    protected Pricer(Parcel in) {
        openHeadId = in.readInt();
        itemId = in.readInt();
        price = in.readFloat();
        itemPicture = in.readString();
        pricerLink = in.readString();
    }
    public Pricer(int openHeadIds,int itemIds,float prices,String prcLink,String iFoto){
        this.openHeadId=openHeadIds;
        this.itemId=itemIds;
        this.price=prices;
        this.pricerLink=prcLink;
        this.itemPicture=iFoto;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(openHeadId);
        dest.writeInt(itemId);
        dest.writeFloat(price);
        dest.writeString(itemPicture);
        dest.writeString(pricerLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pricer> CREATOR = new Creator<Pricer>() {
        @Override
        public Pricer createFromParcel(Parcel in) {
            return new Pricer(in);
        }

        @Override
        public Pricer[] newArray(int size) {
            return new Pricer[size];
        }
    };

    public int getOpenHeadId() {
        return openHeadId;
    }

    public void setOpenHeadId(int openHeadId) {
        this.openHeadId = openHeadId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }
     public void setPricerLink(String link){
         this.pricerLink=link;
     }
     public String getPricerLink(){
         return pricerLink;
     }
}
