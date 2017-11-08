package com.hansuo.service;

import org.springframework.web.multipart.MultipartFile;

import com.hansuo.common.pojo.PictureResult;

public interface PicService {

	public PictureResult uploadPic(MultipartFile picFile) ;
}
