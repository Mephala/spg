package com.spg.activity;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import service.provider.common.dto.LightSPGPostDto;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.spg.R;
import com.spg.utility.SPClient;

public class MainActivity extends Activity {
	private WebView wv;
	private GPSTracker gpsTracker;

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
				double lattitude = gpsTracker.getLatitude();
				double longitude = gpsTracker.getLongitude();

				List<LightSPGPostDto> lightSPGPosts = null;
				try {
					lightSPGPosts = SPClient.getNearbyPosts(lattitude, longitude);
				} catch (Exception e) {
					System.err.println(e.getLocalizedMessage());
				}
				if (lightSPGPosts == null) {
					// TODO handle nullness...
				} else {
					LightSPGPostDto lightSpgPost = lightSPGPosts.get(0);
					ObjectMapper mapper = new ObjectMapper();
					try {
						String jsonConversion = mapper.writeValueAsString(lightSpgPost);
						wv.loadUrl("javascript:load('" + jsonConversion + "');");
					} catch (JsonGenerationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	@JavascriptInterface
	public void loadPage() {
		System.out.println("cebuddey");

		wv.post(new Runnable() {
			@Override
			public void run() {
				double lattitude = gpsTracker.getLatitude();
				double longitude = gpsTracker.getLongitude();

				List<LightSPGPostDto> lightSPGPosts = null;
				try {
					lightSPGPosts = SPClient.getNearbyPosts(lattitude, longitude);
				} catch (Exception e) {
					System.err.println(e.getLocalizedMessage());
				}
				if (lightSPGPosts == null) {
					// TODO handle nullness...
				} else {
					LightSPGPostDto lightSpgPost = lightSPGPosts.get(0);
					ObjectMapper mapper = new ObjectMapper();
					try {
						String jsonConversion = mapper.writeValueAsString(lightSPGPosts);
						wv.loadUrl("javascript:load('" + jsonConversion + "');");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gpsTracker = GPSTracker.getInstance(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alternative_main);
		wv = (WebView) this.findViewById(R.id.webview32);
		// requires javascript
		wv.getSettings().setJavaScriptEnabled(true);
		// make this activity accessible to javascript
		wv.addJavascriptInterface(this, "android");
		// set the html view to load sample.html
		double lattitude = gpsTracker.getLatitude();
		double longitude = gpsTracker.getLongitude();

		List<LightSPGPostDto> lightSPGPosts = null;
		try {
			// lightSPGPosts = SPClient.getNearbyPosts(lattitude, longitude);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		if (lightSPGPosts == null) {
			// TODO handle nullness...
		}
		wv.loadUrl("file:///android_asset/home.html");
	}
}
