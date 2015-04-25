package com.spg.utility;

import org.apache.commons.codec.binary.Base64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtils {

	public static Bitmap createBitmapFromEncodedString(String encodedString) {
		byte[] bytez = Base64.decodeBase64(encodedString.getBytes());
		Bitmap bm = BitmapFactory.decodeByteArray(bytez, 0, bytez.length);
		return bm;
	}

}
