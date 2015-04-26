package com.spg.utility;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageUtils {

	public static Bitmap createBitmapFromEncodedString(String encodedString) {
		byte[] bytez = org.apache.commons.codec.binary.Base64.decodeBase64(encodedString.getBytes());
		Bitmap bm = BitmapFactory.decodeByteArray(bytez, 0, bytez.length);
		return bm;
	}

	public static String createEncodedStringFromBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
		return encoded;
	}

}
