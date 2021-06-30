package com.sakura.cloud;

import com.sakura.common.fastdfs.FastDFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 *
 * fastdfs工具类测试
 *
 * @auther YangFan
 * @Date 2021/2/26 10:53
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileClientApplicationTests {

    @Test
    public void Upload() {
        String fileUrl = this.getClass().getResource("/fileUpload/redis-M-S.png").getPath();
        File file = new File(fileUrl);
        String str = FastDFSClient.uploadFile(file);
        FastDFSClient.getResAccessUrl(str);
    }

    @Test
    public void Delete() {
        //上传附件之后返回的url
        FastDFSClient.deleteFile("group1/M00/00/00/wKgjDGA4Zl2ARQNYAABZAfzrN84686.png");
    }
}
