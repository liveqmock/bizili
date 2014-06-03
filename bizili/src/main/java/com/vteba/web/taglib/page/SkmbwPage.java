package com.vteba.web.taglib.page;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.vteba.tx.generic.Page;

/**
 * 分页标签实现
 * @author yinlei
 * date 2012-6-6 下午8:47:24
 */
public class SkmbwPage extends TagSupport {
	private static final long serialVersionUID = -3852899396840400257L;
	public static final String PAGE_LANGUAGE = "_PAGE_LANGUAGE_";
    private static Map<String, String> lanMap;//分页标签语言国际化
    private String formName = "queryForm";//form 名字
    private boolean hasForm = true;//jsp页面是否有form,默认有
    private String beanName;//request中page的名字
    
    static {
        lanMap = new HashMap<String, String>();
        lanMap.put("pageBar", "\u5171<span class=\"emph\">{0}</span>\u6761\u8BB0\u5F55&nbsp;<span class=\"emph\" id='totalPages'>{2}</span>\u9875 &nbsp;\u5F53\u524D\u7B2C<span class=\"emph\" id='curPage'>{3}</span>\u9875\n");
        lanMap.put("first", "\u9996\u9875");
        lanMap.put("pre", "\u4E0A\u9875");
        lanMap.put("next", "\u4E0B\u9875");
        lanMap.put("last", "\u672B\u9875");
        lanMap.put("into", "\u8FDB\u5165");
        lanMap.put("en-pageBar", "All <span class=\"emph\">{0}</span> records , <span class=\"emph\">{1}</span> records per page, <span class=\"emph\">{2}</span> pages &nbsp;current pageno:{3}\n");
        lanMap.put("en-first", "First page");
        lanMap.put("en-pre", "Previous page");
        lanMap.put("en-next", "Next page");
        lanMap.put("en-last", "Last page");
        lanMap.put("en-into", "Enter");
    }
    
	public SkmbwPage() {
		formName = "queryForm";
	}

	public int doStartTag() throws JspException {
		return 0;
	}

    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        String language = (String)request.getSession().getAttribute("_PAGE_LANGUAGE_");
        String lanMap_prx = "";
        boolean isChinese = true;
        if(language != null && "en".equalsIgnoreCase(language)){
            lanMap_prx = "en-";
            isChinese = false;
        }
        JspWriter out = pageContext.getOut();
        String pageURL = request.getRequestURI();
        if(request.getQueryString() != null){
        	pageURL = (new StringBuilder(pageURL)).append("?").append(request.getQueryString()).toString();
        }
            
        Page<?> page = (Page<?>)pageContext.getRequest().getAttribute(beanName);
		int pageNo = 1;
		int pageSize = 10;
		int recordCount = 0;
		int pageCount = 0;
		if (page != null) {
			pageNo = (int)page.getPageNo();
			pageSize = page.getPageSize();
			pageCount = (int) page.getTotalPageCount();
			recordCount = (int) page.getTotalRecordCount();
		}
		int prevPage = pageNo - 1;
		int nextPage = pageNo + 1;
        if(prevPage < 1)
            prevPage = 1;
        if(nextPage > pageCount)
            nextPage = pageCount;
        try {
            if(!hasForm) {//jsp页面没有form
                out.println((new StringBuilder("<form name=\"")).append(formName).append("\" method=\"post\" action=\"").append(pageURL).append("\">").toString());
            }
            MessageFormat form = new MessageFormat((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("pageBar").toString()));
            out.println(form.format(((Object) (new Object[] {
                (new StringBuilder(String.valueOf(recordCount))).toString(), (new StringBuilder(String.valueOf(pageSize))).toString(), (new StringBuilder(String.valueOf(pageCount))).toString(), (new StringBuilder()).append(pageNo).toString()
            }))));
            if(page.getPageNo() > 1) {
                out.println((new StringBuilder("<a href=\"javascript:submitPageForm('")).append(formName).append("',1,").append(pageSize).append(")\"><font color=red>[").append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("first").toString())).append("]</font></a>&nbsp;").toString());
                out.println((new StringBuilder("<a href=\"javascript:submitPageForm('")).append(formName).append("',").append(prevPage).append(",").append(pageSize).append(")\"><font color=red>[").append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("pre").toString())).append("]</font></a>&nbsp;").toString());
            } else {
                out.println((new StringBuilder("[")).append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("first").toString())).append("]&nbsp;[").append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("pre").toString())).append("]").toString());
            }
            if(pageNo < pageCount) {
                out.println((new StringBuilder("<a href=\"javascript:submitPageForm('")).append(formName).append("',").append(nextPage).append(",").append(pageSize).append(")\"><font color=red>[").append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("next").toString())).append("]</font></a>&nbsp;").toString());
                out.println((new StringBuilder("<a href=\"javascript:submitPageForm('")).append(formName).append("',").append(pageCount).append(",").append(pageSize).append(")\"><font color=red>[").append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("last").toString())).append("]</font></a>&nbsp;").toString());
            } else {
                out.println((new StringBuilder("[")).append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("next").toString())).append("]&nbsp;[").append((String)lanMap.get((new StringBuilder(String.valueOf(lanMap_prx))).append("last").toString())).append("]").toString());
            }
            out.println("&nbsp;");
            if(isChinese)
                out.println("\u6BCF\u9875");
            out.println((new StringBuilder("<select name='pageSizeChange' onchange=\"javascript:submitPageForm('")).append(formName).append("',").append(pageNo).append(",this.value)\">").toString());
            out.println((new StringBuilder("<option value=\"10\" ")).append(pageSize != 10 ? "" : "selected").append(">10</option>").toString());
            out.println((new StringBuilder("<option value=\"20\" ")).append(pageSize != 20 ? "" : "selected").append(">20</option>").toString());
            out.println((new StringBuilder("<option value=\"30\" ")).append(pageSize != 30 ? "" : "selected").append(">30</option>").toString());
            out.println("</select>");
            if(isChinese)
                out.println("\u6761");
            if (isChinese) {
				StringBuilder sb = new StringBuilder();
				sb.append("<span>&nbsp;<input type=button onclick=\"javascript:submitPageForm('");
				sb.append(formName);
				sb.append("',document.");
				sb.append(formName);
				sb.append(".pageNo.value,");
				sb.append(pageSize);
				sb.append(")\" style=\"cursor:hand\" value=\"\u8F6C\u5230\" size=3 class=\"\">&nbsp;<input type=\"text\" name=\"pageNo\" size=\"3\" value=\"");
				sb.append(pageNo);
				sb.append("\" class=\"input\">\u9875</span>");
				out.println(sb.toString());
            } else {
				StringBuilder sb = new StringBuilder("<span>&nbsp;<input type=button onclick=\"javascript:submitPageForm('");
				sb.append(formName);
				sb.append("',document.");
				sb.append(formName);
				sb.append(".pageNo.value,");
				sb.append(pageSize);
				sb.append(")\" value='Go' size=3 class=\"\">&nbsp;<input type=\"text\" name=\"pageNo\" size=\"3\" value=\"");
				sb.append(pageNo);
				sb.append("\" class=\"input\">page</span>");
						
                out.println(sb.toString());
            }
            if (!hasForm) {//jsp页面没有form
            	out.println("</form>");
            }
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return 6;
    }

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

    public boolean isHasForm() {
		return hasForm;
	}

	public void setHasForm(boolean hasForm) {
		this.hasForm = hasForm;
	}

}
