package com.example.hungrynow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity {
	private TextView textNumber;
	private TextView textAddress;
	private Button webButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		textNumber = (TextView) findViewById(R.id.number);
		Linkify.addLinks(textNumber, Linkify.PHONE_NUMBERS);
		
		textAddress = (TextView) findViewById(R.id.address);
		Linkify.addLinks(textAddress, Linkify.MAP_ADDRESSES);
		
		webButton = (Button) findViewById(R.id.web_button);
		webButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	launcher();
            }
        });
		}
	
	private void launcher(){
        Intent i = new Intent(this, Web.class);
        startActivity(i);
	}


}
