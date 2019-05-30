package com.server.demotest;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.server.common.resource.converter.StringToDateConverter;
import com.server.common.resource.converter.StringToIntegerConverter;
import com.server.common.resource.reader.ResId;
import com.server.common.resource.reader.Resource;

import java.util.Date;

/**
 * @author yuxianming
 * @date 2019/5/24 15:36
 */
@Resource
public class BookResource {

    @ResId
    @CsvCustomBindByName(converter = StringToIntegerConverter.class)
    private int id;

    @CsvBindByName
    private String name;

    @CsvCustomBindByName(converter = StringToDateConverter.class)
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BookResource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
