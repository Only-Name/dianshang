package datadump.util;

import org.eclipse.persistence.oxm.annotations.XmlPath;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TT_CUT")
@XmlAccessorType(XmlAccessType.FIELD)

public class TaskListXml {
    @XmlPath("/task_base/text()")
    private String task_base;//任务属性信息
    @XmlPath("/execute_info/text()")
    private String execute_info;//exe执行信息
    @XmlPath("/satellite_inf/text()")
    private String satellite_inf;//（卫星信息、卫星属性信息）
    @XmlPath("/input_chain/text()")
    private String input_chain;//输入数据链信息
    @XmlPath("/output_chain/text()")
    private String output_chain;//输出数据信息
    @XmlPath("/process_inf/text()")
    private String process_inf;//处理参数
    @XmlPath("/message_info/text()")
    private String message_info;//上报消息参数
    @XmlPath("/reserved/text()")
    private String reserved;

    @XmlPath("/task_base/station_id/text()")
    private String station_id;//卫星地面站标识
    @XmlPath("/task_base/task_serial_number/text()")
    private String task_serial_number;//任务序列号
    @XmlPath("/task_base/job_task_id/text()")
    private String job_task_id;//任务标识
    @XmlPath("/task_base/order_id/text()")
    private String order_id;//作业标识
    @XmlPath("/task_base/sub_order_id/text()")
    private String sub_order_id;//子作业标识
    @XmlPath("/task_base/user_id/text()")
    private String user_id;//任务来自用户的标识
    @XmlPath("/task_base/priority/text()")
    private String priority;//优先级

    @XmlPath("/execute_info/module_id/text()")
    private String module_id;//Exe标识
    @XmlPath("/execute_info/unit_id/text()")
    private String unit_id;//运行设备单元标识
    @XmlPath("/execute_info/start_time/text()")
    private String start_time;//任务开始时间,服务器本地时间
    @XmlPath("/execute_info/end_time/text()")
    private String end_time;//任务结束时间,服务器本地时间
    @XmlPath("/execute_info/data_type/text()")
    private String data_type;//数据类型

    @XmlPath("/satellite_inf/satellite_id/text()")
    private String satellite_id;//卫星简称
    @XmlPath("/satellite_inf/orbit_number/text()")
    private String orbit_number;//轨道号
    @XmlPath("/satellite_inf/station_for_file/text()")
    private String station_for_file;//接收站

    @XmlPath("/input_chain/src_path/text()")
    private String src_path;//本地数据所在路径，程序会将该路径下所有数据转储
    @XmlPath("/input_chain/raw_data_quality_value/text()")
    private String raw_data_quality_value;//原始数据质量评价值

    @XmlPath("/output_chain/data_status/text()")
    private String data_status;//前任程序生成的待转储数据结果，决定是否归档
    @XmlPath("/output_chain/total_done_file_num/text()")
    private String total_done_file_num;//本次JOB任务中，done文件的总数
    @XmlPath("/output_chain/done_file_index/text()")
    private String done_file_index;//本次JOB任务中，done文件的序号
    @XmlPath("/output_chain/done_notify_path/text()")
    private String done_notify_path;//done文件的存储路径
    @XmlPath("/output_chain/dst_path/text()")
    private String dst_path;//数据上传路径，绝对路径
    @XmlPath("/output_chain/module_log_file/text()")
    private String module_log_file;//完成通知路径
    @XmlPath("/output_chain/work_dir/text()")
    private String work_dir;//临时路径

    @XmlPath("/process_inf/delete_flag/text()")
    private int delete_flag;//转储执行结束后，是否删除本地文件
    @XmlPath("/process_inf/is_wait/text()")
    private String is_wait;//是否监控指定进程
    @XmlPath("/process_inf/exe_list/text()")
    private String exe_list;//监控进程列表

    @XmlPath("/message_info/messg_type/text()")
    private String messg_type;//消息上报类型
    @XmlPath("/message_info/messg_server_IP/text()")
    private String messg_server_IP;//UDP消息Host
    @XmlPath("/message_info/messg_server_port/text()")
    private String messg_server_port;//UDP消息端口
    @XmlPath("/message_info/mq_server_user/text()")
    private String mq_server_user;//MQ服务端用户名
    @XmlPath("/message_info/mq_server_pwd/text()")
    private String mq_server_pwd;//MQ服务端密码
    @XmlPath("/message_info/mq_server_host/text()")
    private String mq_server_host;//MQ服务端IP
    @XmlPath("/message_info/mq_server_port/text()")
    private int mq_server_port;//MQ服务端端口
    @XmlPath("/message_info/mq_running_freq_in_ms/text()")
    private long mq_running_freq_in_ms;//MQ Running消息上报间隔，单位毫秒
    @XmlPath("/message_info/mq_output_freq_in_ms/text()")
    private long mq_output_freq_in_ms;//MQ Output消息上报间隔，单位毫秒
    @XmlPath("/message_info/mq_message_expiry/text()")
    private String mq_message_expiry;//MQ消息超时时间

    @XmlPath("/reserved/mq_queue_manager/text()")
    private String mq_queue_manager;//MQ队列管理
    @XmlPath("/reserved/mq_queue_running/text()")
    private long mq_queue_running;//MQ Running队列
    @XmlPath("/reserved/mq_queue_output/text()")
    private long mq_queue_output;//MQ Output队列
    @XmlPath("/reserved/mq_queue_status/text()")
    private String mq_queue_status;//MQ start/error/complete队列
    @XmlPath("/reserved/config_file_path/text()")
    private String config_file_path;//配置文件路径
    @XmlPath("/reserved/notify_file_path/text()")
    private String notify_file_path;//配置文件路径


    public TaskListXml() {

    }

    public void setStation_id(String station_id) { this.station_id = station_id; }

    public void setSatellite_id(String satellite_id) { this.satellite_id = satellite_id; }

    public void setOrbit_number(String orbit_number) { this.orbit_number = orbit_number; }

    public void setStation_for_file(String station_for_file) { this.station_for_file = station_for_file; }

    public void setTask_serial_number(String task_serial_number) {
        this.task_serial_number = task_serial_number;
    }

    public void setJob_task_id(String job_task_id) {
        this.job_task_id = job_task_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setSub_order_id(String sub_order_id) {
        this.sub_order_id = sub_order_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public void setSrc_path(String src_path) {
        this.src_path = src_path;
    }

    public void setRaw_data_quality_value(String raw_data_quality_value) { this.raw_data_quality_value = raw_data_quality_value; }

    public void setData_status(String data_status) {
        this.data_status = data_status;
    }

    public void setTotal_done_file_num(String total_done_file_num) {
        this.total_done_file_num = total_done_file_num;
    }

    public void setDone_file_index(String done_file_index) {
        this.done_file_index = done_file_index;
    }

    public void setDone_notify_path(String done_notify_path) {
        this.done_notify_path = done_notify_path;
    }

    public void setDst_path(String dst_path) {
        this.dst_path = dst_path;
    }

    public void setModule_log_file(String module_log_file) {
        this.module_log_file = module_log_file;
    }

    public void setWork_dir(String work_dir) {
        this.work_dir = work_dir;
    }

    public void setDelete_flag(int delete_flag) {
        this.delete_flag = delete_flag;
    }

    public void setIs_wait(String is_wait) {
        this.is_wait = is_wait;
    }

    public void setExe_list(String exe_list) {
        this.exe_list = exe_list;
    }

    public void setMessg_type(String messg_type) {
        this.messg_type = messg_type;
    }

    public void setMessg_server_IP(String messg_server_IP) {
        this.messg_server_IP = messg_server_IP;
    }

    public void setMessg_server_port(String messg_server_port) {
        this.messg_server_port = messg_server_port;
    }

    public void setMq_server_user(String mq_server_user) {
        this.mq_server_user = mq_server_user;
    }

    public void setMq_server_pwd(String mq_server_pwd) {
        this.mq_server_pwd = mq_server_pwd;
    }

    public void setMq_server_host(String mq_server_host) {
        this.mq_server_host = mq_server_host;
    }

    public void setMq_server_port(int mq_server_port) {
        this.mq_server_port = mq_server_port;
    }

    public void setMq_running_freq_in_ms(long mq_running_freq_in_ms) { this.mq_running_freq_in_ms = mq_running_freq_in_ms; }

    public void setMq_output_freq_in_ms(long mq_output_freq_in_ms) { this.mq_output_freq_in_ms = mq_output_freq_in_ms; }

    public void setMq_message_expiry(String mq_message_expiry) {
        this.mq_message_expiry = mq_message_expiry;
    }

    public void setMq_queue_manager(String mq_queue_manager) {
        this.mq_queue_manager = mq_queue_manager;
    }

    public void setMq_queue_running(long mq_queue_running) {
        this.mq_queue_running = mq_queue_running;
    }

    public void setMq_queue_output(long mq_queue_output) {
        this.mq_queue_output = mq_queue_output;
    }

    public void setMq_queue_status(String mq_queue_status) {
        this.mq_queue_status = mq_queue_status;
    }

    public void setConfig_file_path(String config_file_path) {
        this.config_file_path = config_file_path;
    }

    public void setNotify_file_path(String notify_file_path) {
        this.notify_file_path = notify_file_path;
    }

    public String getSatellite_id() { return satellite_id; }

    public String getOrbit_number() { return orbit_number; }

    public String getStation_for_file() { return station_for_file; }

    public String getStation_id() {
        return station_id;
    }

    public String getTask_serial_number() {
        return task_serial_number;
    }

    public String getJob_task_id() {
        return job_task_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getSub_order_id() {
        return sub_order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPriority() {
        return priority;
    }

    public String getModule_id() {
        return module_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public String  getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getData_type() {
        return data_type;
    }

    public String getSrc_path() {
        return src_path;
    }

    public String getRaw_data_quality_value() {
        return raw_data_quality_value;
    }

    public String getData_status() {
        return data_status;
    }

    public String getTotal_done_file_num() {
        return total_done_file_num;
    }

    public String getDone_file_index() {
        return done_file_index;
    }

    public String getDone_notify_path() {
        return done_notify_path;
    }

    public String getDst_path() {
        return dst_path;
    }

    public String getModule_log_file() {
        return module_log_file;
    }

    public String getWork_dir() {
        return work_dir;
    }

    public int getDelete_flag() {
        return delete_flag;
    }

    public String getIs_wait() {
        return is_wait;
    }

    public String getExe_list() {
        return exe_list;
    }

    public String getMessg_type() {
        return messg_type;
    }

    public String getMessg_server_IP() {
        return messg_server_IP;
    }

    public String getMessg_server_port() {
        return messg_server_port;
    }

    public String getMq_server_user() {
        return mq_server_user;
    }

    public String getMq_server_pwd() {
        return mq_server_pwd;
    }

    public String getMq_server_host() {
        return mq_server_host;
    }

    public int getMq_server_port() {
        return mq_server_port;
    }

    public long getMq_running_freq_in_ms() { return mq_running_freq_in_ms; }

    public long getMq_output_freq_in_ms() { return mq_output_freq_in_ms; }

    public String getMq_message_expiry() {
        return mq_message_expiry;
    }

    public String getMq_queue_manager() {
        return mq_queue_manager;
    }

    public long getMq_queue_running() {
        return mq_queue_running;
    }

    public long getMq_queue_output() {
        return mq_queue_output;
    }

    public String getMq_queue_status() {
        return mq_queue_status;
    }

    public String getConfig_file_path() {
        return config_file_path;
    }

    public String getNotify_file_path() {
        return notify_file_path;
    }
}
