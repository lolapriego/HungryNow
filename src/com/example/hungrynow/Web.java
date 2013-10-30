package com.example.hungrynow;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Web extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("http://www.ginos.es");
	}

}
