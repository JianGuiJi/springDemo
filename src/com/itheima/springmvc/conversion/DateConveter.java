package com.itheima.springmvc.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 转换日期类型的数据
 * S : 页面传递过来的类型
 * T ： 转换后的类型
 *
 * @author lx
 */
public class DateConveter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        // TODO Auto-generated method stub
        try {
            if (null != source) {
                // 2016:11-05 11_43-50
                System.out.println("======== convert ======");
                System.out.println(source);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = df.parse(source);
                System.out.println(date);

                return date;
            }
        } catch (Exception e) {
            // TODO: handle exception;
            System.out.println(e);
        }
        return null;
    }

}
