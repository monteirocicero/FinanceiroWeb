package financeiro.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.SessionFactory;

import financeiro.util.HibernateUtil;

/**
 * Servlet Filter implementation class ConexaoHibernateFilter
 */
@WebFilter("*.jsf")
public class ConexaoHibernateFilter implements Filter {
	
	private SessionFactory sf;

    /**
     * Default constructor. 
     */
    public ConexaoHibernateFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			sf.getCurrentSession().beginTransaction();
			chain.doFilter(request, response);
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
		} catch (Throwable e) {
			try {
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable e2) {
				e2.printStackTrace();
			}
			throw new ServletException(e);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		sf = HibernateUtil.getSessionFactory();
	}

}
