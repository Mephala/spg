package com.spg.activity;

import java.math.BigDecimal;
import java.util.Random;

import service.provider.common.core.RequestApplication;
import service.provider.common.dto.SPGPostDto;
import service.provider.common.request.RequestDtoFactory;
import service.provider.common.request.SPGCreatePostRequestDto;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spg.R;
import com.spg.UserSession;
import com.spg.utility.Devices;
import com.spg.utility.ImageUtils;
import com.spg.utility.SPClient;

public class PostGourmetActivity extends Activity {

	RelativeLayout layout;
	UserSession userSession = UserSession.getInstance();
	private float textSize = 33f;
	private int imageViewId = 9999;
	private int tasteLabelId = 10000;
	private int speedLabelId = 10001;
	private int tasteRatingId = 10002;
	private int priceLabelId = 10003;
	private int speedRatingId = 10004;
	private int editTextId = 10005;
	private int postButtonId = 10006;
	private int locationEditTextId = 10007;
	private GPSTracker gpsTracker;
	private String deviceName;
	private BigDecimal lattitude;
	private BigDecimal longitude;
	private EditText postCommentText;
	private RatingBar tasteRating;
	private RatingBar serviceRating;
	private RatingBar speedRating;
	private EditText locationText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_gourmet);
		this.gpsTracker = GPSTracker.getInstance(this);
		gpsTracker.getIsGPSTrackingEnabled();
		deviceName = Devices.getDeviceName();

		// Thread saveImageThread = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// UserSession us = UserSession.getInstance();
		// Bitmap takenPicture = us.getTakenPicture();
		// SPClient.savePicture(takenPicture);
		// }
		// });
		// saveImageThread.start();
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
		tasteTextView.setText(getResources().getString(R.string.taste));
		tasteTextView.setTextSize(textSize);
		RelativeLayout.LayoutParams relativeLayoutParameters = createBelowRelativeLayoutParams(imageViewId);
		tasteTextView.setLayoutParams(relativeLayoutParameters);
		tasteTextView.setId(tasteLabelId);
		layout.addView(tasteTextView);
		tasteRating = new RatingBar(this);
		tasteRating.setRating(5);
		tasteRating.setId(tasteRatingId);
		RelativeLayout.LayoutParams tasteViewRLParams = createNextToRightRelativeLayoutParams(tasteLabelId);
		tasteViewRLParams.addRule(RelativeLayout.BELOW, imageViewId);
		tasteRating.setLayoutParams(tasteViewRLParams);
		layout.addView(tasteRating);
		TextView speedTextView = new TextView(this);
		speedTextView.setText(getResources().getString(R.string.speed));
		speedTextView.setTextSize(textSize);
		speedTextView.setId(speedLabelId);
		RelativeLayout.LayoutParams lp = createBelowRelativeLayoutParams(tasteLabelId);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.setMargins(0, 50, 0, 0);
		speedTextView.setId(speedLabelId);
		speedTextView.setLayoutParams(lp);
		layout.addView(speedTextView);
		speedRating = new RatingBar(this);
		speedRating.setRating(5);
		lp = createBelowRelativeLayoutParams(tasteRatingId);
		lp.addRule(RelativeLayout.RIGHT_OF, speedLabelId);
		speedRating.setLayoutParams(lp);
		speedRating.setId(speedRatingId);
		layout.addView(speedRating);
		TextView priceTextView = new TextView(this);
		priceTextView.setText(getResources().getString(R.string.service));
		priceTextView.setId(priceLabelId);
		priceTextView.setTextSize(textSize);
		lp = createBelowRelativeLayoutParams(speedLabelId);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.setMargins(0, 45, 0, 0);
		priceTextView.setLayoutParams(lp);
		layout.addView(priceTextView);
		serviceRating = new RatingBar(this);
		serviceRating.setRating(5);
		lp = createBelowRelativeLayoutParams(speedRatingId);
		lp.addRule(RelativeLayout.RIGHT_OF, priceLabelId);
		serviceRating.setLayoutParams(lp);
		layout.addView(serviceRating);
		locationText = new EditText(this);
		locationText.setId(locationEditTextId);
		lp = createBelowRelativeLayoutParams(priceLabelId);
		locationText.setLayoutParams(lp);
		locationText.setHint(getResources().getString(R.string.unknownLocation));
		layout.addView(locationText);
		postCommentText = new EditText(this);
		postCommentText.setId(editTextId);
		lp = createBelowRelativeLayoutParams(locationEditTextId);
		postCommentText.setLayoutParams(lp);
		postCommentText.setHint(getResources().getString(R.string.enterYourThoughts));
		layout.addView(postCommentText);
		Button postButton = new Button(this);
		postButton.setId(postButtonId);
		lp = createBelowRelativeLayoutParams(editTextId);
		postButton.setLayoutParams(lp);
		postButton.setText(getResources().getString(R.string.postGourme));
		postButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				postGourme();
			}

		});
		layout.addView(postButton);
		longitude = new BigDecimal(gpsTracker.getLongitude());
		lattitude = new BigDecimal(gpsTracker.getLatitude());
		Toast.makeText(getBaseContext(), "Long:" + longitude + " , Lattitude:" + lattitude + " , DeviceName:" + deviceName, Toast.LENGTH_LONG).show();
	}

	private void postGourme() {
		SPGCreatePostRequestDto postRequest = RequestDtoFactory.createSPGPostRequest(RequestApplication.SPG);
		SPGPostDto postDto = new SPGPostDto();
		postDto.setDeviceModel(deviceName);
		UserSession us = UserSession.getInstance();
		Bitmap takenPicture = us.getTakenPicture();
		String encodedData = ImageUtils.createEncodedStringFromBitmap(takenPicture);
		postDto.setImageData(encodedData);
		postDto.setLocationLattitude(lattitude.setScale(4, BigDecimal.ROUND_HALF_UP));
		postDto.setLocationLongitude(longitude.setScale(4, BigDecimal.ROUND_HALF_UP));
		postDto.setPostComment(postCommentText.getText().toString());
		postDto.setService(new BigDecimal(serviceRating.getRating()));
		postDto.setSpeed(new BigDecimal(speedRating.getRating()));
		BigDecimal tasteDecimal = new BigDecimal(tasteRating.getRating());
		postDto.setTaste(tasteDecimal);
		postDto.setLocationName(locationText.getText().toString());
		Random random = new Random();
		postDto.setUserId(Math.abs(random.nextLong()));
		postRequest.setPostDto(postDto);
		Boolean postGourmeResult = SPClient.postGourme(postRequest);
		if (Boolean.TRUE.equals(postGourmeResult)) {
			Toast.makeText(getBaseContext(), "Post Success", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getBaseContext(), "!!!!Post FAIL!!!", Toast.LENGTH_LONG).show();
		}
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
