package com.xqbase.etcd4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.*;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.EnumSet;
import java.util.concurrent.ExecutionException;

/**
 * Add Timeout Test Case.
 *
 * @author Tony He
 */
public class EtcdTimeoutTest {

    private static final int PORT = 56789;
    private static final int TIMEOUT = 10 * 1000;
    private static final int MAX_ALLOWED_TIMEOUT = 12 * 1000;

    private static Server server;
    private EtcdClient client;
    private String key = "/timeout";

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(PORT);
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.addFilter(DelayFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        handler.addServlet(TestServlet.class, "/*");
        server.setHandler(handler);
        server.start();
    }

    @Before
    public void setUp() throws Exception {
        this.client = new EtcdClient(URI.create("http://127.0.0.1:" + PORT));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    public void testGet() throws Exception {
        long start = System.currentTimeMillis();
        try {
            this.client.get(key);
        } catch (EtcdClientException e) {
            validateTestResult(e, start);
        }
    }

    @Test
    public void testSet() throws Exception {
        long start = System.currentTimeMillis();
        try {
            this.client.set(key, "TestValue");
        } catch (EtcdClientException e) {
            validateTestResult(e, start);
        }
    }

    @Test
    public void testListChildren() throws Exception {
        long start = System.currentTimeMillis();
        try {
            this.client.listDir(key, true);
        } catch (EtcdClientException e) {
            validateTestResult(e, start);
        }
    }

    private void validateTestResult(EtcdClientException e, long start) {
        long cost = System.currentTimeMillis() - start;
        Assert.assertTrue(cost >= TIMEOUT && cost <= MAX_ALLOWED_TIMEOUT);
        Exception cause = (Exception) e.getCause();
        Assert.assertTrue(cause instanceof ExecutionException);
        Assert.assertTrue(cause.getCause() instanceof SocketTimeoutException);
    }

    public static class DelayFilter implements Filter {

        private static int DELAYED_TIME = MAX_ALLOWED_TIMEOUT;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            try {
                Thread.sleep(DELAYED_TIME);
            } catch (InterruptedException e) {
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }

        @Override
        public void destroy() {
        }
    }

    public static class TestServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getOutputStream().write("Test".getBytes());
        }
    }
}
