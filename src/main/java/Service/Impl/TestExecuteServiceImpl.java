package Service.Impl;

import Entity.Report;
import Entity.TestEntity;
import Repository.TestRepository;
import Service.TestExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
@Service
public class TestExecuteServiceImpl implements TestExecuteService{
    @Autowired
    private TestRepository testRepository;

    @Override
    public Report javaTestAll(Long testId) {
        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();
        String disk=src.split(":")[0];

        command="cmd /c start "+disk+": && cd "+src+" && call mvn test ";

        String line = null;
        String out="";
        try {
            Process process = runtime.exec(command);
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                out=out+line + "\n";
            }
            process.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(out);

        Report report=javaReport(src);
        boolean isSuccess=saveReport(report,testId);

        return report;
    }

    @Override
    public Report javaTest(List<String> file, Long testId) {
        return null;
    }

    @Override
    public Report pythonTest(List<String> file, Long testId) {
        return null;
    }

    @Override
    public Report cTest(List<String> file, Long testId) {
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

    private boolean saveReport(Report report, Long testId){
        TestEntity testEntity=testRepository.findById(testId);
        testEntity.addReports(report);
        testRepository.saveAndFlush(testEntity);
        return true;
    }


}
