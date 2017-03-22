package jdk.reflect.demo.dynamicProxy.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;

public class ProxyHandler {
    public static Object getPoxyObject(final Object c) {
        return Proxy.newProxyInstance(c.getClass().getClassLoader(), c.getClass().getInterfaces(), // JDK实现动态代理，但JDK实现必须需要接口
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object reObj = null;
                        reObj = method.invoke(c, args);
                        if (method.getName().equals("saySomething")) {
                            System.out.println("at [" + Calendar.getInstance().get(Calendar.HOUR) + ":"
                                    + Calendar.getInstance().get(Calendar.MINUTE) + ":"
                                    + Calendar.getInstance().get(Calendar.SECOND) + "]\n");
                        }
                        return reObj;
                    }
                });
    }

}
