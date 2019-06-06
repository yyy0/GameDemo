package com.server.common.resource;

import com.server.common.resource.reader.AnnotationScanner;
import com.server.common.resource.reader.CsvUtil;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author yuxianming
 * @date 2019/5/24 18:27
 */
@Component
public class ResourceManager {
    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);
    /**
     * csv文件后缀名
     */
    private static final String EXT = "csv";
    /**
     * 资源文件夹
     */
    private static final String RESOURCE = "resource";

    /**
     * resource对象所在包路径
     */
    private static final String RESOURCE_PACKAGE = "com.server";

    private Map<String, Map<Integer, Object>> resourceDatas = new HashMap<>();


    private Set<Class<?>> resourceClazz = new HashSet<>();

    /**
     * 获取所有csv文件
     *
     * @param resourcePath
     * @param recursive    是否递归遍历
     * @return
     */
    private Collection<File> getAllCsvFile(String resourcePath, boolean recursive) {
        File csvFile = new File(resourcePath);

        if (!(csvFile.exists() && csvFile.isDirectory())) {
            logger.error("the directory to package is empty: {}", resourcePath);
            return null;
        }

        return FileUtils.listFiles(csvFile, new String[]{EXT}, recursive);
    }

    /**
     * 去除文件后缀名
     *
     * @param fileName
     * @return
     */
    public String getSimpleFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获取文件路径
     *
     * @param pkgName
     * @return
     */
    public String getPkgPath(String pkgName) {
        String pkgDirName = pkgName.replace('.', '/');
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDirName);

        return url == null ? null : url.getFile();
    }

    /**
     * 获取所有resource的class集合
     *
     * @return
     */
    private Set<Class<?>> loadResourceClazz() {

        String packPath = getPkgPath(RESOURCE_PACKAGE);
        Set<Class<?>> classSet = AnnotationScanner.scanClazzByAnnotation(RESOURCE_PACKAGE, packPath, true,
                Resource.class);
        return classSet;
    }

    /**
     * 加载resource并转换成对应的对象
     */
    public void loadResource() {
        this.resourceClazz = loadResourceClazz();
        String packPath = getPkgPath(RESOURCE);
        Collection<File> allClassFile = getAllCsvFile(packPath, false);
        if (allClassFile == null) {
            logger.error("未获取csv文件资源！！");
            return;
        }
        for (File file : allClassFile) {

            //获取无后缀的文件名称
            String fileSimpleName = getSimpleFileName(file.getName());
            Class<?> fileClass = null;
            for (Class<?> fileClazz : resourceClazz) {
                if (fileClazz.getSimpleName().equals(fileSimpleName)) {
                    fileClass = fileClazz;
                    break;
                }
            }
            if (fileClass == null) {
                logger.error("找不到对应的class与csv对应: " + fileSimpleName);
                return;
            }
            String csvName = RESOURCE + File.separator + file.getName();

            List resourceBeans = CsvUtil.getCsvData(csvName, fileClass);
            if (resourceBeans != null) {
                Map<Integer, Object> resourceData = new HashMap<>();
                //将主键封装进map
                for (Object resource : resourceBeans) {
                    int getValue = getIdValue(resource);
                    resourceData.put(getValue, resource);
                }

                resourceDatas.put(fileSimpleName, resourceData);
            }
        }
    }

    /**
     * 反射获取指定注解（@ResId）的值
     */

    public int getIdValue(Object resource) {
        Class clz = resource.getClass();
        Field[] fields = clz.getDeclaredFields();
        int getValue = 0;
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(ResId.class)) {
                    String fieldName = field.getName();
                    String getFieldName = parGetName(fieldName);
                    Method method = null;
                    method = clz.getDeclaredMethod(getFieldName);
                    getValue = (Integer) ReflectionUtils.invokeMethod(method, resource);
                }
            }
            return getValue;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return getValue;

    }

    /**
     * 获取某属性的get方法
     */
    public String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        char[] charArray = fieldName.toCharArray();
        charArray[0] -= 32;
        String newName = String.valueOf(charArray);
        return "get" + newName;
    }

    public Map<Integer, Object> getResources(String resourceName) {
        return resourceDatas.get(resourceName);
    }

}
