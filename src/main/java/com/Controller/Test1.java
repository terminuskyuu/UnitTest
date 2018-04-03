package com.Controller;

import com.DataVO.MyResponseData;
import com.DataVO.TestCaseVO;
import com.DataVO.TestVO;
import com.Service.TestCaseService;
import com.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Test1 {
    @Autowired
    TestService testService;
    @Autowired
    TestCaseService testCaseService;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public MyResponseData<Boolean> createTest(){
        TestVO testVO=new TestVO();
        testVO.setProject_id("001");
        testVO.setName("aaa");
        testVO.setTestId("01");

        testService.createTest(testVO);
        return new MyResponseData<Boolean>("succeed", new String[]{"成功创建测试！"}, true);
    }

    @RequestMapping(value = "/testcase1", method = RequestMethod.GET)
    public MyResponseData<Boolean> createTestCase(){
        long testId=testService.getTestByProject("001").get(0).getId();
        TestCaseVO testCaseVO=new TestCaseVO();
        testCaseVO.setCaseId("00001");
        testCaseVO.setName("aas");
        testCaseService.createTestCase(testCaseVO,testId);
        return new MyResponseData<Boolean>("succeed", new String[]{"成功创建测试用例！"}, true);
    }

}
