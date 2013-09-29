package com.example.hungrynow;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
	public String name;
	public double priceS;
	public double priceM;
	public double priceL;
	public int quantityS;
	public int quantityM;
	public int quantityL;

	
	public Product (String name, double priceS, double priceM, double priceL, int quantityS, int quantityM, int quantityL){
		this.name = name;
		this.priceS = priceS;
		this.priceM = priceM;
		this.priceL = priceL;
		this.quantityS = quantityS;
		this.quantityM = quantityM;
		this.quantityL = quantityL;
	}
	
    /* 
     * Save object in parcel
     * See Android documentation for more details. It is done exactly as the documentation explains 
     */
    public void writeToParcel(Parcel out, int flags) {
    	out.writeString(name);
    	out.writeDouble(priceS);
    	out.writeDouble(priceM);
    	out.writeDouble(priceL);
    	out.writeInt(quantityS);
    	out.writeInt(quantityM);
    	out.writeInt(quantityL);
    }
    

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /** recreate object from parcel */
    private Product(Parcel in) {
    	name = in.readString();
    	priceS = in.readDouble();
    	priceM = in.readDouble();
    	priceL = in.readDouble();
    	quantityS = in.readInt();
    	quantityM = in.readInt();
    	quantityL = in.readInt();
    }    	
   

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
