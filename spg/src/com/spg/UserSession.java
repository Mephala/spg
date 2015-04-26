package com.spg;

import android.graphics.Bitmap;

public class UserSession {

	private static UserSession instance;
	private Bitmap takenPicture;

	private UserSession() {

	}

	public static synchronized UserSession getInstance() {
		if (instance == null)
			instance = new UserSession();
		return instance;
	}

	public Bitmap getTakenPicture() {
		return takenPicture;
	}

	public void setTakenPicture(Bitmap takenPicture) {
		this.takenPicture = takenPicture;
	}

}
