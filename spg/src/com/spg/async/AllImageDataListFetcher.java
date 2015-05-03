package com.spg.async;

import java.util.List;

import com.spg.utility.SPClient;

public class AllImageDataListFetcher extends Thread {

	private static List<Long> allImageIds;

	public AllImageDataListFetcher() {
		super(new Runnable() {
			@Override
			public void run() {
				allImageIds = SPClient.getAllImageIds();
			}
		});
	}

	public List<Long> getAllImageIds() {
		return allImageIds;
	}

}
