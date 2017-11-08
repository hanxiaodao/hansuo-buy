package com.hansuo.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.hansuo.utils.FastDFSClient;

public class FastDFSTest {

	@Test
	public void test1() throws FileNotFoundException, IOException, MyException{
//		1、把FastDFS提供的jar包添加到工程中
//		2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init("E:\\project05tao\\hansuo-manager\\hansuo-manager-web\\src\\main\\resources\\properties\\Client.conf");
//		3、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
//		4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
//		5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
//		6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		7、直接调用StorageClient对象方法上传文件即可
		String [] str = storageClient.upload_file("F:\\tupian\\1_gang_strong.jpg", "jpg", null);
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
	}
	@Test
	public void test2() throws Exception{
		FastDFSClient client = new FastDFSClient("E:\\project05tao\\hansuo-manager\\hansuo-manager-web\\src\\main\\resources\\properties\\Client.conf");
		String str = client.uploadFile("F:\\tupian\\6a8792e9720512ed.jpg", "jpg");
		System.out.println(str);
		
	}
}
