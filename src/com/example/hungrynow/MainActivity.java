package com.example.hungrynow;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class MainActivity extends ListActivity {
	public static final String ITEM = "Product";
	public static final String QUANTITY = "Quantity";
	
	private Button bCancel;
	private Button bOrder;
	
	private mPFood mFList;
	private int mPosition; //When calling Single Activity, this will be set as the item selected
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        
        mFList = new mPFood();
        
        bOrder = (Button) findViewById(R.id.order);
		bCancel = (Button) findViewById(R.id.discard);
        
        if(savedInstanceState != null) mFList = (mPFood) savedInstanceState.getParcelable("ORDER");               
        
        bOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	restart();
	            Toast.makeText(MainActivity.this, "The Food is on its Way!", Toast.LENGTH_SHORT).show();
            }
        });
        
        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	restart();      	
            }
        });
        

    }
    
    
    public void fillData(){
    	ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();    	
    	
    	for(int i = 0; i<mFList.mFood.length; i++){
    		int total = mFList.mFood[i].quantityS + mFList.mFood[i].quantityM + mFList.mFood[i].quantityL;
    				
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(ITEM, mFList.mFood[i].name);
            map.put(QUANTITY, Integer.toString(total));
            
    		mList.add(map);
    	}
    	
    	ListAdapter adapter = new SimpleAdapter(this, mList, R.layout.row, new String[] { ITEM, QUANTITY }, new int[] { R.id.item, R.id.quantity });

    	setListAdapter(adapter);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ORDER", mFList);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillData();
    }      
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mPosition = position;
        
        Intent i = new Intent(this, Single.class);
        i.putExtra("PRODUCT", mFList.mFood[mPosition]);
        startActivityForResult(i, 0);
    }
    
    private void restart (){
    	for(int i = 0; i<mFList.mFood.length; i++){
    		mFList.mFood[i].quantityS = 0;
    		mFList.mFood[i].quantityM = 0;
    		mFList.mFood[i].quantityL = 0;
    	}
        fillData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
       super.onActivityResult(requestCode, resultCode, intent);
       
       if(resultCode == RESULT_OK) mFList.mFood[mPosition] = intent.getExtras().getParcelable("PRODUCT");

       fillData();
    }



}
