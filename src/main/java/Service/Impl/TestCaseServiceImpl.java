package Service.Impl;

import DataVO.TestCaseVO;
import Entity.TestCase;
import Entity.TestEntity;
import Repository.TestCaseRepository;
import Repository.TestRepository;
import Service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/3/20.
 */
@Service
public class TestCaseServiceImpl implements TestCaseService{

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestCaseRepository testCaseRepo;

    @Override
    public boolean createTestCase(TestCaseVO testCaseVO, Long testId) {
        TestCase testCase=new TestCase(testCaseVO);
        TestEntity test=testRepository.findById(testId);
        test.addTest_case(testCase);
        testRepository.saveAndFlush(test);
        return true;
    }

    @Override
    public boolean deleteTestCase(Long id) {
        TestCase testCase=testCaseRepo.findById(id);
        testCaseRepo.delete(testCase);
        testCaseRepo.flush();
        return true;
    }

    @Override
    public boolean updateTestCase(TestCaseVO testCaseVO) {
        TestCase testCase=new TestCase(testCaseVO);
        testCase.setId(testCaseVO.getId());
        testCaseRepo.saveAndFlush(testCase);
        return true;
    }

    @Override
    public TestCaseVO getTestCaseById(Long id) {
        TestCase testCase=testCaseRepo.findById(id);
        return testCase.toTestCaseVO();
    }
}
