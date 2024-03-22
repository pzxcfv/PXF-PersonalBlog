package com.blog.personalblog;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;

public class AliyunOssTest {

    public static void main(String[] args) throws Exception {
        String endpoint = "";
        // 从环境变量中获取访问凭证。
        //EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        String ACCESS_KEY_ID="";
        String ACCESS_KEY_SECRET="";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "";
        // 填写Object完整路径，完整路径中不能包含Bucket名称
        String objectName = "01.png";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, ACCESS_KEY_ID,ACCESS_KEY_SECRET);

        try {
            // 填写字符串。
            String content = "Hello OSS，你好世界";

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new FileInputStream("C:\\Users\\asus\\Desktop\\dongli\\01.png"));

            // 如果需要上传时设置存储类型和访问权限
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
