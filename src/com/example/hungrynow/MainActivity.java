package com.example.hungrynow;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ListActivity {
	public static final String ITEM = "Product";
	public static final String QUANTITY = "Quantity";
	public static final String TOTALPRICE = "Total";	
	
	private Button bCancel;
	private Button bOrder;
	private TextView number;
	
	private mPFood mFList;
	private int mPosition; //When calling Single Activity, this will be set as the item selected
	private OrderDbAdapter mDbHelper;
	private int REQUEST_PICK_CONTACT = 1;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        
        mFList = new mPFood();
        
        bOrder = (Button) findViewById(R.id.order);
		bCancel = (Button) findViewById(R.id.discard);
		number = (TextView) findViewById(R.id.number_order);
		
        mDbHelper = new OrderDbAdapter(this);
        mDbHelper.open();
        
        if(savedInstanceState != null) mFList = (mPFood) savedInstanceState.getParcelable("ORDER");               
        

        number.setText(Integer.toString(mDbHelper.count));
        bOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {            	
            	Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, REQUEST_PICK_CONTACT);
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
    		double totalPrice = mFList.mFood[i].quantityS * mFList.mFood[i].priceS + mFList.mFood[i].quantityM * mFList.mFood[i].priceM + mFList.mFood[i].quantityL * mFList.mFood[i].priceL;
    				
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(ITEM, mFList.mFood[i].name);
            map.put(QUANTITY, "Quantity " + Integer.toString(total));
            map.put(TOTALPRICE, "Price: " + Double.toString(totalPrice) + " $");
            
    		mList.add(map);
    	}
    	
    	ListAdapter adapter = new SimpleAdapter(this, mList, R.layout.row, new String[] { ITEM, QUANTITY, TOTALPRICE }, new int[] { R.id.item, R.id.quantity, R.id.total_price });

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
        number.setText(Integer.toString(mDbHelper.count));

        fillData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
       super.onActivityResult(requestCode, resultCode, intent);
       
       if(resultCode == RESULT_OK && requestCode != REQUEST_PICK_CONTACT) {
    	   mFList.mFood[mPosition] = intent.getExtras().getParcelable("PRODUCT");
    	   fillData();
       }
       
       else if(requestCode == REQUEST_PICK_CONTACT && intent != null){
    	   Uri contactUri = intent.getData();
    	   
    	   String[] projection = {Phone.NUMBER};

           // Perform the query on the contact to get the NUMBER column
           // We don't need a selection or sort order (there's only one result for the given URI)
           // CAUTION: The query() method should be called from a separate thread to avoid blocking
           // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
           // Consider using CursorLoader to perform the query.
           Cursor cursor = getContentResolver()
                   .query(contactUri, projection, null, null, null);
           cursor.moveToFirst();

           // Retrieve the phone number from the NUMBER column
           int column = cursor.getColumnIndex(Phone.NUMBER);
           String number = cursor.getString(column);
    	   
    	   mDbHelper.createOrder(mFList, number);
    	   restart();
           Toast.makeText(MainActivity.this, "The Food is on its Way!", Toast.LENGTH_SHORT).show();
       }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem infoMenu = menu.add(0, Menu.FIRST, 0, R.string.info);
        infoMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
		return true;
	}
	
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case Menu.FIRST:
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
            	return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }


}
