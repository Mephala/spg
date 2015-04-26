package com.spg.async;

import service.provider.common.dto.ImageDto;

import com.spg.utility.SPClient;

public class ImageFetcher extends Thread {

	private static ImageDto imageDto;

	public ImageFetcher(final Long imageId) {
		this(new Runnable() {

			@Override
			public void run() {
				imageDto = SPClient.getImageById(imageId);
			}
		});
	}

	private ImageFetcher(Runnable runnable) {
		super(runnable);
	}

	public ImageDto getImageDto() {
		return imageDto;
	}

}
