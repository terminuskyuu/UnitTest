package Service;

import Entity.Report;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public interface TestExecuteService {
    public Report javaTestAll(String src);

    public Report javaTest(String src,List<String> file);

    public Report pythonTest(String src,List<String> file);

    public Report cTest(String src,List<String> file);
}
