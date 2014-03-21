package com.vteba.web.taglib.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.UrlSupport;

import com.vteba.tm.generic.Page;

public class PageTag extends TagSupport {

	private static final long serialVersionUID = 7605066418666386886L;
	private String beanName = "page";
	private String url;
	private String forms = "queryForm";
	private int number = 5;
	
	@Override
    public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
        Page<?> onePage = (Page<?>)pageContext.getRequest().getAttribute(beanName);
        if (onePage == null) {
        	return SKIP_BODY;
        }
        url = resolveUrl(url, pageContext);
        if (onePage.getResult() != null && onePage.getResult().size() > 0){
        	try {
				//上一页
        		if (onePage.hasPreviousPage()) {
					String preUrl = append(url, "page.pageNo", onePage.getPageNo() - 1);
					preUrl = append(preUrl, "pre", "true");

					writer.print("<a href=\"" + preUrl + "\">上一页</a>&nbsp;");
					if (onePage.getPageNo() > 2 && onePage.hasNextPage()) {
						writer.print("<a href=\"" + append(url, "page.pageNo", 1) + "\">1</a>&nbsp;");
					}
				}
        		//中间页
				long currentPage = onePage.getPageNo();
				long startPage = (currentPage - 2 > 0) ? currentPage - 2 : 1;
				for (int i = 1; i <= number
						&& startPage <= onePage.getTotalPageCount(); startPage++, i++) {
					if (startPage == currentPage) {
						writer.print(startPage + "&nbsp;");
						continue;
					}
					String pageUrl = append(url, "page.pageNo", startPage);

					writer.print("<a href=\"" + pageUrl + "\">" + startPage + "</a>&nbsp;");
				}
				//下一页
				if (onePage.hasNextPage()) {
					String nextUrl = append(url, "page.pageNo", onePage.getPageNo() + 1);

					if (onePage.getTotalPageCount() - onePage.getPageNo() > 2) {
						writer.print("<a href=\""
								+ append(url, "page.pageNo", onePage.getTotalPageCount())
								+ "\">" + onePage.getTotalPageCount() + "</a>&nbsp;");
					}
					writer.print("<a href=\"" + nextUrl + "\">下一页</a>");
				}
				writer.print("&nbsp;(共" + onePage.getTotalRecordCount() + "条记录)<br/>");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }      
		return SKIP_BODY;
	}
	
	/**
	 * 为url加入参数对儿 
	 * @author yinlei
	 * @date 2012-5-1 下午10:05:56
	 */
	private String append(String url, String key, long value) {
        return append(url, key, String.valueOf(value));
    }
    
    /**
     * 为url加入参数对儿
     * @param url url
     * @param key 参数名
     * @param value 参数值
     * @return 处理后的url
     */
    private String append(String url, String key, String value) {
		if (url == null || url.trim().length() == 0) {
			return "";
		}
		if (url.indexOf("?") == -1) {
			url = url + "?" + key + "=" + value;
		} else {
			if (url.endsWith("?")) {
				url = url + key + "=" + value;
			} else {
				url = url + "&amp;" + key + "=" + value;
			}
		}
		return url;
    }
    /**
     * 为url添加上下文环境，如果是登陆用户则还要添加uid参数
     * @param url
     * @param pageContext
     * @return 处理后的url
     * @throws javax.servlet.jsp.JspException
     */
    private String resolveUrl(String url, PageContext pageContext) throws JspException{
        url = UrlSupport.resolveUrl(url, null, pageContext);
        url = url.replaceAll("&page.pageNo=\\d*", "").replaceAll("page.pageNo=\\d*", "").replaceAll("&pre=true", "");
        return url;
    }
    
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getForms() {
		return forms;
	}

	public void setForms(String forms) {
		this.forms = forms;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
