package com.spg.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spg.R;
import com.spg.UserSession;
import com.spg.utility.SPClient;

public class PostGourmetActivity extends Activity {

	RelativeLayout layout;
	UserSession userSession = UserSession.getInstance();
	private int imageViewId = 9999;
	private int tasteLabelId = 10000;
	private int speedLabelId = 10001;
	private int tasteRatingId = 10002;
	private int priceLabelId = 10003;
	private int speedRatingId = 10004;
	private float textSize = 33f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_gourmet);
		Thread saveImageThread = new Thread(new Runnable() {

			@Override
			public void run() {
				UserSession us = UserSession.getInstance();
				Bitmap takenPicture = us.getTakenPicture();
				SPClient.savePicture(takenPicture);
			}
		});
		saveImageThread.start();
		layout = (RelativeLayout) findViewById(R.id.postGourmetMainLayout);
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
		imageView.setId(imageViewId);
		LayoutParams imageViewLayoutParameters = new LayoutParams(LayoutParams.MATCH_PARENT, height / 2);
		imageView.setLayoutParams(imageViewLayoutParameters);
		layout.addView(imageView);
		TextView tasteTextView = new TextView(this);
		tasteTextView.setText("Tat :");
		tasteTextView.setTextSize(textSize);
		RelativeLayout.LayoutParams relativeLayoutParameters = createBelowRelativeLayoutParams(imageViewId);
		tasteTextView.setLayoutParams(relativeLayoutParameters);
		tasteTextView.setId(tasteLabelId);
		layout.addView(tasteTextView);
		RatingBar tasteRating = new RatingBar(this);
		tasteRating.setRating(5);
		tasteRating.setId(tasteRatingId);
		RelativeLayout.LayoutParams tasteViewRLParams = createNextToRightRelativeLayoutParams(tasteLabelId);
		tasteViewRLParams.addRule(RelativeLayout.BELOW, imageViewId);
		tasteRating.setLayoutParams(tasteViewRLParams);
		layout.addView(tasteRating);
		TextView speedTextView = new TextView(this);
		speedTextView.setText("Hýz :");
		speedTextView.setTextSize(textSize);
		speedTextView.setId(speedLabelId);
		RelativeLayout.LayoutParams lp = createBelowRelativeLayoutParams(tasteLabelId);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.setMargins(0, 105, 0, 0);
		speedTextView.setId(speedLabelId);
		speedTextView.setLayoutParams(lp);
		layout.addView(speedTextView);
		RatingBar speedRating = new RatingBar(this);
		speedRating.setRating(5);
		lp = createBelowRelativeLayoutParams(tasteRatingId);
		lp.addRule(RelativeLayout.RIGHT_OF, speedLabelId);
		speedRating.setLayoutParams(lp);
		speedRating.setId(speedRatingId);
		layout.addView(speedRating);
		TextView priceTextView = new TextView(this);
		priceTextView.setText("Fiyat :");
		priceTextView.setId(priceLabelId);
		priceTextView.setTextSize(textSize);
		lp = createBelowRelativeLayoutParams(speedLabelId);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		priceTextView.setLayoutParams(lp);
		layout.addView(priceTextView);
		RatingBar priceRating = new RatingBar(this);
		priceRating.setRating(5);
		lp = createBelowRelativeLayoutParams(speedRatingId);
		lp.addRule(RelativeLayout.RIGHT_OF, priceLabelId);
		priceRating.setLayoutParams(lp);
		layout.addView(priceRating);
	}

	private android.widget.RelativeLayout.LayoutParams createNextToRightRelativeLayoutParams(int viewId) {
		RelativeLayout.LayoutParams relativeLayoutParameters = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLayoutParameters.addRule(RelativeLayout.RIGHT_OF, viewId);
		return relativeLayoutParameters;
	}

	private RelativeLayout.LayoutParams createBelowRelativeLayoutParams(int viewId) {
		RelativeLayout.LayoutParams relativeLayoutParameters = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLayoutParameters.addRule(RelativeLayout.BELOW, viewId);
		return relativeLayoutParameters;
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
