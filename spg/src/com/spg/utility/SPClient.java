package com.spg.utility;

import service.provider.client.executor.ServiceClient;
import service.provider.common.core.RequestApplication;
import service.provider.common.dto.ImageDto;
import service.provider.common.request.GetImageRequestDto;
import service.provider.common.request.RequestDtoFactory;
import service.provider.common.response.GetImageResponseDto;

public class SPClient {

	private static final String SPC_URL = "http://192.168.2.85:8080/";

	static {
		ServiceClient.initialize(SPC_URL);
	}

	public static ImageDto getImageById(Long id) {
		if (id == null)
			return null;
		GetImageRequestDto getImageRequest = RequestDtoFactory.createGetImageRequest(RequestApplication.SPG);
		getImageRequest.setImageId(id);
		GetImageResponseDto getImageResponse = ServiceClient.getImage(getImageRequest);
		return getImageResponse.getImageDto();
	}

}
