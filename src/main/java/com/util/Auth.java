package com.util;

import com.baidu.aip.imageclassify.AipImageClassify;

/**
 * @author prism17
 * @email 2839296425@qq.com
 * @Date 20/05/2019 23:25
 **/
public class Auth {
    private static final String APP_ID = "16434400";
    private static final String API_KEY = "hay2PRqBINBN6bomWWmWfAPQ";
    private static final String SECRET_KEY = "4ErbGGHFMpXrlB13juG73hDoNDmispqs";

    static public AipImageClassify getClient() {
        return new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
    }
}
