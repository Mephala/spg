package com.spg.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.spg.R;
import com.spg.UserSession;

public class PostGourmetActivity extends Activity {

	LinearLayout layout;
	UserSession userSession = UserSession.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_gourmet);
		layout = (LinearLayout) findViewById(R.id.postGourmetMainLayout);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setImageBitmap(userSession.getTakenPicture());
		imageView.setAdjustViewBounds(true);
		imageView.setMinimumHeight((height / 2) - 1);
		imageView.setMaxHeight(height / 2);
		imageView.setMinimumWidth((width / 2) - 1);
		imageView.setMaxHeight(width / 2);
		layout.addView(imageView);
		LinearLayout formLayout = new LinearLayout(getApplicationContext());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		formLayout.setLayoutParams(layoutParams);
		layout.addView(formLayout);
		TextView tasteTextView = new TextView(this);
		tasteTextView.setText("Tat :");
		tasteTextView.setTextSize(45f);
		RatingBar tasteRating = new RatingBar(this);
		formLayout.addView(tasteTextView);
		formLayout.addView(tasteRating);
		TextView speedTextView = new TextView(this);
		speedTextView.setText("Hýz :");
		speedTextView.setTextSize(45f);
		RatingBar speedRating = new RatingBar(this);
		formLayout.addView(speedTextView);
		formLayout.addView(speedRating);
		TextView priceTextView = new TextView(this);
		priceTextView.setText("Fiyat :");
		priceTextView.setTextSize(45f);
		RatingBar priceRating = new RatingBar(this);
		formLayout.addView(priceTextView);
		formLayout.addView(priceRating);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_gourmet, menu);
		return true;
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
		return super.onOptionsItemSelected(item);
	}
}
