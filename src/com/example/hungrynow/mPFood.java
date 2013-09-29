package com.example.hungrynow;


import android.os.Parcel;
import android.os.Parcelable;

public class mPFood implements Parcelable{
	public Product[] mFood;
	
	public mPFood(){
		mFood = food;
	}
    /* 
     * Save object in parcel
     * See Android documentation for more details. It is done exactly as the documentation explains 
     */
    public void writeToParcel(Parcel out, int flags) {
    	for (int i = 0; i<food.length; i++){
    		out.writeInt(mFood[i].quantityS);
    		out.writeInt(mFood[i].quantityM);
    		out.writeInt(mFood[i].quantityL);
    	}
    }

    public static final Parcelable.Creator<mPFood> CREATOR
            = new Parcelable.Creator<mPFood>() {
        public mPFood createFromParcel(Parcel in) {
            return new mPFood(in);
        }

        public mPFood[] newArray(int size) {
            return new mPFood[size];
        }
    };

    /** recreate object from parcel */
    private mPFood(Parcel in) {
    	for (int i = 0; i<food.length; i++){
    		mFood[i].quantityS = in.readInt();
    		mFood[i].quantityM = in.readInt();
    		mFood[i].quantityL = in.readInt();
    	}    	
    }
	
	public final static Product[] food = {
			new Product("Pizza 4 Cheeses", 6, 8.5, 12, 0, 0, 0) , 
			new Product("BBQ Pizza", 7, 9.5, 14,0, 0, 0) , 
			new Product("Veggie Pizza", 7.2, 8.5, 11,0, 0, 0) , 
			new Product("Special Pizza", 9, 12.5, 15,0, 0, 0) , 
			new Product("Mac and Cheese", 4, 5.5, 7,0, 0, 0) , 
			new Product("Teriyaki Burger", 6, 7.5, 10,0, 0, 0) , 
			new Product("Cheese Burger", 6, 8.5, 12,0, 0, 0) , 
			new Product("Jack Daniel Burger", 6, 8.5, 12,0, 0, 0) , 
			new Product("Surprise", 2, 4.5, 6,0, 0, 0) , 
			new Product("Coke", 2, 2.5, 4,0, 0, 0) 
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
// Initial Data


