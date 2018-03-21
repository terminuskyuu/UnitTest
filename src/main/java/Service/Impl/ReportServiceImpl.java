package Service.Impl;

import DataVO.ReportVO;
import Entity.Report;
import Entity.TestEntity;
import Repository.ReportRepository;
import Repository.TestRepository;
import Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean createReport(ReportVO reportVO,Long testId) {
        Report report=new Report(reportVO);
        TestEntity testEntity=testRepository.findById(testId);
        testEntity.addReports(report);
        testRepository.saveAndFlush(testEntity);
        return true;
    }

    @Override
    public boolean deleteReport(Long id) {
        reportRepository.delete(id);

        return true;
    }

    @Override
    public List<ReportVO> getReportByTest(Long testId) {
        List<ReportVO> list=new ArrayList<ReportVO>();
        for(Report temp:testRepository.findById(testId).getReports()){
            list.add(temp.toReportVO());
        }

        return list;
    }

    @Override
    public ReportVO getReportById(Long id) {
        return reportRepository.findById(id).toReportVO();
    }
}
