package com.vteba.web.servlet.converter.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Spring MVC JSON HttpMessageConverter，基于fastjson实现。
 * @author yinlei
 * @date 2013年10月11日 下午8:53:08
 */
public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object> {

	public final static Charset UTF8 = Charset.forName("UTF-8");
	private final static SimplePropertyFilter FILTER = new SimplePropertyFilter();
	private Charset charset = UTF8;

	private SerializerFeature[] features = new SerializerFeature[0];

    public FastJsonHttpMessageConverter(){
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8),
        		new MediaType("text", "json", UTF8));
    }
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }
	
	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[2048];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }
        byte[] bytes = baos.toByteArray();
        return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, FILTER, features);
        byte[] bytes = text.getBytes(charset);
        out.write(bytes);
	}

	//要和原来的非泛型的进行统一
	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		return canRead(contextClass, mediaType);
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		return readGeneric(type, contextClass, inputMessage);
	}

	private Object readGeneric(Type type, Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[2048];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }
        //String text = IOUtils.toString(in);
        //if (text == null || text.equals("")) {
        //	return null;
        //}
        //return JSON.parseObject(text, type, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
        byte[] bytes = baos.toByteArray();
        if (bytes.length == 0) {
        	return null;
        }
        return JSON.parseObject(bytes, type);
	}
}
