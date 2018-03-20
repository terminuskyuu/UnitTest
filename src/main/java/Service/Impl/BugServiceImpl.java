package Service.Impl;

import DataVO.BugChangeVO;
import DataVO.BugVO;
import Service.BugService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
public class BugServiceImpl implements BugService{
    @Override
    public boolean createBug(BugVO bugVO) {
        return false;
    }

    @Override
    public boolean deleteBug(BugVO bugVO) {
        return false;
    }

    @Override
    public boolean updateBug(BugVO bugVO) {
        return false;
    }

    @Override
    public List<BugVO> getBugByProject(String projectId) {
        return null;
    }

    @Override
    public BugVO getBugById(Long id) {
        return null;
    }

    @Override
    public boolean createBugChange(BugChangeVO bugChangeVO, Long bugId) {
        return false;
    }
}
