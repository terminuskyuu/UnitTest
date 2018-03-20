package Service;

import DataVO.TestVO;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public interface TestService {
    public boolean createTest(TestVO testVO);

    public boolean deleteTest(TestVO testVO);

    public boolean updateTest(TestVO testVO);

    public List<TestVO> getTestByProject(String projectId);

    public TestVO getTestById(Long id);

}
