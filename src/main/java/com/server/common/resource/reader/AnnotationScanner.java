package com.server.common.resource.reader;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author yuxianming
 * @date 2019/5/24 12:18
 */
public class AnnotationScanner {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationScanner.class);
    /**
     * 后缀名称
     */
    private static final String EXT = "class";

    private static final String PRE = "target/classes/";

    /**
     * 根据包名获取包的URL
     *
     * @param pkgName com.demo.controller
     * @return
     */
    public static String getPkgPath(String pkgName) {
        String pkgDirName = pkgName.replace('.', '/');
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDirName);

        return url == null ? null : url.getFile();
    }

    /**
     * 获取指定包下所有类对象的集合
     *
     * @param packageName 包名(com.demo.controller)
     * @param packagePath 包路径(/Users/xxx/workspace/java/project/out/production/classes/com/demo/controller)
     * @param recursive   是否递归遍历子目录
     * @return 类集合
     */
    public static Set<Class<?>> scanClazz(String packageName, String packagePath, final boolean recursive) {
        Set<Class<?>> classesSet = new HashSet<>();

        Collection<File> allClassFile = getAllClassFile(packagePath, recursive);

        for (File curFile : allClassFile) {
            try {
                classesSet.add(getClassObj(curFile, packagePath, packageName));
            } catch (ClassNotFoundException e) {
                logger.error("load class fail", e);
            }
        }

        return classesSet;
    }

    /**
     * 获取指定包下包含指定注解的所有类对象的集合
     *
     * @param pkgName           包名(com.demo.controller)
     * @param pkgPath           包路径(/Users/xxx/workspace/java/project/out/production/classes/com/demo/controller)
     * @param recursive         是否递归遍历子目录
     * @param targetAnnotations 指定注解
     * @return 以注解和对应类集合构成的键值对
     */
    public static Map<Class<? extends Annotation>, Set<Class<?>>> scanClassesByAnnotations(
            String pkgName, String pkgPath, final boolean recursive, List<Class<? extends Annotation>> targetAnnotations) {
        Map<Class<? extends Annotation>, Set<Class<?>>> resultMap = new HashMap<>(16);

        Collection<File> allClassFile = getAllClassFile(pkgPath, recursive);

        for (File curFile : allClassFile) {
            try {
                Class<?> curClass = getClassObj(curFile, pkgPath, pkgName);
                for (Class<? extends Annotation> annotation : targetAnnotations) {
                    if (curClass.isAnnotationPresent(annotation)) {
                        if (!resultMap.containsKey(annotation)) {
                            resultMap.put(annotation, new HashSet<Class<?>>());
                        }
                        resultMap.get(annotation).add(curClass);
                    }
                }
            } catch (ClassNotFoundException e) {
                logger.error("load class fail", e);
            }
        }

        return resultMap;
    }

    /**
     * 通过指定注解获得class
     *
     * @param pkgName
     * @param pkgPath
     * @param recursive
     * @param targetAnnotation
     * @return
     */
    public static Set<Class<?>> scanClazzByAnnotation(
            String pkgName, String pkgPath, final boolean recursive, Class<? extends Annotation> targetAnnotation) {
        Set<Class<?>> clazz = new HashSet<>();

        Collection<File> allClassFile = getAllClassFile(pkgPath, recursive);

        for (File curFile : allClassFile) {
            try {
                Class<?> curClass = getClassObj(curFile, pkgPath, pkgName);
                if (curClass.isAnnotationPresent(targetAnnotation)) {
                    clazz.add(curClass);
                }
            } catch (ClassNotFoundException e) {
                logger.error("load class fail", e);
            }
        }

        return clazz;
    }

    /**
     * 加载类
     *
     * @param file
     * @param pkgPath
     * @param pkgName
     * @return
     * @throws ClassNotFoundException
     */
    private static Class<?> getClassObj(File file, String pkgPath, String pkgName) throws ClassNotFoundException {
        // 考虑class文件在子目录中的情况

        String absPath = file.getPath().substring(0, file.getPath().length() - EXT.length() - 1);
        String className = absPath.substring(pkgPath.length()).replace(File.separatorChar, '.');
        className = className.startsWith(".") ? pkgName + className : pkgName + "." + className;

        return Thread.currentThread().getContextClassLoader().loadClass(className);
    }

    /**
     * 遍历指定目录下所有扩展名为class的文件
     *
     * @param pkgPath   包目录
     * @param recursive 是否递归遍历子目录
     * @return
     */
    private static Collection<File> getAllClassFile(String pkgPath, boolean recursive) {
        File fPkgDir = new File(pkgPath);

        if (!(fPkgDir.exists() && fPkgDir.isDirectory())) {
            logger.error("the directory to package is empty: {}", pkgPath);
            return null;
        }

        return FileUtils.listFiles(fPkgDir, new String[]{EXT}, recursive);
    }

    /**
     * 查找指定注解的Method
     *
     * @param classes           查找范围
     * @param targetAnnotations 指定的注解
     * @return 以注解和对应Method类集合构成的键值对
     */
    public static Map<Class<? extends Annotation>, Set<Method>> scanMethodsByAnnotations(Set<Class<?>> classes,
                                                                                         List<Class<? extends Annotation>> targetAnnotations) {
        Map<Class<? extends Annotation>, Set<Method>> resultMap = new HashMap<>(16);

        for (Class<?> cls : classes) {
            Method[] methods = cls.getMethods();

            for (Class<? extends Annotation> annotation : targetAnnotations) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(annotation)) {
                        if (!resultMap.containsKey(annotation)) {
                            resultMap.put(annotation, new HashSet<Method>());
                        }
                        resultMap.get(annotation).add(method);
                    }
                }
            }
        }

        return resultMap;
    }

    public static void main(String[] args) {
        String packageName = "com.server";
//        String packPath = "target/classes/com";
        String packPath = getPkgPath(packageName);
        logger.info("pkgPath is {}", packageName);

        Set<Class<?>> classSet = scanClazzByAnnotation(packageName, packPath, true,
                Resource.class);


    }


}
