package com.sakura.cloud.minio;

import com.sakura.common.minio.item.MinioItem;
import com.sakura.common.minio.template.MinioTemplate;
import io.minio.StatObjectResponse;
import io.minio.messages.Bucket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @auther yangfan
 * @date 2021/6/21
 * @describle minio的api测试
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MinioTest {

    @Autowired
    MinioTemplate minioTemplate;

    /**
     * 检查文件存储桶是否存在
     */
    @Test
    public void bucketExistsTest() {
        boolean test = minioTemplate.bucketExists("test");
        System.err.println(test);
    }

    /**
     * 创建bucket(可重复创建)，若之前的桶中有附件，重新创建附件不丢失
     */
    @Test
    public void createBucketTest() {
        minioTemplate.createBucket("test2");
    }

    /**
     * 获取全部bucket
     */
    @Test
    public void getAllBucketsTest() {
        List<Bucket> allBuckets = minioTemplate.getAllBuckets();
        System.err.println(allBuckets);
    }

    /**
     * 根据bucketName获取信息
     */
    @Test
    public void getBucketTest() {
        Optional<Bucket> test = minioTemplate.getBucket("test");
        Bucket bucket = test.get();
        System.err.println(bucket.name());
    }

    /**
     * 根据bucketName删除信息，桶中存在附件时，会删除失败
     */
    @Test
    public void removeBucketTest() {
        minioTemplate.removeBucket("my-bucketname");
    }

    /**
     *根据文件前缀查询文件
     */
    @Test
    public void getAllObjectsByPrefixTest() {
        List<MinioItem> allObjectsByPrefix = minioTemplate.getAllObjectsByPrefix("test", "", true);
        System.err.println(allObjectsByPrefix);
    }

    /**
     * 获取文件
     */
    @Test
    public void getObjectTest() {
        InputStream test2 = minioTemplate.getObject("test2", "1624267446(1).jpg");
        System.err.println(test2);
    }

    /**
     * 创建文件夹
     * @throws Exception
     */
    @Test
    public void putObjectTest() throws Exception {
        minioTemplate.putObject("test2", "a/b");
    }

    /**
     * 查找文件
     * @throws Exception
     */
    @Test
    public void getObjectInfoTest() throws Exception {
        StatObjectResponse test2 = minioTemplate.getObjectInfo("test", "hh");
        System.err.println(test2.size());
    }

    /**
     * 删除具体路径的文件
     * @throws Exception
     */
    @Test
    public void removeObjectTest() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("my-objectname");
        list.add("yangfan/yangfan/2021/0622/image-20210619115520500.png");
        minioTemplate.removeObject("test", list);
    }






}
