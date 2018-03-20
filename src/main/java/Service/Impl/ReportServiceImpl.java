package Service.Impl;

import DataVO.ReportVO;
import Service.ReportService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public class ReportServiceImpl implements ReportService{
    @Override
    public boolean createReport(ReportVO reportVO) {
        return false;
    }

    @Override
    public boolean deleteReport(ReportVO reportVO) {
        return false;
    }

    @Override
    public List<ReportVO> getReportByTest(String projectId) {
        return null;
    }

    @Override
    public ReportVO getReportById(Long id) {
        return null;
    }
}
