package com.Controller;

import com.DataVO.ReportVO;
import com.Service.TestExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */
@RestController
public class TestExecuteController {
    @Autowired
    TestExecuteService testExecuteService;

    @RequestMapping(value = "/test/java-all", method = RequestMethod.POST)
    public ReportVO javaTestAll(@RequestParam("id") long id){
        return testExecuteService.javaTestAll(id);
    }

    @RequestMapping(value = "/test/java", method = RequestMethod.POST)
    public ReportVO javaTest(@RequestParam("id") long id,@RequestParam("file") List<String> file){
        return testExecuteService.javaTest(file,id);
    }

    @RequestMapping(value = "/test/python", method = RequestMethod.POST)
    public ReportVO pythonTest(@RequestParam("id") long id,@RequestParam("file") List<String> file){
        return testExecuteService.javaTest(file,id);
    }

    @RequestMapping(value = "/test/c", method = RequestMethod.POST)
    public ReportVO cTest(@RequestParam("id") long id,@RequestParam("file") List<String> file){
        return testExecuteService.javaTest(file,id);
    }

}
