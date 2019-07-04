package com.server.common.resource.reader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author yuxianming
 * @date 2019/5/22 22:23
 */
public class CsvUtil {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    /**
     * 解析csv文件并转成bean
     *
     * @param
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvData(String fileName, Class<T> clazz) {
        InputStreamReader in;
        if ("".equals(fileName)) {
            throw new IllegalArgumentException("文件名不能为空！！");
        }
        try {
            InputStream filStream = clazz.getClassLoader().getResourceAsStream(fileName);
            if (filStream == null) {
                logger.error("读取不了文件！！-->" + fileName);
                return null;
            }
            in = new InputStreamReader(filStream, "gbk");
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                    //分割符
                    .withSeparator(',')
                    //引号字符
                    .withQuoteChar('\'')
                    //读取header时跳过的行数
                    .withSkipLines(CsvConstant.HEADERLINE)
                    .withMappingStrategy(strategy).build();
            return csvToBean.parse();
        } catch (Exception e) {
            logger.error(e.getMessage() + "  filename找不到对应的bean对象！！-->" + fileName);

        }
        return null;

    }

    public static <T> List<T> getCsvData(InputStream fileStream, Class<T> clazz) {
        InputStreamReader in;
        if ("".equals(fileStream)) {
            throw new IllegalArgumentException("文件不能为空！！");
        }
        try {
            in = new InputStreamReader(fileStream, "gbk");
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                    //分割符
                    .withSeparator(',')
                    //引号字符
                    .withQuoteChar('\'')
                    //读取header时跳过的行数
                    .withSkipLines(CsvConstant.HEADERLINE)
                    .withMappingStrategy(strategy).build();
            return csvToBean.parse();
        } catch (Exception e) {
            logger.error(e.getMessage() + fileStream.toString());

        }
        return null;

    }


}
