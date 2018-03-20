package Entity;
import DataVO.TestCaseVO;

import javax.persistence.*;
/**
 * Created by Administrator on 2018/3/16.
 */
@Entity
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String caseId;

    private String info;
    /*
    private String precondition;

    private String input;

    private String output;


    private String state;
    */
    private String file;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity testEntity;

    public TestCase() {
    }

    public TestCase(String name, String caseId, String info, String file, TestEntity testEntity) {
        this.name = name;
        this.caseId = caseId;
        this.info = info;
        this.file = file;
        this.testEntity = testEntity;
    }

    public TestCase(String name, String caseId, String file, TestEntity testEntity) {
        this.name = name;
        this.caseId = caseId;
        this.file = file;
        this.testEntity = testEntity;
    }

    public TestCase(TestCaseVO testCaseVO) {
        this.name = testCaseVO.getName();
        this.caseId = testCaseVO.getCaseId();
        this.file = testCaseVO.getFile();
    }

    public TestCaseVO toTestCaseVO(){
        return new TestCaseVO(name,caseId,file,info);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public TestEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        this.testEntity = testEntity;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
