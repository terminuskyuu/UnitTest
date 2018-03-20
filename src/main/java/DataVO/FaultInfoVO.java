package DataVO;

/**
 * Created by Administrator on 2018/3/16.
 */
public class FaultInfoVO {
    private String case_name;

    private String func_name;

    private int line;

    private String type;

    public FaultInfoVO() {
    }

    public FaultInfoVO(String case_name, String func_name, int line, String type) {
        this.case_name = case_name;
        this.func_name = func_name;
        this.line = line;
        this.type = type;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public String getFunc_name() {
        return func_name;
    }

    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
