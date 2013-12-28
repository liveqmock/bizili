package com.vteba.web.tiles;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * tiles结果集映射，返回tiles视图
 * @author yinlei 2011-11-30
 *
 */
public class YinleiTilesResult extends ServletDispatcherResult {

    private static final long serialVersionUID = -3806939435493086244L;

    public YinleiTilesResult() {
        super();
    }

    public YinleiTilesResult(String location) {
        super(location);
    }
    /**
     * Dispatches to the given location. Does its forward via a RequestDispatcher. If the
     * dispatch fails a 404 error will be sent back in the http response.
     * @author yinlei 2011-11-30
     * @param location the location to dispatch to.
     * @param invocation    the execution state of the action
     * @throws Exception if an error occurs. If the dispatch fails the error will go back via the
     *                   HTTP request.
     */
    public void doExecute(String location, ActionInvocation invocation) throws Exception {
        setLocation(location);
        ServletContext servletContext = ServletActionContext.getServletContext();
        //TilesContainer container = TilesAccess.getContainer(servletContext);
        //上述方法不推荐使用，推荐使用下面的
        TilesContainer container = ServletUtil.getContainer(servletContext);

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        container.render(location, request, response);
    }
}
