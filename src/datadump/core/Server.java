package datadump.core;

import ch.ethz.ssh2.Connection;
import datadump.thread.Lock;
import datadump.thread.MonitorThread;
import datadump.consumer.*;
import datadump.thread.DataDump;
import datadump.thread.Producer;
import datadump.util.TaskListXml;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class);
    private ExecutorService executor = Executors.newFixedThreadPool(Settings.getThreadNum());

    private Server() {
    }
    private static Server server = new Server();
    public static Server getInstance(){
        return server;
    }


    public void start() {
        try {
            //启动消费者
            consumer();

            Connection conn = connect();
            String xmlPath = Settings.getXmlPath();
            checkXml(xmlPath,Settings.getXsd_dir());
            Lock lock = new Lock();//锁对象，标记类

            //开辟一个新线程，用来监测系统中的制定进程
            MonitorThread monitorThread = new MonitorThread();
            monitorThread.init(Settings.getSoftName(),lock,conn);
            executor.submit(monitorThread);

            //Scanner scan = new Scanner(System.in);
            //System.out.println("请输入XML文件路径：");
            //String xmlPath = scan.nextLine();
            //String xmlPath = args[0];

            //建立数据转存对象d
            DataDump dataDump = new DataDump();
            TaskListXml taskListXml = readXml(xmlPath);//XML任务单解析
            dataDump.init(lock,taskListXml,executor);

            Producer producer = new Producer();
            long Start = System.currentTimeMillis();//开始转存的时间
            producer.initial(taskListXml,Start);
            producer.sendStart();//MQ Start消息上报
            executor.submit(producer);//MQ消息上报线程开启

            executor.submit(dataDump);//转存线程开启

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consumer() {
        //MQ消息中Start消息接收;
        Consumer1 c1 = new Consumer1();
        c1.receiveMsg();

        //MQ消息中的Running消息接收
        Consumer2 c2 = new Consumer2();
        c2.receiveMsg();

        //MQ消息中的Output消息接收
        Consumer3 c3 = new Consumer3();
        c3.receiveMsg();

        //MQ消息中的Error消息接收
        Consumer4 c4 = new Consumer4();
        c4.receiveMsg();

        //MQ消息中的Complete消息接收
        Consumer5 c5 = new Consumer5();
        c5.receiveMsg();
    }

    /**
     * 创建connection，连接Linux
     * @return
     */
    private Connection connect() {
        Connection conn = null;
        try {
            conn = new Connection(Settings.getHostname(), Settings.getPort());
            conn.connect();
            boolean flag  = conn.authenticateWithPassword(Settings.getUsername(), Settings.getPassword());
            if(flag== false){
                throw new IOException("Authentication failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //xsd校验
    private void checkXml(String xmlPath,String xsdPath)  {
        try {
            XMLErrorHandler errorHandler = new XMLErrorHandler();//创建默认的XML错误消息处理类
            SAXParserFactory factory = SAXParserFactory.newInstance();//获得基于 SAX 的解析器的工厂类
            factory.setValidating(true);//解析器在解析时验证XML内容
            factory.setNamespaceAware(true);//指定由此代码生成的解析器将提供对 XML 名称空间的支持。
            SAXParser parser = factory.newSAXParser();//使用当前配置的工厂参数创建 SAXParser 的一个新实例。
            SAXReader xmlReader = new SAXReader();//创建一个读取工具
            Document xmlDocument = xmlReader.read(new File(xmlPath));//读取XML文件

            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", "file:" + xsdPath);
            SAXValidator validator = new SAXValidator(parser.getXMLReader());

            validator.setErrorHandler(errorHandler);// 发生错误时得到相关信息
            validator.validate(xmlDocument);// 进行校验

            //错误输出方式
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            //通过是否有错误信息判断校验是否匹配
            if (errorHandler.getErrors().hasContent()) {
                logger.info("XML文件通过XSD文件校验失败！");
                writer.write(errorHandler.getErrors());
            } else {
                logger.info("XML文件通过XSD文件校验成功！");
            }
        } catch (Exception ex) {
            logger.info("XML文件: " + xmlPath + " 通过XSD文件:" + xsdPath + "检验失败原因： " + ex.getMessage());
            ex.printStackTrace();
            return;
        }
    }

    //解析XML
    private TaskListXml readXml(String xmlPath) throws JAXBException {
        Unmarshaller u1;
        Marshaller ms;
        JAXBContext jc = JAXBContext.newInstance(TaskListXml.class);
        u1 = jc.createUnmarshaller();
        ms = jc.createMarshaller();
        ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        TaskListXml taskListXml = u1.unmarshal(new StreamSource(xmlPath), TaskListXml.class).getValue();
        StringWriter writer = new StringWriter();
        ms.marshal(taskListXml,writer);
        return taskListXml;
    }
}
