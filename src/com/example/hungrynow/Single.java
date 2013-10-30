package com.example.hungrynow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Single extends Activity {
	private Product item;
	
	private Button bSave;
	private Button bCancel;
	private TextView pName;
	private TextView priceS;
	private TextView priceM;
	private TextView priceL;
	private EditText quantS;
	private EditText quantM;
	private EditText quantL;
	private Button bInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single);
		
		Bundle extras = getIntent().getExtras();		
		item = extras.getParcelable("PRODUCT");
		
		bSave = (Button) findViewById(R.id.save);
		bCancel = (Button) findViewById(R.id.cancel);
		pName = (TextView) findViewById(R.id.name);
		priceS = (TextView) findViewById(R.id.priceS);
		priceM = (TextView) findViewById(R.id.priceM);
		priceL = (TextView) findViewById(R.id.priceL);
		quantS = (EditText) findViewById(R.id.quantityS);
		quantM = (EditText) findViewById(R.id.quantityM);
		quantL = (EditText) findViewById(R.id.quantityL);
		bInfo = (Button) findViewById(R.id.info);
		
		pName.setText(item.name);
		priceS.setText(Double.toString(item.priceS) + " $");
		priceM.setText(Double.toString(item.priceM) + " $");
		priceL.setText(Double.toString(item.priceL) + " $");
		
		quantS.setText(Integer.toString(item.quantityS));
		quantM.setText(Integer.toString(item.quantityM));
		quantL.setText(Integer.toString(item.quantityL));
		
        bSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	item.quantityS = Integer.parseInt(quantS.getText().toString());
            	item.quantityM = Integer.parseInt(quantM.getText().toString());
            	item.quantityL = Integer.parseInt(quantL.getText().toString());
            	
            	Intent i = new Intent();
            	i.putExtra("PRODUCT", item);
            	setResult(RESULT_OK,i);
            	finish();
            }
        });
        
        bCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	setResult(RESULT_CANCELED);
            	finish();
            }
        });
        
		bInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	launcher();
            }
        });
	}
	
	private void launcher(){
        Intent i = new Intent(this, Manager.class);
        startActivity(i);
	}


	


}
