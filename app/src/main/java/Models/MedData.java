package Models;

import android.os.Parcelable;

import java.io.Serializable;

public class MedData implements Serializable{
    String brand, expdate,manudate,medtype,mg,name,ownedby,price;

    public MedData(){}
    public MedData(String brand, String expdate, String manudate, String medtype, String mg, String name, String ownedby, String price) {
        this.brand = brand;
        this.expdate = expdate;
        this.manudate = manudate;
        this.medtype = medtype;
        this.mg = mg;
        this.name = name;
        this.ownedby = ownedby;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getManudate() {
        return manudate;
    }

    public void setManudate(String manudate) {
        this.manudate = manudate;
    }

    public String getMedtype() {
        return medtype;
    }

    public void setMedtype(String medtype) {
        this.medtype = medtype;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnedby() {
        return ownedby;
    }

    public void setOwnedby(String ownedby) {
        this.ownedby = ownedby;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
