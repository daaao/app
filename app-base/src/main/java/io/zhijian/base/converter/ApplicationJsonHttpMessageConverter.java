package io.zhijian.base.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import io.zhijian.base.exception.StatusCode;
import io.zhijian.base.model.response.IgnoreResponse;
import io.zhijian.base.model.response.Response;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @文件名 :ApplicationJsonHttpMessageConverter.java
 * @描述 :自定义统一返回格式
 */
public class ApplicationJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    /**
     * 响应输出
     * @param obj
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        //解决@ref
        SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
        //解决null对象
        SerializerFeature writeMapNullValue = SerializerFeature.WriteMapNullValue;
        //解决""字段
        SerializerFeature writeNullStringAsEmpty = SerializerFeature.WriteNullListAsEmpty;
        //解决时间long转成固定时间格式
        SerializerFeature writeDateUseDateFormat = SerializerFeature.WriteDateUseDateFormat;

        //响应结果
        String result = "";
        if(obj instanceof IgnoreResponse){
            result = JSON.toJSONString(obj, feature, writeMapNullValue, writeNullStringAsEmpty, writeDateUseDateFormat);
        }else if(obj instanceof List){
            List list = (List) obj;
            if (list != null && !list.isEmpty() && list.get(0) instanceof IgnoreResponse) {
                result = JSON.toJSONString(obj, feature, writeMapNullValue, writeNullStringAsEmpty, writeDateUseDateFormat);
            }else{
                Response response = new Response(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), obj);
                result = JSON.toJSONString(response, feature, writeMapNullValue, writeNullStringAsEmpty, writeDateUseDateFormat);
            }
        }else{
            Response response = new Response(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), obj);
            result = JSON.toJSONString(response, feature, writeMapNullValue, writeNullStringAsEmpty, writeDateUseDateFormat);
        }
        byte[] bytes = result.getBytes(super.getCharset());
        out.write(bytes);
        out.flush();
    }

    /**
     * 请求输入
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return super.readInternal(clazz, inputMessage);
    }
}
