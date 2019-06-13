package com.util;

/**
 * @author prism17
 * @email 2839296425@qq.com
 * @Date 25/05/2019 14:11
 **/

public class TopicUtil {
    static public String getCHName(String name) {
        switch (name) {
            case "plant":
                return "植物";
            case "animal":
                return "动物";
            case "food":
                return "菜品";
            case "vegetable":
                return "果蔬";
            case "wine":
                return "红酒";
            case "other":
                return "通用";
            default:
                return "所有";
        }
    }
}
