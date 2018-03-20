package DataVO;

/**
 * Created by Administrator on 2018/3/16.
 */
public class TestCaseVO {
    private String name;

    private String caseId;

    private String file;

    private String info;

    public TestCaseVO() {
    }

    public TestCaseVO(String name, String caseId, String file, String info) {
        this.name = name;
        this.caseId = caseId;
        this.file = file;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
