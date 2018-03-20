package Service;

import DataVO.ReportVO;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public interface ReportService {
    public boolean createReport(ReportVO reportVO);

    public boolean deleteReport(ReportVO reportVO);

    public List<ReportVO> getReportByTest(String projectId);

    public ReportVO getReportById(Long id);
}
