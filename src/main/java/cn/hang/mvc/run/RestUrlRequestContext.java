package cn.hang.mvc.run;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hang.common.util.StringUtils;
import cn.hang.mvc.RequestContext;
import cn.hang.mvc.common.util.RequestContextConstants;
import cn.hang.mvc.common.util.ServletUtils;

/**
 *  REST风格的URL的支持，将resource变成URL中的一部分，如：http://www.host.com/home/user.do?type=vm&event=login
 * <p>
 * @author hang.gao Initial Created at 2014年5月10日
 * <p>
 */
public class RestUrlRequestContext extends DefaultRequestContext {

    private String resource;
    private String moduleName;
    private Pattern pattern = Pattern.compile("/");
    public RestUrlRequestContext() {
        super();
        extractResourceName();
    }

    private void extractResourceName() {
        String uri = getURI().substring(ServletUtils.getBaseUri().length());
        if (uri.length() <= 1) {
            resource = "";
            moduleName = "";
            return;
        }
        if (uri.startsWith("/")) {
            uri = uri.substring(1);//去掉最前面的'/'
        }
        String[] dir = pattern.split(uri);
        if (dir.length != 2) {
            resource = "";
            moduleName = "";
            return;
        }
        moduleName = dir[0];
        int dot = dir[1].lastIndexOf('.');
        if (dot == -1) {
            resource = dir[1];
        } else {
            resource = dir[1].substring(0, dot);
        }
    }

    public RestUrlRequestContext(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
            RequestContext parent) {
        super(servletRequest, servletResponse, parent);
        extractResourceName();
    }

    public RestUrlRequestContext(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        super(servletRequest, servletResponse);
        extractResourceName();
    }

    public RestUrlRequestContext(RequestContext parent) {
        super(parent);
        extractResourceName();
    }

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public String getRequestModuleName() {
        return moduleName;
    }

    @Override
    public String getActionName() {
        String action = getParameter(RequestContextConstants.ACTION_PARAMETER_NAME);
        if (StringUtils.isEmpty(action)) {
            action = resource + "Action";
        }
        return action;
    }

}
