package com.Service.Impl;

import com.DataVO.ReportVO;
import com.Entity.FaultInfo;
import com.Entity.Report;
import com.Entity.TestEntity;
import com.Feignclient.FileService;
import com.Repository.TestRepository;
import com.Service.TestExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */
@Service
public class TestExecuteServiceImpl implements TestExecuteService{
    @Autowired
    private TestRepository testRepository;


    @Override
    public ReportVO javaTestAll(Long testId ,String username) {
        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();

        String path=""+src;
        String shPath=src+"/exectest.sh";
        File shfile = new File(shPath);
        if(!shfile.exists()){
            shfile.getParentFile().mkdirs();
        }
        try {
            shfile.createNewFile();
            FileWriter fw = new FileWriter(shfile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(ScriptServiceImpl.javashAll());
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        command="cd "+path+" && sh ./exectest.sh ";
        String[] commands=new String[]{"/bin/sh","-c",command};
        String line = null;
        String out="";
        try {
            Process process = runtime.exec(commands);
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
        boolean isSuccess=saveReport(report,testId,username);

        return report.toReportVO();
    }

    @Override
    public ReportVO javaTest(List<String> file, Long testId ,String username) {
        if(file.isEmpty()){
            return null;
        }

        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();

        String path=""+src;
        String shPath=src+"/exectest.sh";
        File shfile = new File(shPath);
        if(!shfile.exists()){
            shfile.getParentFile().mkdirs();
        }
        try {
            shfile.createNewFile();
            FileWriter fw = new FileWriter(shfile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(ScriptServiceImpl.javash(file));
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        command="cd "+path+" && sh ./exectest.sh ";
        String[] commands=new String[]{"/bin/sh","-c",command};
        String line = null;
        String out="";
        try {
            Process process = runtime.exec(commands);
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
        boolean isSuccess=saveReport(report,testId,username);

        return report.toReportVO();
    }

    @Override
    public ReportVO pythonTest(List<String> file, Long testId ,String username) {
        if(file.isEmpty()){
            return null;
        }

        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();

        String path=""+src;
        String shPath=src+"/exectest.sh";
        File shfile = new File(shPath);
        if(!shfile.exists()){
            shfile.getParentFile().mkdirs();
        }
        try {
            shfile.createNewFile();
            FileWriter fw = new FileWriter(shfile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(ScriptServiceImpl.pythonsh(file));
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        command="cd "+path+" && sh ./exectest.sh ";
        String[] commands=new String[]{"/bin/sh","-c",command};
        String line = null;
        String out="";
        try {
            Process process = runtime.exec(commands);
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

        Report report=pythonReport(src);
        boolean isSuccess=saveReport(report,testId,username);

        return report.toReportVO();
    }

    @Override
    public ReportVO pythonTestAll(Long testId ,String username) {
        String src=testRepository.findById(testId).getSrc();
        File dir=new File(src);
        if (!dir.isDirectory()) {
            System.out.println("not a dir");
            Report report=new Report();
            report.setError_info("diretory error");
            return report.toReportVO();
        } else {
            // 内部匿名类，用来过滤文件类型
            File[] pyList = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().endsWith(".py")&& file.getName().toLowerCase().contains("test")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            List<String> files=new ArrayList<String>();
            for(int i=0;i<pyList.length;i++){
                files.add(pyList[i].getName());
            }
            return pythonTest(files,testId,username);

        }


    }

    @Override
    public ReportVO cTest(List<String> file, Long testId ,String username) {
        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();

        String path=src+="/makefile";
        File makefile = new File(path);
        if(!makefile.exists()){
            makefile.getParentFile().mkdirs();
        }
        try {
            makefile.createNewFile();
            FileWriter fw = new FileWriter(makefile, false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(ScriptServiceImpl.cmakefile(file));
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String shPath=src+="/exectest.sh";
        File shfile = new File(shPath);
        if(!shfile.exists()){
            shfile.getParentFile().mkdirs();
        }
        try {
            shfile.createNewFile();
            FileWriter fw = new FileWriter(shfile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(ScriptServiceImpl.csh(src));
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        command="cd "+path+" && sh ./exectest.sh ";
        String[] commands=new String[]{"/bin/sh","-c",command};
        String line = null;
        String out="";
        try {
            Process process = runtime.exec(commands);
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

        Report report=cReport(src);
        boolean isSuccess=saveReport(report,testId,username);

        return report.toReportVO();
    }

    private Report javaReport(String src){
        src+="/target/surefire-reports";
        File dir=new File(src);

        if (!dir.isDirectory()) {
            System.out.println("not a dir");
            Report report=new Report();
            report.setError_info("diretory error");
            return report;
        } else {
            // 内部匿名类，用来过滤文件类型
            File[] xmlList = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().endsWith(".xml")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            return ReportGenerateImpl.javaXmlReport(xmlList);

        }


    }

    @Override
    public ReportVO cTestAll(Long testId ,String username) {
        String src=testRepository.findById(testId).getSrc();
        File dir=new File(src);
        if (!dir.isDirectory()) {
            System.out.println("not a dir");
            Report report=new Report();
            report.setError_info("diretory error");
            return report.toReportVO();
        } else {
            // 内部匿名类，用来过滤文件类型
            File[] pyList = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().endsWith(".c")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            List<String> files=new ArrayList<String>();
            for(int i=0;i<pyList.length;i++){
                files.add(pyList[i].getName());
            }
            return cTest(files,testId,username);

        }


    }

    private Report pythonReport(String src){
        src+="/log.xml";
        File log=new File(src);
        return ReportGenerateImpl.pythonXmlReport(log);
    }

    private Report cReport(String src){
        Report report=new Report();
        File log=null;
        File dir=new File(src);
        if (!dir.isDirectory()) {
            System.out.println("not a dir");

            report.setError_info("diretory error");
        } else {
            // 内部匿名类，用来过滤文件类型
            File[] xmlList = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isFile() && file.getName().endsWith("-Results.xml")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            log=xmlList[0];
            report=ReportGenerateImpl.cXmlReport(log);
        }
        if(log==null){
            report.setError_info("no file");
        }

        return report;
    }

    private boolean saveReport(Report report, Long testId ,String username){
        TestEntity testEntity=testRepository.findById(testId);
        testEntity.setLatest_time(report.getTime());
        testEntity.setPerform_times(testEntity.getPerform_times()+1);
        testEntity.setLatest_person(username);
        testEntity.addReports(report);
        testRepository.saveAndFlush(testEntity);
        return true;
    }

    private boolean clearFile(){ //清空报告文件

        return false;
    }


    private static List<String> getAllFiles(String postfix,String path){
        List<String> fileList=new ArrayList<String>();
        File dir=new File(path);
        File[] allList = dir.listFiles();
        for(File f:allList){
            if(f.isDirectory()){
                List<String> tempList=getAllFiles(postfix,f.getPath());
                for(String s:tempList){
                    fileList.add(s);
                }
            }

            if(f.isFile() && f.getName().endsWith("."+postfix)){
                fileList.add(f.getPath());
            }

        }
        return fileList;
    }

}
