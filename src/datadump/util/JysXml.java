package datadump.util;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "metaData")
@XmlAccessorType(XmlAccessType.FIELD)

public class JysXml {
    @XmlPath("/fileHeader/text()")
    private String fileHeader;//头文件
    @XmlPath("/basicInfo/text()")
    private String basicInfo;//基础信息
    @XmlPath("/metaInfo/text()")
    private String metaInfo;//元数据信息

    @XmlPath("/fileHeader/source/text()")
    private String source;//文件来源
    @XmlPath("/fileHeader/destination/text()")
    private String destination;//文件目的地
    @XmlPath("/fileHeader/createdTime/text()")
    private String createdTime;//文件生成日期和时间


    @XmlPath("/basicInfo/station/text()")
    private String station;//接收站名
    @XmlPath("/basicInfo/jobTaskID/text()")
    private String jobTaskID;//作业任务编号
    @XmlPath("/basicInfo/taskSerialNumber/text()")
    private String taskSerialNumber;//任务单流水号
    @XmlPath("/basicInfo/satelliteName/text()")
    private String satelliteName;//卫星名
    @XmlPath("/basicInfo/orbit/text()")
    private String orbit;//轨道或条带信息
    @XmlPath("/basicInfo/dataType/text()")
    private String dataType;//数据类型

    @XmlPath("/metaInfo/dataFilename/text()")
    private String dataFilename;//数据文件名
    @XmlPath("/metaInfo/fileSize/text()")
    private String fileSize;//数据文件大小
    @XmlPath("/metaInfo/receivingStartTime/text()")
    private String receivingStartTime;//数据记录任务开始时间
    @XmlPath("/metaInfo/receivingEndTime/text()")
    private String receivingEndTime;//数据记录任务结束时间
    @XmlPath("/metaInfo/jysID/text()")
    private String jysID;//解压缩设备编号
    @XmlPath("/metaInfo/jysSensorName/text()")
    private String jysSensorName;//解压缩文件对应的xxxx
    @XmlPath("/metaInfo/orderID/text()")
    private String orderID;//内部一级作业编号
    @XmlPath("/metaInfo/subOderID/text()")
    private String subOderID;//内部二级作业编号

    public JysXml(){

    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setJobTaskID(String jobTaskID) {
        this.jobTaskID = jobTaskID;
    }

    public void setTaskSerialNumber(String taskSerialNumber) {
        this.taskSerialNumber = taskSerialNumber;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setDataFilename(String dataFilename) {
        this.dataFilename = dataFilename;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setReceivingStartTime(String receivingStartTime) {
        this.receivingStartTime = receivingStartTime;
    }

    public void setReceivingEndTime(String receivingEndTime) {
        this.receivingEndTime = receivingEndTime;
    }

    public void setJysID(String jysID) {
        this.jysID = jysID;
    }

    public void setJysSensorName(String jysSensorName) {
        this.jysSensorName = jysSensorName;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setSubOderID(String subOderID) {
        this.subOderID = subOderID;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getStation() {
        return station;
    }

    public String getJobTaskID() {
        return jobTaskID;
    }

    public String getTaskSerialNumber() {
        return taskSerialNumber;
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public String getOrbit() {
        return orbit;
    }

    public String getDataType() {
        return dataType;
    }

    public String getDataFilename() {
        return dataFilename;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getReceivingStartTime() {
        return receivingStartTime;
    }

    public String getReceivingEndTime() {
        return receivingEndTime;
    }

    public String getJysID() {
        return jysID;
    }

    public String getJysSensorName() {
        return jysSensorName;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getSubOderID() {
        return subOderID;
    }
}


