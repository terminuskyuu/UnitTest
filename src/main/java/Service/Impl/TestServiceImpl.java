package Service.Impl;

import DataVO.TestVO;
import Service.TestService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public class TestServiceImpl implements TestService{


    @Override
    public boolean createTest(TestVO testVO) {
        return false;
    }

    @Override
    public boolean deleteTest(TestVO testVO) {
        return false;
    }

    @Override
    public boolean updateTest(TestVO testVO) {
        return false;
    }

    @Override
    public List<TestVO> getTestByProject(String projectId) {
        return null;
    }

    @Override
    public TestVO getTestById(Long id) {
        return null;
    }
}
