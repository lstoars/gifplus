package com.lstoars.gifplus.sevlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lstoars.gifplus.constant.GifPlusConstant;
import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.qplus.QPushBean;
import com.lstoars.gifplus.qplus.QPushResult;
import com.lstoars.gifplus.qplus.QPushService;
import com.lstoars.gifplus.result.PageResult;
import com.lstoars.gifplus.service.IUserService;

/**
 * push��Ϣ
 * @author lstoars
 *
 */
public class PushMessageServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 2161982504699279321L;

	private IUserService userService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		String buffer = req.getParameter("buffer");
		String text = req.getParameter("text");
		int pageSize = 10;
		
		if (StringUtils.equals(action, "byOne")) {
			String uid = req.getParameter("uId");
			this.push(uid, buffer, text);
		} else if (StringUtils.equals(action, "all")) {
			int pageNum = 1;

			PageResult<List<UserDO>> result = null;
			do {
				logger.warn("batch push by page:"+pageNum);
				result = userService.queryUser(pageNum, pageSize);
				for (UserDO user : result.getValue()) {
					this.push(user.getPushId(), buffer, text);
				}
				if(pageNum % 10 ==0 ){
					try {
						TimeUnit.SECONDS.sleep(60L);
					} catch (InterruptedException e) {
						logger.warn("sleep error", e);
					}
				}
				pageNum ++;
			} while (result.isSuccess()
					&& result.getCurrentPage() * result.getPageSize() < result
							.getTotalCount());
		} else if (StringUtils.equals(action, "byPage")){
			String pageNumStr = req.getParameter("pageNum");
			String[] p = pageNumStr.split("-");
			int begin = NumberUtils.toInt(p[0], 1);
			int end = NumberUtils.toInt(p[1]);
			for (int i = begin; i <= end; i++) {
				logger.warn("batch push by page:"+i);
				PageResult<List<UserDO>> result = userService.queryUser(i, pageSize);
				for (UserDO user : result.getValue()) {
					this.push(user.getPushId(), buffer, text);
				}
			}
		}
	}

	private void push(String uId, String buffer, String text) {
		final QPushService service = QPushService.createInstance(
				GifPlusConstant.APP_ID, GifPlusConstant.APP_SECRETkEY);
		QPushBean bean = new QPushBean();
		bean.setNum(20); // ��Appָ����һ��չʾ��Appͼ������Ͻǡ����100
		bean.setInstanceid(0); // ����ʵ��ID, ���֣�Ŀǰ������0
		bean.setOptype(1); // չ�ַ�ʽ: 1-��������ֱ�ӽ���Ϣ����
		bean.setQplusid(uId); // ����ID���ַ�����������Ϣ�������ݻᱻУ��
		bean.setCustomize("frompush-0-uId");  //�������Զ���BUFFER  �260�ֽڡ����ֶλ�������App��ʱ��͸����AppӦ�ó���
		bean.setText(text); // �ı���ʾ�� Utf8���룬�90�ֽ�
		bean.setPushmsgid("1"); // ����PUSH����ϢID��������д������Ϊ��������
		try {
			QPushResult result = service.push(bean);
			logger.warn("push result:" + result);
		} catch (IOException e) {
			logger.error("push message error", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		userService = (IUserService) context.getBean("userService");
		super.init(config);
	}

}
