package datadump.thread;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import datadump.util.DateUtil;
import datadump.util.StringUtil;
import datadump.util.TaskListXml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class  Producer implements Runnable{
    private TaskListXml taskListXml;
    private long srcFileSize;
    private long Start;
    private String fileNames;
    private String fileSizes;

    private ArrayList<String> lists = new ArrayList<>();
    private ArrayList<Long> sizes = new ArrayList<>();
    private ArrayList<Long> time = new ArrayList<>();
    private ArrayList<Long> files = new ArrayList<>();

    public void initial(TaskListXml taskListXml,long Start){
        this.taskListXml = taskListXml;
        this.Start = Start;
        this.srcFileSize = StatisticsFileSize.getTotalSizeOfFilesInDir(new File(taskListXml.getSrc_path()));
        this.fileNames = fileNames();
        this.fileSizes = fileSizes();
    }

    public void run(){
        try {

            //3.通过工厂对象获取与MQ的连接
            Connection connection = conect();
            Channel channel = connection.createChannel();
            //4.定义主题交换机
            channel.exchangeDeclare("XCN_ORDER", BuiltinExchangeType.TOPIC);
            //5.定义队列（参数1：定义的队列名称，参数2：队列中的数据是否持久化，参数3：是否排外（当前队列是否为当前连接私有），
            // 参数4：自动删除（当此队列的连接数为0时，此队列会销毁（无论队列中是否还有数据）），参数5：设置当前队列的参数）
            channel.queueDeclare("queue2", true, false, false, null);
            channel.queueDeclare("queue3", true, false, false, null);
            channel.queueDeclare("queue4", true, false, false, null);
            channel.queueDeclare("queue5", true, false, false, null);
            //6.交换机绑定队列（参数1：队列名称，参数2：目标交换机，参数3：队列的key）
            channel.queueBind("queue2", "XCN_ORDER", taskListXml.getSub_order_id()+".RUNNING");
            channel.queueBind("queue3", "XCN_ORDER", taskListXml.getSub_order_id()+".OUTPUT");
            channel.queueBind("queue4", "XCN_ORDER", taskListXml.getSub_order_id()+".STATUS");
            channel.queueBind("queue5", "XCN_ORDER", taskListXml.getSub_order_id()+".STATUS");

            long dstFileSize;
            //原路径不存在时,通过MQ上报消息并退出
            if(StringUtil.isNull(taskListXml.getSrc_path())){
                String msg = "Error:原路径不存在";
                channel.basicPublish("XCN_ORDER", taskListXml.getSub_order_id()+".STATUS", null, msg.getBytes());
                return;
            }
            //原路径无文件时,通过MQ上报消息并退出
            else if(StringUtil.isNotNull(taskListXml.getSrc_path()) && srcFileSize == 0){
                String msg = "Error:原路径存在但无文件";
                channel.basicPublish("XCN_ORDER", taskListXml.getSub_order_id()+".STATUS", null, msg.getBytes());
            }

            //正常情况下Running和Output消息的上报
            else if(StringUtil.isNotNull(taskListXml.getSrc_path()) && (srcFileSize != 0)){
                do{
                    dstFileSize = StatisticsFileSize.getTotalSizeOfFilesInDir(new File(taskListXml.getDst_path()));
                    if(dstFileSize != 0){
                        //存储时间
                        long currentTime = System.currentTimeMillis();
                        time.add(currentTime);
                        long start = time.get(0);
                        //现在时刻转格式
                        String dateStr1 = DateUtil.getCurrentDateLong(currentTime,"yyyy-MM-dd HH:mm:ss");
                        //目标路径存储文件大小
                        long fileSize = StatisticsFileSize.getTotalSizeOfFilesInDir(new File(taskListXml.getDst_path()));
                        files.add(fileSize);
                        String msg1 = "";
                        String msg2 = "";
                        if(files.size() >= 2){
                            long f1 = files.get((files.size()-2));
                            msg1 = "Running@"+dateStr1+"@"+taskListXml.getOrder_id()+"@"+taskListXml.getSub_order_id()+"@"+taskListXml.getModule_id()+"@"+taskListXml.getMq_server_host()+"@"+"InstTransBitRate="+(((fileSize - f1) / (1024*1024)))*8/(taskListXml.getMq_running_freq_in_ms()/1000)+"(Mbps);"+"AvgTransBitRate="+ ((fileSize/(1024*1024)) /((currentTime-start)/1000))*8+"(Mbps);"+"TotalTransBytes="+fileSize+"(Bytes);Percent="+(fileSize*100/srcFileSize)+"%";
                            msg2 = "Output@"+dateStr1+"@"+taskListXml.getOrder_id()+"@"+taskListXml.getSub_order_id()+"@"+taskListXml.getModule_id()+"@"+taskListXml.getMq_server_host()+"@"+"InstTransBitRate="+(((fileSize - f1) / (1024*1024)))*8/(taskListXml.getMq_running_freq_in_ms()/1000)+"(Mbps);"+"AvgTransBitRate="+ ((fileSize/(1024*1024)) /((currentTime-start)/1000))*8+"(Mbps);"+"TotalTransBytes="+fileSize+"(Bytes);Percent="+(fileSize*100/srcFileSize)+"%";
                        }
                        channel.basicPublish("XCN_ORDER", taskListXml.getSub_order_id()+".RUNNING", null, msg1.getBytes());
                        channel.basicPublish("XCN_ORDER", taskListXml.getSub_order_id()+".OUTPUT", null, msg2.getBytes());

                        //休息mq_running_freq_in_ms指定的时间间隔
                        Thread.sleep(taskListXml.getMq_running_freq_in_ms());
                    }
                }while(dstFileSize < this.srcFileSize);

                //Complete消息的上报
                long endTime = System.currentTimeMillis();
                long f =StatisticsFileSize.getTotalSizeOfFilesInDir(new File(taskListXml.getDst_path()));

                String dateStr2 = DateUtil.getCurrentDateLong(endTime,"yyyy-MM-dd HH:mm:ss");

                String msg = "Complete@"+dateStr2+"@"+taskListXml.getOrder_id()+"@"+taskListXml.getSub_order_id()+"@"+taskListXml.getModule_id()+"@"+taskListXml.getMq_server_host()+"@"+"InstTransBitRate=0(Mbps);AvgTransBitRate="+(f*1000/(1024*1024)/(endTime-Start))*8+"(Mbps);"+fileNames+";"+fileSizes+";TaskStatusMsg=SUCCESS";
                channel.basicPublish("XCN_ORDER", taskListXml.getSub_order_id()+".STATUS", null, msg.getBytes());

            }
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection conect() {
        Connection connection = null;
        try {
            //1.创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //2.在工厂对象中设置MQ的连接信息（IP,port端口,virtualhost虚拟主机,username用户名,password密码）
            factory.setHost(taskListXml.getMq_server_host());
            factory.setPort(taskListXml.getMq_server_port());
            factory.setUsername(taskListXml.getMq_server_user());
            factory.setPassword(taskListXml.getMq_server_pwd());
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;

    }

    private String fileNames(){
        String fileName = null;
        if(StringUtil.isNull(taskListXml.getSrc_path()) || srcFileSize==0){
            return "";
        }else {
            try {
                //新建文件名列表
                File srcFolder = new File(taskListXml.getSrc_path());
                File[] fileArray = srcFolder.listFiles();
                //获得并存储文件名
                for(File file : fileArray) {
                    lists.add(file.getName());
                }
                // 将文件名转为字符串
                fileName = "FileName="+lists.get(0);
                for(int i = 1;i<lists.size();i++){
                    fileName += ","+lists.get(i);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return fileName;
        }
    }

    private String fileSizes(){
        String fileSizes = null;
        if(StringUtil.isNull(taskListXml.getSrc_path()) || srcFileSize==0){
            return "";
        }else {
            try {
                //新建文件名列表
                File srcFolder = new File(taskListXml.getSrc_path());
                File[] fileArray = srcFolder.listFiles();
                //获得并存储文件大小
                for (File file : fileArray) {
                    FileInputStream fis = new FileInputStream(file);
                    long size = fis.available();
                    sizes.add(size);
                }
                //将文件大小转为字符串
                fileSizes = "TolSize=" + sizes.get(0) + "(Bytes)";
                for (int i = 1; i < sizes.size(); i++) {
                    fileSizes += "," + sizes.get(i) + "(Bytes)";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileSizes;
    }

    //Start消息的上报
    public void sendStart() {
        try {
            Connection connection = conect();
            Channel channel = connection.createChannel();

            //定义主题交换机
            channel.exchangeDeclare("XCN_ORDER", BuiltinExchangeType.TOPIC);
            //定义队列
            channel.queueDeclare("queue1", true, false, false, null);
            //绑定队列
            channel.queueBind("queue1", "XCN_ORDER", taskListXml.getSub_order_id()+".STATUS");

            long dstFile = StatisticsFileSize.getTotalSizeOfFilesInDir(new File(taskListXml.getDst_path()));
            if(dstFile == 0){
                String msg = "Start";
                channel.basicPublish("XCN_ORDER", taskListXml.getSub_order_id()+".STATUS", null, msg.getBytes());
            }
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}







