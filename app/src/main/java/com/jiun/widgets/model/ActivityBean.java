package com.jiun.widgets.model;

/**
 * @author HJY
 * @date 2018/08/08/17:01
 * <pre>
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class ActivityBean {
    private String name;
    private Class clazz;

    public ActivityBean(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public Class getClazz() {
        return clazz;
    }
}
