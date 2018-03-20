package Service;

import DataVO.TestCaseVO;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public interface TestCaseService {
    public boolean createTestCase(TestCaseVO testCaseVO,Long testId);

    public boolean deleteTestCase(TestCaseVO testCaseVO);

    public boolean updateTestCase(TestCaseVO testCaseVO);

    public TestCaseVO getTestCaseById(Long id);
}
