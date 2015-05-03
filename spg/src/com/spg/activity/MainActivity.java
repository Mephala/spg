package com.spg.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import service.provider.common.dto.ImageDto;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.spg.R;
import com.spg.UserSession;
import com.spg.async.AllImageDataListFetcher;
import com.spg.async.ImageFetcher;
import com.spg.utility.ImageUtils;

public class MainActivity extends Activity {

	ImageButton imageButton;
	private ExecutorService executor = Executors.newCachedThreadPool();
	String text = null;
	LinearLayout layout;

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout) findViewById(R.id.mainLayout);
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			try {
				AllImageDataListFetcher allImageDataFetcher = new AllImageDataListFetcher();
				allImageDataFetcher.start();
				allImageDataFetcher.join();
				List<Long> imageIds = allImageDataFetcher.getAllImageIds();
				if (imageIds != null) {
					List<Future<ImageDto>> imageFuturesList = new ArrayList<Future<ImageDto>>();
					for (Long imageId : imageIds) {
						ImageFetcher imageFetcher = new ImageFetcher(imageId);
						imageFuturesList.add(executor.submit(imageFetcher));
					}
					for (@SuppressWarnings("rawtypes")
					Future future : imageFuturesList) {
						while (!future.isDone())
							;
					}
					for (Future<ImageDto> imageFuture : imageFuturesList) {
						ImageDto imageDto = imageFuture.get();
						if (imageDto != null) {
							String encodedText = imageDto.getEncodedData();
							Bitmap bm = ImageUtils.createBitmapFromEncodedString(encodedText);
							Drawable d = new BitmapDrawable(getResources(), bm);
							String buttonText = "Memeli";
							createMainPageButton(d, buttonText);
						}
					}
				}
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("Errorro");
			}
		} else {
			Context context = getApplicationContext();
			CharSequence text = "Baglanti yok aslanim.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	private void createMainPageButton(Drawable d, String buttonText) {
		Button button = new Button(getApplicationContext());
		button.setText(buttonText);
		button.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);
		layout.addView(button);
	}

	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	public void sendMessage(View view) {
		Context context = getApplicationContext();
		CharSequence text = "Hello toast!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			UserSession userSession = UserSession.getInstance();
			userSession.setTakenPicture(imageBitmap);
			Intent intent = new Intent(this, PostGourmetActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.action_gourme_camera) {
			dispatchTakePictureIntent();
		}
		return super.onOptionsItemSelected(item);
	}
}
