package com.swsk.frame.springboot.business.rgyx.ynaqsj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huangyongqi
 * @date 2023/7/14
 */
// 为了在子类对象中能取到父类对象的属性
@MappedSuperclass
// 需要开启domain对象的监听，否则无法监测到对象的修改
// @EnableJpaAuditing main方法中
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseFieldEntity implements Serializable {

    private static final long serialVersionUID = -6017306316870234303L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", unique = true, updatable = false, length = 36)
    protected String id;

    @ApiModelProperty("创建时间")
    @ExcelProperty("记录时间")
    @Column(name = "create_date")
    @JsonIgnore
    @CreatedDate
    protected Date createDate;

    @ApiModelProperty("修改时间")
    @Column(name = "update_date")
    @JsonIgnore
    @LastModifiedDate
    protected Date updateDate;

    @ApiModelProperty("创建用户id")
    @Column(name = "create_user_id", length = 36)
    protected String createUserId;

    @ApiModelProperty("修改用户id")
    @Column(name = "update_user_id", length = 36)
    protected String updateUserId;

    @ApiModelProperty("删除时间")
    @Column(name = "del_date", length = 19)
    protected String delDate;


}
