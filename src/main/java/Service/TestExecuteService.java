package Service;

import Entity.Report;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public interface TestExecuteService {
    public Report javaTestAll(Long testId);

    public Report javaTest(List<String> file,Long testId);

    public Report pythonTest(List<String> file,Long testId);

    public Report cTest(List<String> file,Long testId);
}
