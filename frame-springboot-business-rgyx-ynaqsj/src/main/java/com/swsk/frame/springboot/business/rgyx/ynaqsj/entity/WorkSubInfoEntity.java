package com.swsk.frame.springboot.business.rgyx.ynaqsj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "t_work_sub_info")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Data
@DynamicInsert
@DynamicUpdate
@ApiModel("作业车辆")
@AllArgsConstructor
@NoArgsConstructor
public class WorkSubInfoEntity implements Serializable {


    private static final long serialVersionUID = -8482327147778106175L;
    @Id
    @Column(name = "ID", unique = true, updatable = false, length = 36)
    private String id;  // 主键
    @Column(name = "AZIMUTH")
    private String azimuth;  // 方位
    @Column(name = "CREATE_ID")
    private String createId;  // 创建人
    @Column(name = "CREATE_TIME")
    private String createTime;  // 创建时间
    @Column(name = "ELEVATION")
    private String elevation;  // 仰角
    @Column(name = "FILL_PERSON")
    private String fillPerson;  // 填报人
    @Column(name = "LAT")
    private String lat;  //
    @Column(name = "LON")
    private String lon;  //
    @Column(name = "ROCKET_NUM")
    private String rocketNum;  // 弹药用量
    @Column(name = "WORK_DATE")
    private String workDate;  // 作业日期
    @Column(name = "WORK_DATE_BEGIN")
    private String workDateBegin;  // 开始时间
    @Column(name = "WORK_DATE_END")
    private String workDateEnd;  // 结束时间
    @Column(name = "WORK_POINT_NAME")
    private String workPointName;  // 作业点名称
    @Column(name = "WORK_POINT_NO")
    private String workPointNo;  // 作业点编码
    @Column(name = "EQUIPS_NO")
    private String equipsNo;  // 使用的装备编号
    @Column(name = "EXAMPLE")
    private String example;  // 个例


    @Column(name = "JOB_LOCATION")
    private String jobLocation; // 作业地点
    @Column(name = "COUNTY")
    private String county; // 所属县
    @Column(name = "JOB_TYPE")
    private String jobType; // 作业类型
    @Column(name = "WEATHER_BEFORE_WORK")
    private String weatherBeforeWork; // 作业前天气状况
    @Column(name = "WEATHER_AFTER_HOMEWORK")
    private String weatherAfterHomework; // 作业后天气状况
    @Column(name = "JOB_EFFECT")
    private String jobEffect; // 作业效果
    @Column(name = "REGION")
    private String region; // 所属地区
    @Column(name = "ASSIGNMENT_STYLE")
    private String assignmentStyle; // 作业方式
    @Column(name = "NUMBER_JOBS")
    private String numberJobs = "1"; // 作业次数
    @Column(name = "FID")
    private String fid; // 文件内编码

    @Column(name = "REAL_POINT_NO")
    private String realPointNo;


}
