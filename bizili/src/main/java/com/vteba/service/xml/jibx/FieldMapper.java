package com.vteba.service.xml.jibx;

import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;

import com.vteba.service.xml.Field;

/**
 * 基于JiBX的xml field mapper。
 * @author yinlei
 * @date 2013年12月1日 下午8:42:08
 */
public class FieldMapper implements IMarshaller, IUnmarshaller, IAliasable {
	private String uri;
	private int index;
	private String fieldName;
	
	public FieldMapper() {
		super();
		this.uri = null;
		this.index = 0;
		this.fieldName = "field";
	}

	public FieldMapper(String uri, int index, String fieldName) {
		super();
		this.uri = uri;
		this.index = index;
		this.fieldName = fieldName;
	}

	@Override
	public boolean isPresent(IUnmarshallingContext context) throws JiBXException {
		return context.isAt(uri, fieldName);
	}

	@Override
	public Object unmarshal(Object object, IUnmarshallingContext context)
			throws JiBXException {
		Field field = (Field) object;
		if (field == null) {
			field = new Field();
		}
		UnmarshallingContext ctx = (UnmarshallingContext) context;
		//ctx.getText();//要解析的这段xml字符串
		String name = ctx.attributeText(uri, getAttrName());//按属性名取属性值
		//ctx.getAttributeValue(0);//按照顺序取属性值
		//ctx.getAttributeCount();//属性数量
		field.setName(name);
		
		ctx.parsePastStartTag(uri, fieldName);//开始解析
		String value = ctx.parseContentText();
		//ctx.getText();
		//ctx.getName();
		ctx.parsePastEndTag(uri, fieldName);//解析后要关闭
		field.setValue(value);
		return field;
	}

	@Override
	public boolean isExtension(String arg0) {
		return false;
	}

	@Override
	public void marshal(Object source, IMarshallingContext context)
			throws JiBXException {
		Field field = (Field) source;
		MarshallingContext ctx = (MarshallingContext) context;
		ctx.startTagAttributes(index, fieldName);
		ctx.attribute(index, getAttrName(), field.getName()).closeStartContent();
		ctx.content(field.getValue());
		ctx.endTag(index, fieldName);
	}

	public String getAttrName() {
		return "name";
	}
	
}
