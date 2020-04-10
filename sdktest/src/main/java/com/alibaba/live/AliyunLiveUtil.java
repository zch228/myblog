package com.alibaba.live;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cunw.cloud.framework.utils.text.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云直播工具类
 */
public class AliyunLiveUtil {

    private static final Logger log = LoggerFactory.getLogger(AliyunLiveUtil.class);

    /**
     *
     * 根据源id创建该id的推流url
     *
     * @param sourceId
     * @param aliyunLiveConfig
     * @return
     */
    public static String createPushUrl(Integer sourceId, LiveTypeEnum liveTypeEnum, AliyunLiveConfig aliyunLiveConfig) {

        // 推流域名
        String pushDomain = aliyunLiveConfig.getAliyunLivePushDomain();
        // 应用名称
        String appName = aliyunLiveConfig.getAliyunLiveAppName();
        // 流名称
        String streamName = aliyunLiveConfig.getAliyunLiveStreamName() + liveTypeEnum.getValue() + sourceId;
        // 推流签名key
        String pushIdentKey = aliyunLiveConfig.getAliyunLivePushIdentKey();
        // 签名url有效时间
        Integer identUrlValidTime = aliyunLiveConfig.getAliyunLiveIdentUrlValidTime();

        // 计算过期时间
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + identUrlValidTime);

        // 组合推流域名前缀
//      rtmp://{pushDomain}/{appName}/{streamName}
//        String rtmpUrl = StrUtil.format("rtmp://{}/{}/{}", pushDomain, appName, streamName);
        String rtmpUrl = "rtmp://"+pushDomain+"/"+appName+"/"+streamName;
       // log.debug("推流域名前缀，rtmpUrl=" + rtmpUrl);

        // 组合md5加密串
//      /{appName}/{streamName}-{timestamp}-0-0-{pushIdentKey}
        String md5Url = StrUtil.format("/{}/{}-{}-0-0-{}", appName, streamName, timestamp, pushIdentKey);
//        String md5Url = "/"+ appName + "/" + streamName + "-" + timestamp + "-0-0-" + pushIdentKey;
        // md5加密
        String md5Str = DigestUtil.md5Hex(md5Url);
//        String  md5Str= MD5Utils.md5(md5Url);
        // log.debug("md5加密串，md5Url=" + md5Url + "------md5加密结果，md5Str=" + md5Str);

        // 组合最终鉴权过的推流域名
//      {rtmpUrl}?auth_key={timestamp}-0-0-{md5Str}
//        String finallyPushUrl = StrUtil.format("{}?auth_key={}-0-0-{}", rtmpUrl, timestamp, md5Str);
        String finallyPushUrl = rtmpUrl + "?auth_key=" + timestamp + "-0-0-" + md5Str;
        log.debug("最终鉴权过的推流域名=" + finallyPushUrl);

        return finallyPushUrl;
    }

    /**
     * 创建拉流域名，key=rtmpUrl、flvUrl、m3u8Url，代表三种拉流类型域名
     *
     * @param sourceId
     * @param aliyunLiveConfig
     * @return
     */
    public static Map<String, String> createPullUrl(Integer sourceId, LiveTypeEnum liveTypeEnum, AliyunLiveConfig aliyunLiveConfig) {

        // 拉流域名
        String pullDomain = aliyunLiveConfig.getAliyunLivePullDomain();
        // 应用名称
        String appName = aliyunLiveConfig.getAliyunLiveAppName();
        // 流名称
//        String streamName = StrUtil.format(aliyunLiveConfig.getAliyunLiveStreamName(), liveTypeEnum.getValue(), sourceId);
        String streamName = aliyunLiveConfig.getAliyunLiveStreamName() + liveTypeEnum.getValue() + sourceId;

        // 拉流签名key
        String pullIdentKey = aliyunLiveConfig.getAliyunLivePullIdentKey();
        // 签名url有效时间
        Integer identUrlValidTime = aliyunLiveConfig.getAliyunLiveIdentUrlValidTime();

        // 计算过期时间
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + identUrlValidTime);

        // 组合通用域名
//      {pullDomain}/{appName}/{streamName}
//        String pullUrl = StrUtil.format("{}/{}/{}", pullDomain, appName, streamName);
        String pullUrl = pullDomain + "/" + appName + "/" + streamName ;
        // log.debug("组合通用域名，pullUrl=" + pullUrl);

        // 组合md5加密串
//      /{appName}/{streamName}-{timestamp}-0-0-{pullIdentKey}
//        String md5Url = StrUtil.format("/{}/{}-{}-0-0-{}", appName, streamName, timestamp, pullIdentKey);
        String md5Url = "/" + appName + "/" + streamName + "-" + timestamp + "-0-0-" + pullIdentKey;
        // md5加密
//        String md5Str = DigestUtil.md5Hex(md5Url);
        String  md5Str= MD5Utils.md5(md5Url);

        //   log.debug("md5加密串，md5Url    =" + md5Url + "       ------     md5加密结果，md5Str=" + md5Str);

        // 组合三种拉流域名前缀
//        rtmp://{pullUrl}?auth_key={timestamp}-0-0-{md5Str}
//        String rtmpUrl = StrUtil.format("rtmp://{}?auth_key={}-0-0-{}", pullUrl, timestamp, md5Str);
        String rtmpUrl = "rtmp://"+pullUrl+"?auth_key="+timestamp+"-0-0-"+md5Str ;

        log.debug("最终鉴权过的拉流rtmp域名=" + rtmpUrl);


        HashMap<String, String> urlMap = new HashMap(16);
        urlMap.put("rtmpUrl", rtmpUrl);


        return urlMap;
    }
}





