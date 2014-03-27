package com.vteba.service.xml.jibx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jibx.runtime.IAliasable;
import org.jibx.runtime.IMarshallable;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;

/**
 * Jibx HashMap 映射。
 * @author yinlei
 * date 2013-9-21 下午1:10:18
 */
public class HashMapper implements IMarshaller, IUnmarshaller, IAliasable {
        
    private static final int DEFAULT_SIZE = 10;
    
    private String m_uri;
    private int m_index;
    private String m_name;
    
    /**
     * Default constructor. This uses a pre-defined name for the top-level
     * element. It'll be used by JiBX when no name information is supplied by
     * the mapping which references this custom marshaller/unmarshaller.
     */
    public HashMapper() {
        m_uri = null;
        m_index = 0;
        m_name = "hashmap";
    }
    
    /**
     * Aliased constructor. This takes a name definition for the top-level
     * element. It'll be used by JiBX when a name is supplied by the mapping
     * which references this custom marshaller/unmarshaller.
     *
     * @param uri namespace URI for the top-level element (also used for all
     * other names within the binding)
     * @param index namespace index corresponding to the defined URI within the
     * marshalling context definitions
     * @param name local name for the top-level element
     */
    public HashMapper(String uri, int index, String name) {
        m_uri = uri;
        m_index = index;
        m_name = name;
    }
    
    /**
     * Method which can be overridden to supply a different name for the wrapper
     * element attribute used to give the number of items present. If present,
     * this attribute is used when unmarshalling to set the initial size of the
     * hashmap. It will be generated when marshalling if the supplied name is
     * non-<code>null</code>.
     */
    protected String getSizeAttributeName() {
        return "size";
    }
    
    /**
     * Method which can be overridden to supply a different name for the element
     * used to represent each item in the map.
     */
    protected String getEntryElementName() {
        return "entry";
    }
    
    /**
     * Method which can be overridden to supply a different name for the
     * attribute defining the key value for each item in the map.
     */
    protected String getKeyAttributeName() {
        return "key";
    }
    
    /**
     * @see org.jibx.runtime.IMarshaller#isExtension(java.lang.String)
     */
    public boolean isExtension(String mapname) {
        return false;
    }

    /**
     * @see org.jibx.runtime.IMarshaller#marshal(java.lang.Object,
     *  org.jibx.runtime.IMarshallingContext)
     */
    public void marshal(Object obj, IMarshallingContext ictx)
        throws JiBXException {
        
        // make sure the parameters are as expected
        if (!(obj instanceof Map)) {
            throw new JiBXException("Invalid object type for marshaller");
        } else if (!(ictx instanceof MarshallingContext)) {
            throw new JiBXException("Invalid object type for marshaller");
        } else {
            
            // start by generating start tag for container
            MarshallingContext ctx = (MarshallingContext)ictx;
            Map<?, ?> map = (Map<?, ?>)obj;
            ctx.startTagAttributes(m_index, m_name).
                attribute(m_index, getSizeAttributeName(), map.size()).
                closeStartContent();
            
            // loop through all entries in map
            Iterator<?> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>)iter.next();
                ctx.startTagAttributes(m_index, getEntryElementName());
                ctx.attribute(m_index, getKeyAttributeName(),
                    entry.getKey().toString());
                ctx.closeStartContent();
                if (entry.getValue() instanceof IMarshallable) {
                    ((IMarshallable)entry.getValue()).marshal(ctx);
                    ctx.endTag(m_index, getEntryElementName());
                } else {
                    throw new JiBXException("Mapped value is not marshallable");
                }
            }
            
            // finish with end tag for container element
            ctx.endTag(m_index, m_name);
        }
    }

    /**
     * @see org.jibx.runtime.IUnmarshaller#isPresent(org.jibx.runtime.IUnmarshallingContext)
     */
    public boolean isPresent(IUnmarshallingContext ctx) throws JiBXException {
        return ctx.isAt(m_uri, m_name);
    }

    /**
     * @see org.jibx.runtime.IUnmarshaller#unmarshal(java.lang.Object,
     *  org.jibx.runtime.IUnmarshallingContext)
     */
    public Object unmarshal(Object obj, IUnmarshallingContext ictx)
        throws JiBXException {
        
        // make sure we're at the appropriate start tag
        UnmarshallingContext ctx = (UnmarshallingContext)ictx;
        if (!ctx.isAt(m_uri, m_name)) {
            ctx.throwStartTagNameError(m_uri, m_name);
        }
        
        // create new hashmap if needed
        int size = ctx.attributeInt(m_uri,
            getSizeAttributeName(), DEFAULT_SIZE);
        
        @SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)obj;
        if (map == null) {
            map = new HashMap<String, Object>(size);
        }
        
        // process all entries present in document
        ctx.parsePastStartTag(m_uri, m_name);
        while (ctx.isAt(m_uri, getEntryElementName())) {
            String key = ctx.attributeText(m_uri, getKeyAttributeName(), null);
            ctx.parsePastStartTag(m_uri, getEntryElementName());
            Object value = ctx.unmarshalElement();
            map.put(key, value);
            ctx.parsePastEndTag(m_uri, getEntryElementName());
        }
        ctx.parsePastEndTag(m_uri, m_name);
        return map;
        
    }
}
