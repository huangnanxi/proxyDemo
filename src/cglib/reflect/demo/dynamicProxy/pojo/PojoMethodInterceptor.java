package cglib.reflect.demo.dynamicProxy.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class PojoMethodInterceptor implements MethodInterceptor {

    private Map<Object, Object> map = null;

    PojoMethodInterceptor() {
        this.map = new HashMap<Object, Object>();
    }

    @Override
    public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
        Object result = null;

        String methodName = arg1.getName();
        if (methodName.startsWith("get")) {
            String name = methodName.substring(methodName.indexOf("get") + 3);
            return map.get(name);
        } else if (methodName.startsWith("set")) {
            String name = methodName.substring(methodName.indexOf("set") + 3);
            map.put(name, arg2[0]);
            return null;
        } else if (methodName.startsWith("is")) {
            String name = methodName.substring(methodName.indexOf("is") + 2);
            return (map.get(name));
        }

        return result;
    }

}
