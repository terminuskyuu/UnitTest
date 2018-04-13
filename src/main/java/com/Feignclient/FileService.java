package com.Feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */
@FeignClient(name = "zuul-service")
public interface FileService {

    public boolean cloneFile(String projectId,String branch);

    public List<String> getAllPath(String projectId);

    public boolean uploadFile(String projectId,String path,String branch,String content);

}
