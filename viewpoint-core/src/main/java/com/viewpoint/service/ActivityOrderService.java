package com.viewpoint.service;

import com.viewpoint.dataobject.ActivityOrder;

public interface ActivityOrderService {
    /**
     * 生成订单
     * @param activityOrder
     */
    void save(ActivityOrder activityOrder);

    /**
     * 微信ID查找活动订单
     * @param buyerOpenid
     * @return
     */
    List<ActivityOrder> findByBuyerOpenid(String buyerOpenid);

    /**
     * 活动ID查找参加人数
     * @param activityId
     * @return
     */
    long countByActivityId(String activityId);
}
