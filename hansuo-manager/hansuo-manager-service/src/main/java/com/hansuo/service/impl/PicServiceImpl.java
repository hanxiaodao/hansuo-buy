package com.hansuo.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hansuo.common.pojo.PictureResult;
import com.hansuo.service.PicService;
import com.hansuo.utils.FastDFSClient;
@Service("picService")
public class PicServiceImpl implements PicService {
	private static Log logger =LogFactory.getLog(PicServiceImpl.class);
	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;
	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		//判断图片是否为空
		if(picFile.isEmpty()){
			result.setError(1);
			result.setMessage("图片为空");
			logger.info("上传图片失败");
			return result;
		}
		//上传图片操作
		try {
			//取图片的扩展名
			String originaFilename= picFile.getOriginalFilename();
			//取扩展名不要“.”
			String extName = originaFilename.substring(originaFilename.lastIndexOf(".")+1);
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String url=client.uploadFile(picFile.getBytes(), extName);
			//拼接url
			url=IMAGE_SERVER_BASE_URL+url;
			//封装picResult
			result.setError(0);
			result.setUrl(url);
			logger.info("上传图片成功:  "+url);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			result.setMessage("图片上传失败");
			logger.info("上传图片失败");
			return result;
		}
		
	}

}
