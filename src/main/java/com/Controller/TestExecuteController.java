package com.Controller;

import com.Common.Language;
import com.DataVO.ReportVO;
import com.Feignclient.FileService;
import com.Service.TestExecuteService;
import com.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */
@RestController
public class TestExecuteController {
    @Autowired
    TestService testService;
    @Autowired
    TestExecuteService testExecuteService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/test/execute-all", method = RequestMethod.POST)
    public ReportVO TestAll(@RequestParam("id") long id,@RequestParam ("username") String username){

        String lan=testService.getTestById(id).getLanguage();
        String projectId=testService.getTestById(id).getProject_id();
        String branch=testService.getTestById(id).getBranch();
        boolean isSuccess=fileService.cloneFile(projectId,branch);
        if(!isSuccess){
            return null;
        }
        if(lan.equalsIgnoreCase(Language.java.toString())){
            return testExecuteService.javaTestAll(id,username);

        }else if(lan.equalsIgnoreCase(Language.python.toString())){
            return testExecuteService.pythonTestAll(id,username);
        }else if(lan.equalsIgnoreCase(Language.c.toString())){
            return testExecuteService.cTestAll(id,username);
        }else{
            return null;
        }


    }

    @RequestMapping(value = "/test/execute", method = RequestMethod.POST)
    public ReportVO executeTest(@RequestParam("id") long id,@RequestParam("file") List<String> file,@RequestParam ("username") String username){
        String lan=testService.getTestById(id).getLanguage();
        String projectId=testService.getTestById(id).getProject_id();
        String branch=testService.getTestById(id).getBranch();
        boolean isSuccess=fileService.cloneFile(projectId,branch);
        if(!isSuccess){
            return null;
        }
        if(lan.equalsIgnoreCase(Language.java.toString())){
            return testExecuteService.javaTest(file,id,username);

        }else if(lan.equalsIgnoreCase(Language.python.toString())){
            return testExecuteService.pythonTest(file,id,username);
        }else if(lan.equalsIgnoreCase(Language.c.toString())){
            return testExecuteService.cTest(file,id,username);
        }else{
            return null;
        }
    }


}
