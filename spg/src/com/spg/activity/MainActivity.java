package com.spg.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.spg.R;

public class MainActivity extends Activity {
	private WebView wv;

	// the method to call from the html button click
	@JavascriptInterface
	public void TestMethod(final String formData) {
		System.out.println("TestMethod FormData " + formData);
		try {
			JSONObject jsonData = new JSONObject(formData);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		wv.post(new Runnable() {
			@Override
			public void run() {
				wv.loadUrl("javascript:bar('" + formData + "');");
			}
		});
		Toast.makeText(getApplicationContext(), formData, 5000).show();
		Intent intent = new Intent(this, PostGourmetActivity.class);
		startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alternative_main);
		wv = (WebView) this.findViewById(R.id.webview32);
		// requires javascript
		wv.getSettings().setJavaScriptEnabled(true);
		// make this activity accessible to javascript
		wv.addJavascriptInterface(this, "android");
		// set the html view to load sample.html
		wv.loadUrl("file:///android_asset/sample.html");

	}
}
