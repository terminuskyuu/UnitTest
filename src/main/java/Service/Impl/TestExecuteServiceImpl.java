package Service.Impl;

import Entity.FaultInfo;
import Entity.Report;
import Entity.TestEntity;
import Repository.TestRepository;
import Service.TestExecuteService;
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
    public Report javaTestAll(Long testId) {
        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();
        String disk=src.split(":")[0];

        command="cmd /c  "+disk+": && cd "+src+" && call mvn test ";

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
        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();
        String disk=src.split(":")[0];

        command="cmd /c "+disk+": && cd "+src+" && call mvn -Dtest=";
        for(String s:file){
            command=command+s+",";
        }
        command=command.substring(0,command.lastIndexOf(','));
        command+=" test";

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
    public Report pythonTest(List<String> file, Long testId) {
        Runtime runtime = Runtime.getRuntime();
        String command="";
        String src=testRepository.findById(testId).getSrc();
        String disk=src.split(":")[0];

        command="cmd /c "+disk+": && cd "+src+" &&  py.test ";
        for(String s:file){
            command=command+s+" ";
        }
        command+=" --junitxml="+src+"\\report\\log.xml";

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

        Report report=pythonReport(src);
        boolean isSuccess=saveReport(report,testId);

        return report;
    }

    @Override
    public Report cTest(List<String> file, Long testId) {
        return null;
    }

    private Report javaReport(String src){
        src+="\\target\\surefire-reports";
        File dir=new File(src);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Report report=new Report();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        report.setTime(sdf.format(d));

        int cases=0;
        int failures=0;
        int success=0;
        int error=0;

        if (!dir.isDirectory()) {
            System.out.println("not a dir");

            report.setError_info("diretory error");
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
            for (File f:xmlList) {
                DocumentBuilder builder = null;
                try {
                    builder = factory.newDocumentBuilder();
                    Document document = builder.parse(f);
                    Element rootElement = document.getDocumentElement();
                    cases+=Integer.parseInt(rootElement.getAttribute("tests"));
                    failures+=Integer.parseInt(rootElement.getAttribute("failures"));
                    int failnum=Integer.parseInt(rootElement.getAttribute("failures"));
                    String name=rootElement.getAttribute("name");
                    error=Integer.parseInt(rootElement.getAttribute("errors"));

                    if(failnum>0){
                        NodeList failList = rootElement.getElementsByTagName("failure");
                        for(int i=0;i<failnum;i++){
                            Element fail = (Element)failList.item(i);
                            FaultInfo faultInfo=new FaultInfo();
                            faultInfo.setCase_name(name);
                            String funcname=((Element)fail.getParentNode()).getAttribute("name");
                            faultInfo.setFunc_name(funcname);
                            String type=fail.getAttribute("type");
                            if(fail.getAttribute("message")!=null){
                                type=type+"  "+fail.getAttribute("message");
                            }
                            faultInfo.setType(type);
                            String value=fail.getFirstChild().getNodeValue();
                            int line=0;
                            int start=value.indexOf(funcname);
                            int end=value.indexOf(")",start);
                            value=value.substring(start,end);
                            value=value.split(":")[1];
                            line=Integer.parseInt(value);
                            faultInfo.setLine(line);
                            report.addFault_info(faultInfo);

                            System.out.println(name+" "+funcname+" "+line+" "+type);

                        }


                    }

                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            success=cases-failures;
            report.setCase_num(cases);
            report.setFail_num(failures);
            report.setSucess_num(success);
            report.setError_info("");
            if(cases==0){
                report.setError_info("result do not exist");
            }

        }

        return report;
    }

    private Report pythonReport(String src){
        src+="\\report\\log.xml";
        File log=new File(src);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Report report=new Report();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        report.setTime(sdf.format(d));

        int cases=0;
        int failures=0;
        int success=0;
        int error=0;

        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(log);
            Element rootElement = document.getDocumentElement();
            cases=Integer.parseInt(rootElement.getAttribute("tests"));
            failures=Integer.parseInt(rootElement.getAttribute("failures"));
            int failnum=Integer.parseInt(rootElement.getAttribute("failures"));
            error=Integer.parseInt(rootElement.getAttribute("errors"));

            if(failnum>0){
                NodeList failList = rootElement.getElementsByTagName("testcase");
                for(int i=0;i<failList.getLength();i++){
                    Element fail = (Element)failList.item(i);
                    FaultInfo faultInfo=new FaultInfo();
                    String name=fail.getAttribute("file");
                    faultInfo.setCase_name(name);
                    String funcname=fail.getAttribute("name");
                    faultInfo.setFunc_name(funcname);
                    int line=Integer.parseInt(fail.getAttribute("line"));
                    faultInfo.setLine(line);
                    Element failure=(Element) fail.getFirstChild();
                    if(failure!=null){
                        String type=failure.getAttribute("message");
                        faultInfo.setType(type);
                        report.addFault_info(faultInfo);

                        System.out.println(name+" "+funcname+" "+line+" "+type);
                    }

                }

            }
            if(error>0){
                NodeList errorList = rootElement.getElementsByTagName("error");
                Element errorTemp=(Element) errorList.item(0);
                String errorInfo=errorTemp.getAttribute("message");
                errorInfo+="\n"+errorTemp.getFirstChild().getNodeValue();
                report.setError_info(errorInfo);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    success=cases-failures;
    report.setCase_num(cases);
    report.setFail_num(failures);
    report.setSucess_num(success);
    report.setError_info("");
    if(cases==0) {
        report.setError_info("result do not exist");
    }

        return report;
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
