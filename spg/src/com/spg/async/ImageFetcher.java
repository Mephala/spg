package com.spg.async;

import java.util.concurrent.Callable;

import service.provider.common.dto.ImageDto;

import com.spg.utility.SPClient;

public class ImageFetcher implements Callable<ImageDto> {

	private final Long imageId;

	public ImageFetcher(Long imageId) {
		this.imageId = imageId;
	}

	@Override
	public ImageDto call() throws Exception {
		return SPClient.getImageById(imageId);
	}
}
