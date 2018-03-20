package Service.Impl;

import Entity.Report;
import Service.TestExecuteService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public class TestExecuteServiceImpl implements TestExecuteService{
    @Override
    public Report javaTestAll(String src) {
        return null;
    }

    @Override
    public Report javaTest(String src, List<String> file) {
        return null;
    }

    @Override
    public Report pythonTest(String src, List<String> file) {
        return null;
    }

    @Override
    public Report cTest(String src, List<String> file) {
        return null;
    }

    private Report javaReport(String src){
        return null;
    }

    private Report pythonReport(String src){
        return null;
    }

    private Report cReport(String src){
        return null;
    }
}
