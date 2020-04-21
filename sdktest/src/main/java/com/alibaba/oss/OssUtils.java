package com.alibaba.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class OssUtils {
    /**
     * 从OSS服务中下载所需文件到本地临时文件
     * @param endponit oss对外服务的域名
     * @param accessKeyId 用户身份认证标识
     * @param accessKeySecret 用于加密签名字符串，oss用来验证签名字符串的秘钥
     * @param bucketName 要访问的存储空间
     * @param objectNames 要下载的对象/文件
     * @return
     */
    public static void main(String[] args) {
        List<String> names = Arrays.asList("lingshi/01第一节(纯手写SpringMVC框架-项目演示).mp4");

        String s = downloadOSS("oss-cn-beijing.aliyuncs.com", "accessKeyId", "accessKeySecret", "testlivetest1", names, "d://");
        System.out.println(s);
    }

    private static String downloadOSS(String endponit, String accessKeyId, String accessKeySecret, String bucketName, List<String> objectNames,String filePath){
//        String basePath="src/main/resources/files";
        OSSClient ossClient = null;
        try{
            //创建OSSClient实例，用于操作oss空间
            ossClient = new OSSClient(endponit, accessKeyId, accessKeySecret);
            for (String objectName:objectNames){
                //指定文件保存路径
//                String filePath = basePath+"/"+objectName.substring(0,objectName.lastIndexOf("/"));
                //判断文件目录是否存在，不存在则创建
                File file = new File(filePath);
                if (!file.exists()){
                    file.mkdirs();
                }
                //判断保存文件名是否加后缀
                if (objectName.contains(".")){
                    //指定文件保存名称
                    filePath = filePath+"/"+objectName.substring(objectName.lastIndexOf("/")+1);
                }

                //获取OSS文件并保存到本地指定路径中，此文件路径一定要存在，若保存目录不存在则报错，若保存文件名已存在则覆盖本地文件
                ossClient.getObject(new GetObjectRequest(bucketName,objectName),new File(filePath));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            //关闭oss连接
            if (ossClient != null){
                ossClient.shutdown();
            }
        }
        return filePath;
    }
}
