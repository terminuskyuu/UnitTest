package com.Controller;

import com.DataVO.MyResponseData;
import com.DataVO.ReportVO;
import com.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */
@RestController
public class ReportController {
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/report/create", method = RequestMethod.POST)
    public MyResponseData<Boolean> createReport(@RequestParam("report")ReportVO reportVO, @RequestParam("testId") long testId){
        reportService.createReport(reportVO,testId);
        return new MyResponseData<Boolean>("succeed", new String[]{"成功创建缺陷！"}, true);
    }

    @RequestMapping(value = "/report/delete", method = RequestMethod.POST)
    public MyResponseData<Boolean> deleteReport(@RequestParam("id") long id){
        reportService.deleteReport(id);
        return new MyResponseData<Boolean>("succeed", new String[]{"成功删除缺陷！"}, true);
    }

    @RequestMapping(value = "/report/get-by-project", method = RequestMethod.GET)
    public List<ReportVO> getReportByTest(@RequestParam("testId") long id){
        List<ReportVO> reports=reportService.getReportByTest(id);
        return reports;
    }

    @RequestMapping(value = "/report/get-by-id", method = RequestMethod.GET)
    public ReportVO getReportById(@RequestParam("id") long id){
        ReportVO report=reportService.getReportById(id);
        return report;
    }

}
