package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 应用授权地址
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_app_authorized")
public class UcAppAuthorized implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * APP key
     */
    @TableId("app_key")
    private String appKey;

    /**
     * 授权地址
     */
    private String url;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UcAppAuthorized{" +
        ", appKey=" + appKey +
        ", url=" + url +
        "}";
    }
}
