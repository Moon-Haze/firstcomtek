package com.firstcomtek.Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.firstcomtek.Utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
/**
 * Servlet Filter implementation class OpenSessionInView
 */
@WebFilter(filterName = "/OpenSessionInView", urlPatterns = "/*")
public class OpenSessionInView implements Filter {

    /**
     * Default constructor.
     */
    public OpenSessionInView() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        SqlSession session = null;
        try {
            session = MyBatisUtils.getSession();
            chain.doFilter(request, response);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSession();
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
