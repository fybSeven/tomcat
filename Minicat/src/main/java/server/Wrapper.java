package server;

/**
 * @AUTHOR: yb.feng
 * @DATE: 2020/5/7 15:50
 * @DESC: /lagou
 */
public class Wrapper {

    private String beanName;

    private Object servlet;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Object getServlet() {
        return servlet;
    }

    public void setServlet(Object servlet) {
        this.servlet = servlet;
    }

    public Wrapper(String beanName, Object servlet) {
        this.beanName = beanName;
        this.servlet = servlet;
    }
}
