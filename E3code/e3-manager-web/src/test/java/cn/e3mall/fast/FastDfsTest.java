package cn.e3mall.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDfsTest {
	@Test
	public void testUpload()throws Exception{
		ClientGlobal.init("F:/01personallearning/01E3/e3-manager-web/src/main/resources/conf/client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		String[] strings = storageClient.upload_file("C:/Users/dud_u/Pictures/bg5.png", "png", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	@Test
	public void testFastDfsClient()throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("F:/01personallearning/01E3/e3-manager-web/src/main/resources/conf/client.conf");
	    String string = fastDFSClient.uploadFile("C:/Users/dud_u/Pictures/beizhu.png");
		System.out.println(string);
	}
}
