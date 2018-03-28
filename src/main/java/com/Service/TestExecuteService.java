package com.Service;

import com.DataVO.ReportVO;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public interface TestExecuteService {
    public ReportVO javaTestAll(Long testId);

    public ReportVO javaTest(List<String> file,Long testId);

    public ReportVO pythonTest(List<String> file, Long testId);

    public ReportVO cTest(List<String> file,Long testId);
}
