package datadump.thread;

import datadump.util.*;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class DataDump implements Runnable{

    private static final Logger logger = Logger.getLogger(DataDump.class);
    private ArrayList<String> srcFileLists = new ArrayList<>();
    private ArrayList<String> files = new ArrayList<>();
    private ArrayList<String> metas = new ArrayList<>();
    private TaskListXml taskListXml;
    private ExecutorService executor;
    public Lock lock ;

    public void init(Lock lock, TaskListXml taskListXml, ExecutorService executor) {
        this.taskListXml = taskListXml;
        this.executor = executor;
        this.lock = lock;
    }
    public void run() {
        //检查任务要求的开始时间，结束时间
        long currentTime = System.currentTimeMillis();
        long endTime = DateUtil.parseDate(taskListXml.getEnd_time(),"yyyy-MM-dd HH:mm:ss").getTime();
        long startTime = DateUtil.parseDate(taskListXml.getStart_time(),"yyyy-MM-dd HH:mm:ss").getTime();
        if((endTime >= currentTime)) {
            //循环获取当前时间并判断
            while (true) {
                currentTime = System.currentTimeMillis(); //当前时间
                //如果当前时间晚于开始时间，立即开始执行
                if (currentTime >= startTime) {
                    //XML任务单中的data_status为SUCCESS时，才进行转存
                    if ("SUCCESS".equals(taskListXml.getData_status())) {
                        copy();
                        creatOdl("SUCCESS");//done文件的生成
                    } else if("FAIL".equals(taskListXml.getData_status())){
                        logger.info("参数data_status值为FAIL，不转存!");
                        creatOdl("FAIL");
                    }
                    break;
                }

                //如果当前时间早于开始时间，则等待开始时间后执行任务
                else {
                  sleepAwhile();
                    logger.info("未到开始时间，请等待!");
                }
            }
        }
        //若结束时间已过，不转存，退出
        else{
            logger.info("超出任务结束时间，不转存!");
        }
    }

    private void copy() {
        long srcFile = new StatisticsFileSize().getTotalSizeOfFilesInDir(new File(taskListXml.getSrc_path()));
        if(StringUtil.isNull(taskListXml.getSrc_path()) || srcFile==0){
            return;
        } else{
            getSrcFileLists();
            dataDump(taskListXml.getData_type());//数据转存
        }
    }

    private void dataDump(String dataType) {
            if(lock.isRun()){
                logger.info("监控进程正在执行");
               sleepAwhile();
            } else{
                while (srcFileLists.size()>0){
                    String name = srcFileLists.remove(0);
                    if (name.endsWith(".dat")) {
                        logger.info("监控进程未执行，正在转存.dat");
                        copyFile(name);
                        files.add(name);
                    }
                    //.xml文件的转存
                    else if (name.endsWith(".xml")) {
                        logger.info("监控进程未执行，正在转存.xml");
                        xmlCopy(name, dataType);
                        metas.add(name);
                    }
                }
            }

    }

//    //.dat文件的转存
//    private void get_dat(String fileName){
//        try {
//            FileInputStream fis = new FileInputStream(fileName);
//            String dstPath = fileName.replace(taskListXml.getSrc_path(),taskListXml.getDst_path());
//            FileOutputStream fos = new FileOutputStream(dstPath);
//
//            byte[] bys = new byte[1024*1024];
//            int len = 0;
//
//            while((len = fis.read(bys)) != -1) {
//                    while (lock.isRun()){
//                        sleepAwhile();
//                    }
//                //logger.info("拷贝字节"+len);
//                fos.write(bys,0,len);
//            }
//            fos.close();
//            fis.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void sleepAwhile() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     * @return
     */
    private void copyFile(String filName) {
         File fileSrc = new File(filName);
         String destPath = filName.replace(taskListXml.getSrc_path(),taskListXml.getDst_path());
         File destFile =  new File(destPath);
         ConcurrentLinkedQueue<byte[]> linkedQueue = new ConcurrentLinkedQueue();
         ByteBuffer byteBuffer = ByteBuffer.allocate(4194304);
         Set<Boolean> set = new  HashSet<>();
            executor.submit(new Thread(){
                public void run(){
                    FileChannel fis = null;
                    long totalsize = 0L;
                    try {
                        fis = new FileInputStream(fileSrc).getChannel();
                        int c;
                        //logger.info(fileSrc.getName()+"传输开始时间："+ TimeUtils.now());
                        while((c = fis.read(byteBuffer))!=-1){
                            while (lock.isRun()){
                                sleepAwhile();
                            }
                            byteBuffer.flip();//小车准备就绪
                            byte[] bytes = new byte[byteBuffer.limit()];
                            byteBuffer.get(bytes);
                            totalsize+=c;
                            while(linkedQueue.size()>20){
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            linkedQueue.add(bytes);//卸货
                            byteBuffer.clear();
                        }
                        set.add(false);

                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        try {
                            if(fis!=null){
                                fis.close();
                            }
                            //logger.info(fileSrc.getName()+"共发送"+totalsize+"字节");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

            executor.submit(new Thread(){
                public void run(){
                    FileOutputStream fos = null;
                    long bytesRead = 0L;
                    long totalSize = 0L;
                    try {
                        long intervalTime = System.currentTimeMillis();
                        fos = new FileOutputStream(destPath);
                        while (true){
                            while (lock.isRun()){
                                sleepAwhile();//自旋锁
                            }
                            if(!linkedQueue.isEmpty()){
                                byte[] bytes = linkedQueue.poll();
                                bytesRead += bytes.length;
                                totalSize += bytes.length;
                                fos.write(bytes);
                                if ((System.currentTimeMillis() - intervalTime) / 1000L > 2) {
                                    double time = (System.currentTimeMillis() - intervalTime) / 1000.0D;
                                    double speed = bytesRead / 1048576.0D / time;
                                    intervalTime = System.currentTimeMillis();
                                    bytesRead = 0;
                                    //logger.info(destFile.getName()+String.format("吞吐速率%.2f兆/秒", new Object[]{Double.valueOf(speed)}));
                                }
                            }
                            if(set.size()>0&&linkedQueue.isEmpty()){
                                set.clear();
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if(fos!=null){
                                fos.close();
                            }
                            String name = destFile.getName();
                            //logger.info(name+"传输结束时间："+TimeUtils.now());
                            //logger.info(name+"共接收"+totalSize+"字节，约"+String.format("%.2fG", new Object[]{Double.valueOf(totalSize/1073741824.0D)}));
                            //creatOdl("SUCCESS");//done文件的生成
                            if(taskListXml.getDelete_flag() == 1) {
                                deleteFile(filName);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }

    private void deleteFile(String fileName){
        File file = new File(fileName);
        boolean flag = file.delete();
        while (!flag){
            flag = file.delete();
        }
    }


    private void xmlCopy(String fileName, String xmlType) {
        try {
            StringWriter writer = getStringWriter(fileName, xmlType);
            String str = writer.toString();
            //写出到xml文件中
            String dstpath = fileName.replace(taskListXml.getSrc_path(),taskListXml.getDst_path());
            BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(dstpath)));
            bfw.write(str);
            bfw.close();

            if(taskListXml.getDelete_flag() == 1) {
                deleteFile(fileName);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private StringWriter getStringWriter(String fileName, String xmlType) {
        JAXBContext jc = null;
        Unmarshaller u1 = null;
        Marshaller ms = null;
        StringWriter writer = null;
        try {
            switch (xmlType){
                case "RAW":
                    jc =  JAXBContext.newInstance(RawXml.class);
                    u1 = jc.createUnmarshaller();
                    RawXml raw = u1.unmarshal(new StreamSource(fileName), RawXml.class).getValue();
                    raw.setRAWdataQualityEvalutionValue(taskListXml.getRaw_data_quality_value());
                    ms = jc.createMarshaller();
                    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                    writer = new StringWriter();
                    ms.marshal(raw,writer);
                    break;
                case "MWD":
                    jc =  JAXBContext.newInstance(MwdXml.class);
                    u1 = jc.createUnmarshaller();
                    MwdXml mwd = u1.unmarshal(new StreamSource(fileName), MwdXml.class).getValue();
                    ms = jc.createMarshaller();
                    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                    writer = new StringWriter();
                    ms.marshal(mwd,writer);
                    break;
                case "JYS":
                    jc =  JAXBContext.newInstance(JysXml.class);
                    u1 = jc.createUnmarshaller();
                    JysXml jys = u1.unmarshal(new StreamSource(fileName), JysXml.class).getValue();
                    ms = jc.createMarshaller();
                    ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                    writer = new StringWriter();
                    ms.marshal(jys,writer);
                    break;
                default:
                    logger.error("无此xml类型");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return writer;
    }

    private void getSrcFileLists(){
        File srcFolder = new File(taskListXml.getSrc_path());
        File[] fileArray = srcFolder.listFiles();

        //本地路径下有文件，正常转存
        for(File file : fileArray) {
            String srcpath = taskListXml.getSrc_path() + "/" + file.getName();
            srcFileLists.add(srcpath);
        }
    }

    //done文件的生成
    private void creatOdl(String status) {
        try {
            String donepath = taskListXml.getDone_notify_path() + "/" + taskListXml.getData_type() + "_" + taskListXml.getSatellite_id() + "_" + taskListXml.getOrbit_number() + "_" + taskListXml.getStation_for_file() + "_" + taskListXml.getJob_task_id() + "_" + taskListXml.getTotal_done_file_num() + "_" + taskListXml.getDone_file_index() + ".done";
            FileOutputStream done = null;
            done = new FileOutputStream(donepath);

            //XML中data_status参数为SUCCESS时，done文件的生成
            if("SUCCESS".equals(status)){
                done.write("file_list=".getBytes());
                for (int i = 0; i < files.size(); i++)
                {
                    String name = files.get(i);
                    done.write(name.getBytes());//成功转存的.dat文件
                    done.write(";".getBytes());
                }
                done.write(("\r\n").getBytes());
                done.write("meta_list=".getBytes());
                for (int i = 0; i < metas.size(); i++)
                {
                    String name =  metas.get(i);
                    done.write(name.getBytes());//成功转存的.xml文件
                    done.write(";".getBytes());
                }
                done.close();
            }
            //XML中data_status参数为FAIL时，done文件的生成
            else if("FAIL".equals(status)){
                done.write("任务失败".getBytes());
                done.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
