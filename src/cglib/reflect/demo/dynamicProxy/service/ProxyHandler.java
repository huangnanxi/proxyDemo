package cglib.reflect.demo.dynamicProxy.service;

import java.lang.reflect.Method;
import java.util.Calendar;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyHandler {
    public static Object getPoxyObject(Object c) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(c.getClass());
        
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy proxy) throws Throwable {

                proxy.invokeSuper(arg0, arg2);

                if (arg1.getName().equals("saySomething")) {
                    System.out.println("at [" + Calendar.getInstance().get(Calendar.HOUR) + ":"
                            + Calendar.getInstance().get(Calendar.MINUTE) + ":"
                            + Calendar.getInstance().get(Calendar.SECOND) + "]\n");
                }
                
                return null;
            }
        });
        
        return enhancer.create();
    }
}
