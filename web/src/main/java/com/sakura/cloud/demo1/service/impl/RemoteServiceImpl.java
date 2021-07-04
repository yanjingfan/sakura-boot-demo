package com.sakura.cloud.demo1.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.remote.AppsServiceFeignClient;
import com.sakura.cloud.demo1.service.RemoteService;
import com.sakura.cloud.demo1.vo.CategoryAppJsonVO;
import com.sakura.common.exception.CloudException;
import com.sakura.common.exception.YErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 远程接口调用示例
 *
 * @auther YangFan
 * @Date 2021/1/8 14:00
 */
@Service
public class RemoteServiceImpl implements RemoteService {

    @Autowired
    AppsServiceFeignClient appsServiceFeignClient;

    /**
     * 使用openfeign跨服务查询
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public CategoryAppJsonVO queryUsers(Long page, Long pageSize) {

        try {
            String result = appsServiceFeignClient.queryUsers(page, pageSize);
//            List<CategoryAppVO> list = new ArrayList<>();
            if (StringUtils.isNotBlank(result)) {
                ObjectMapper mapper = new ObjectMapper();
                CategoryAppJsonVO list = mapper.readValue(result, CategoryAppJsonVO.class);

//                JSONObject jsonObject = JSON.parseObject(result);
//                int code = (Integer) jsonObject.get("code");
//                if (code == 200) {
//                    JSONObject obj = (JSONObject) jsonObject.get("data");
//                    list = JSONArray.parseArray(obj.getString("list"), CategoryAppVO.class);
//                } else {
//                    throw new YErrorException("跨服务查询异常!");//已知异常
//                }
                return list;
            }
        } catch (Exception e) {//未知异常，通用处理
            throw new CloudException("查询出错！", e);
        }
        return null;
    }

    /**
     * 使用restTemplate向远程接口发送GET请求
     * @return
     */
    @Override
    public CategoryAppJsonVO getRemoteDate(Long page, Long pageSize) {
        try {
            RestTemplate client = new RestTemplate();

            //模拟远程接口url
            String url = "http://127.0.0.1:8080/sakura/users?page={page}&pageSize={pageSize}";
            Map<String, Object> uriVariables = new HashMap<>();

            //GET请求参数
            uriVariables.put("page", page);
            uriVariables.put("pageSize", pageSize);

            //发送请求
            String result = client.getForObject(url, String.class, uriVariables);

            ObjectMapper mapper = new ObjectMapper();
            CategoryAppJsonVO list = mapper.readValue(result, CategoryAppJsonVO.class);

            //处理返回来的数据
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            if ((Integer)jsonObject.get("code") == 200){
//                JSONObject data = jsonObject.getJSONObject("data");
//                return data;
//            }
            return list;
        } catch (Exception e) {
            throw new CloudException("远程调用接口查询出错！", e);
        }
    }

    /**
     * 使用restTemplate向远程接口发送POST请求
     * @return
     */
    @Override
    public void saveUser(UserDTO userDTO) {
        try {
            //模拟远程接口
            String url = "http://127.0.0.1:8080/sakura/users";

            //往请求体添加参数
            //第一种传参的方式
            /*JSONObject postData = new JSONObject();
            postData.put("userId", userDTO.getUserId());
            postData.put("username", userDTO.getUsername());
            postData.put("tenantId", userDTO.getTenantId());
            String s = sendPostRequest(url, postData);*/

            //第二种传参的方式，由于远程接口的参数是一个userDTO对象，可以直接传过去
            String result = sendPostRequest(url, userDTO);

            //处理返回的结果
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(result, Map.class);
            Integer code = (Integer) map.get("code");
//            JSONObject jsonObject = JSON.parseObject(result);
//            int code = (Integer)jsonObject.get("code");
            if (!code.equals(200)){
                throw new YErrorException("调用远程接口添加用户失败！");
            }
        } catch (Exception e) {
            throw new CloudException("调用远程接口添加用户出错！", e);
        }
    }

    public String sendPostRequest(String url, UserDTO params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //根据实际情况添加请求头参数，这里是瞎几把乱写的
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("loginUserId", "admin");
        headers.add("loginUserOrgId", "1");
        //将请求头部和参数合成一个请求
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(params, headers);
        //执行POST请求
        ResponseEntity<String> entity = client.postForEntity(url, requestEntity, String.class);
        return entity.getBody();
    }

}
