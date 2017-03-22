package cglib.reflect.demo.dynamicProxy.pojo;

import net.sf.cglib.proxy.Enhancer;

public class ProxyHandler {

    public static Object newInstance(@SuppressWarnings("rawtypes") Class pojoInterface) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(pojoInterface);
        enhancer.setCallback(new PojoMethodInterceptor());
        return enhancer.create();
    }

}
