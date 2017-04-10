package io.zhijian.utils;

import com.alibaba.fastjson.JSON;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanCopier {
    private static final String DOZER_CONFIG = "dozer-config/dozerBeanMapping.xml";
    private static DozerBeanMapper mapper;

    private BeanCopier() {
    }

    private static BeanCopier single = null;

    // 静态工厂方法
    public static BeanCopier getInstance() {
        if (single == null) {
            single = new BeanCopier();
        }
        return single;
    }

    static {
        mapper = new DozerBeanMapper();
        List<String> mappers = new ArrayList<String>();
        mappers.add("dozer-config/dozerBeanMapping.xml");
        mapper.setMappingFiles(mappers);
    }

    /**
     * 对象转换
     * @param source           原始数据对象
     * @param destinationClass 目标对象数据类型
     * @param <T>
     * @return                 返回目标对象
     */
    public static <T> T copy(Object source, Class<T> destinationClass) {
        return (T) mapper.map(source, destinationClass);
    }

    /**
     * 对象集合转换
     * @param sourceList        原始数据对象集合
     * @param destinationClass  目标对象数据类型
     * @param <T>               返回目标对象集合
     * @return
     */
    public static <T> List<T> copy(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<T>();

        for (Object sourceObject : sourceList) {
            T destinationObject = mapper.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 复制对象
     * @param o
     * @param calssz
     * @return
     */
    public static <T> T copyJSONObject(Object o, Class<T> calssz) {
        String string = JSON.toJSONString(o);
        return JSON.parseObject(string, calssz);
    }

    /**
     * 赋值list
     * @param o
     * @param calssz
     * @return
     */
    public static <T> List<T> copyJSONList(Object o, Class<T> calssz) {
        String string = JSON.toJSONString(o);
        return JSON.parseArray(string, calssz);
    }

}
