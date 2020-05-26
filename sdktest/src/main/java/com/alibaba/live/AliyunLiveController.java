package com.alibaba.live;

import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.UUID;


@Slf4j
public class AliyunLiveController {

    private AliyunLiveConfig aliyunLiveConfig;


    public String addLive(Integer courseId) {

        /**
         * 注意，推流要在播流域名里面生成
         */

        System.out.println(aliyunLiveConfig.toString());

        String pushUrl = AliyunLiveUtil.createPushUrl(courseId, LiveTypeEnum.course, aliyunLiveConfig);
        log.debug("推流域名=" + pushUrl);

        Map<String, String> pullUrl = AliyunLiveUtil.createPullUrl(courseId, LiveTypeEnum.course, aliyunLiveConfig);
        log.debug("拉流域名=" + pullUrl.toString());


        return "hello login";
    }

    public static void main(String[] args) {
        /**
         * aliyun.live.push.domain=live.cunwedu.com.cn
         #推流鉴权url key
         aliyun.live.push.ident.key=CUNW20200211live
         #拉流域名
         aliyun.live.pull.domain=broadcast.cunwedu.com.cn
         #拉流鉴权url key
         aliyun.live.pull.ident.key=CUNW20200211play
         #直播测试appName
         aliyun.live.appName=test
         #直播测试streamName{直播类型}{类型id}
         aliyun.live.streamName={}{}
         #鉴权url的有效时间（秒），默认30分钟，1800秒
         aliyun.live.ident.url.validTime=1800
         */
        AliyunLiveConfig aliyunLiveConfig = new AliyunLiveConfig();
        Integer courseId = 111;
        aliyunLiveConfig.setAliyunLiveAppName("");
        aliyunLiveConfig.setAliyunLiveIdentUrlValidTime(3600);
        aliyunLiveConfig.setAliyunLivePullDomain("");
        aliyunLiveConfig.setAliyunLivePullIdentKey("");
        aliyunLiveConfig.setAliyunLivePushDomain("");
        aliyunLiveConfig.setAliyunLivePushIdentKey("");
        aliyunLiveConfig.setAliyunLiveStreamName("");

        String pushUrl = AliyunLiveUtil.createPushUrl(courseId, LiveTypeEnum.course, aliyunLiveConfig);
        System.out.println(pushUrl);
    //    log.debug("推流域名=" + pushUrl);

        Map<String, String> pullUrl = AliyunLiveUtil.createPullUrl(courseId, LiveTypeEnum.course, aliyunLiveConfig);
        System.out.println(pullUrl.toString());
     //   log.debug("拉流域名=" + pullUrl.toString());

        String s = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(s);
    }

}
