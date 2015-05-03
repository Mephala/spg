package com.spg.utility;

import java.util.List;

import service.provider.client.executor.ServiceClient;
import service.provider.common.core.RequestApplication;
import service.provider.common.dto.ImageDto;
import service.provider.common.request.GetAllImageIdsRequestDto;
import service.provider.common.request.GetImageRequestDto;
import service.provider.common.request.RequestDtoFactory;
import service.provider.common.request.SaveImageRequestDto;
import service.provider.common.response.GetAllImageIdsResponseDto;
import service.provider.common.response.GetImageResponseDto;
import service.provider.common.response.SaveImageResponseDto;
import android.graphics.Bitmap;

public class SPClient {

	private static final String SPC_URL = "http://213.14.153.57/serviceProvider/";

	static {
		ServiceClient.initialize(SPC_URL);
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

	public static List<Long> getAllImageIds() {
		GetAllImageIdsRequestDto request = RequestDtoFactory.createGetAllImageIdsRequestDto(RequestApplication.SPG);
		GetAllImageIdsResponseDto response = ServiceClient.getAllImageIds(request);
		if (response == null)
			return null;
		return response.getImageIds();
	}

}
