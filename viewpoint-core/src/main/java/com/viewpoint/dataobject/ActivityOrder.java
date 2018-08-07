package com.viewpoint.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class ActivityOrder implements Serializable {

    private static final long serialVersionUID = 2307429457205668009L;
    @Id
    private String activityOrderId;

    private String activityId;

    private String buyerName;

    private String buyerPhone;

    /** 微信openID  */
    private String buyerOpenid;

    /** 活动订单状态(默认已报名) */
    private String orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
