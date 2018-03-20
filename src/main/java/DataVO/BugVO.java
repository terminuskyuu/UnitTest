package DataVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */
public class BugVO {
    private String project_id;

    private String name;

    private String importance;

    private String info;

    private String state;

    private List<BugChangeVO> bug_change;

    public BugVO() {
    }

    public BugVO(String project_id, String name, String importance, String info, String state, List<BugChangeVO> bug_change) {
        this.project_id = project_id;
        this.name = name;
        this.importance = importance;
        this.info = info;
        this.state = state;
        this.bug_change = bug_change;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<BugChangeVO> getBug_change() {
        return bug_change;
    }

    public void setBug_change(List<BugChangeVO> bug_change) {
        this.bug_change = bug_change;
    }
}
