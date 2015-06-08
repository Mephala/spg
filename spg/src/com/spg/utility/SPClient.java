package com.spg.utility;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import service.provider.client.executor.ServiceClient;
import service.provider.common.core.RequestApplication;
import service.provider.common.core.ResponseStatus;
import service.provider.common.dto.ImageDto;
import service.provider.common.request.GetAllImageIdsRequestDto;
import service.provider.common.request.GetImageRequestDto;
import service.provider.common.request.RequestDtoFactory;
import service.provider.common.request.SPGCreatePostRequestDto;
import service.provider.common.request.SaveImageRequestDto;
import service.provider.common.response.GetAllImageIdsResponseDto;
import service.provider.common.response.GetImageResponseDto;
import service.provider.common.response.SPGCreatePostResponseDto;
import service.provider.common.response.SaveImageResponseDto;
import android.graphics.Bitmap;

public class SPClient {

	private static final String SPC_URL = "http://192.168.2.85:8080/";

	static {
		// ServiceClient.initialize(SPC_URL);
	}

	public static ImageDto getImageById(Long id) {
		if (id == null)
			return null;
		GetImageRequestDto getImageRequest = RequestDtoFactory.createGetImageRequest(RequestApplication.SPG);
		getImageRequest.setImageId(id);
		GetImageResponseDto getImageResponse = ServiceClient.getImage(getImageRequest);
		if (getImageResponse == null)
			return null;
		return getImageResponse.getImageDto();
	}

	public static void savePicture(Bitmap takenPicture) {
		if (takenPicture == null)
			return;
		String encodedData = ImageUtils.createEncodedStringFromBitmap(takenPicture);
		SaveImageRequestDto request = RequestDtoFactory.createSaveImageRequestDto(RequestApplication.SPG);
		request.setImageData(encodedData);
		request.setUserId(155l);
		SaveImageResponseDto response = ServiceClient.saveImage(request);
		if (response != null)
			System.err.println("Saved Image id:" + response.getId());
	}

	public static boolean postGourme(final SPGCreatePostRequestDto request) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Boolean> successFuture = executor.submit(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				SPGCreatePostResponseDto response = ServiceClient.spgMakePost(request);
				if (response == null)
					return Boolean.FALSE;
				if (ResponseStatus.OK.equals(response.getResponseStatus()))
					return Boolean.TRUE;
				return Boolean.FALSE;
			}
		});
		while (!successFuture.isDone()) {
			;
		}
		try {
			return successFuture.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	public static List<Long> getAllImageIds() {
		GetAllImageIdsRequestDto request = RequestDtoFactory.createGetAllImageIdsRequestDto(RequestApplication.SPG);
		GetAllImageIdsResponseDto response = ServiceClient.getAllImageIds(request);
		if (response == null)
			return null;
		return response.getImageIds();
	}

}
