package com.vteba.user.servlet;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skmbw.user.dao.UserDao;
import com.skmbw.user.model.User;
import com.skmbw.user.model.UserBean;
import com.vteba.user.service.IEmpUserService;
import com.vteba.web.servlet.AutowiredHttpServlet;

/**
 * EmpUser Servlet
 * @author yinlei
 * date 2013-8-9 下午5:14:47
 */
public class EmpUserServlet extends AutowiredHttpServlet {
	private static final long serialVersionUID = -1249792727212356213L;
	private static Logger logger = LoggerFactory.getLogger(EmpUserServlet.class);
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private IEmpUserService empUserServiceImpl;
	
	@Override
	public void servlet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.info(request.getCharacterEncoding());
		
		UserBean userBean = new UserBean();
		userBean.createCriteria().andUserIdIsNotNull().andUserIdEqualTo(2L);
		long d = System.currentTimeMillis();
		List<User> list = userDao.selectByExample(userBean);
		logger.info("size=[{}],time=[{}]", list.size(), (System.currentTimeMillis() - d));
		
		empUserServiceImpl.getEmpUserLists();
		
		//request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		logger.info(name);
		request.setAttribute("name", name);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
