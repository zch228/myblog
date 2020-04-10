package com.alibaba.live;

import lombok.Data;

/**
 * 阿里云直播配置参数
 */
@Data
public class AliyunLiveConfig {

    /**
     * 推流域名
     */
    private String aliyunLivePushDomain;

    /**
     * 拉流域名
     */
    private String aliyunLivePullDomain;

    /**
     * 直播测试appName
     */
    private String aliyunLiveAppName;

    /**
     * 直播测试streamName{直播类型}_{类型id}
     */
    private String aliyunLiveStreamName;

    /**
     * 推流鉴权url key
     */
    private String aliyunLivePushIdentKey;

    /**
     * 拉流鉴权url key
     */
    private String aliyunLivePullIdentKey;

    /**
     * 鉴权url的有效时间（秒），默认30分钟，1800秒 key
     */
    private Integer aliyunLiveIdentUrlValidTime;

}
