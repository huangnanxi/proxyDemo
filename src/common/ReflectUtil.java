package common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

    /**
     * 获取对象的属性值，public(ps:非public的会报错)
     * 
     * @param owner 对象实例
     * @param fieldName 属性名称
     * @return
     * @throws Exception
     */
    public static Object getProperty(Object owner, String fieldName) throws Exception {
        Class<? extends Object> ownerClass = owner.getClass();
        Field field = ownerClass.getDeclaredField(fieldName);
        Object property = field.get(owner);
        return property;
    }

    /**
     * 获取对象的属性值，public和非public
     * 
     * @param owner 对象实例
     * @param fieldName 属性名称
     * @return
     * @throws Exception
     */
    public static Object getAccessProperty(Object owner, String fieldName) throws Exception {
        Class<? extends Object> ownerClass = owner.getClass();
        // 1.通过getDeclaredField获取非public和public
        Field field = ownerClass.getDeclaredField(fieldName);
        // 2.开启访问到原本是private域
        field.setAccessible(true);
        Object property = field.get(owner);
        return property;
    }

    /**
     * 获取指定类的静态public属性值。不涉及非public。否则会报错
     * 
     * @param className 例如java.lang.XXX
     * @param fieldName 属性名称
     * @return
     * @throws Exception
     */
    public static Object getStaticProperty(String className, String fieldName) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Field field = ownerClass.getField(fieldName);
        Object property = field.get(ownerClass);
        return property;
    }

    /**
     * 获取指定类的静态public和非public属性值
     * 
     * @param className 例如java.lang.XXX
     * @param fieldName 属性名称
     * @return
     * @throws Exception
     */
    public static Object getStaticAccessProperty(String className, String fieldName) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Field field = ownerClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object property = field.get(ownerClass);
        return property;
    }

    /**
     * 执行对象的public方法，非public会出错
     * 
     * @param owner 对象
     * @param methodName 方法名称
     * @param args 参数列表
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class<? extends Object> ownerClass = owner.getClass();
        Class[] argsClass = null;
        Method method = null;
        try {
            argsClass = new Class[args.length];
        } catch (Exception e) {
            // 方法无参数的情况
            method = ownerClass.getMethod(methodName);
            return method.invoke(owner);
        }

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(owner, args);
    }

    /**
     * 执行对象的public方法+非public
     * @param owner 对象
     * @param methodName 方法名称
     * @param args 参数列表
     * @return
     * @throws Exception
     */
    public static Object invokeAccessMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class<? extends Object> ownerClass = owner.getClass();
        Class[] argsClass = null;
        Method method = null;
        try {
            argsClass = new Class[args.length];
        } catch (Exception e) {
            // 方法无参数的情况
            // 1.获取public+非public
            method = ownerClass.getDeclaredMethod(methodName);
            // 2.开启访问到原本是private方法
            method.setAccessible(true);
            return method.invoke(owner);
        }

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    /**
     * 执行某类的静态方法public+非public的,非静态的会报错
     * @param className 例如java.lang.XXX
     * @param methodName 方法名称
     * @param args 参数列表
     * @return
     * @throws Exception
     */
    public static Object invokeStaticAccessMethod(String className, String methodName, Object[] args) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Class[] argsClass = null;
        Method method = null;
        try {
            argsClass = new Class[args.length];
        } catch (Exception e) {
            method = ownerClass.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method.invoke(null);
        }
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        method = ownerClass.getDeclaredMethod(methodName, argsClass);
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    /**
     * 执行某类的静态方法public 非public的会报错,非静态的也会报错
     * @param className 例如java.lang.XXX
     * @param methodName 方法名称
     * @param args 参数列表
     * @return
     * @throws Exception
     */
    public static Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
        Class<?> ownerClass = Class.forName(className);
        Class[] argsClass = null;
        Method method = null;
        try {
            argsClass = new Class[args.length];
        } catch (Exception e) {
            method = ownerClass.getMethod(methodName);
            return method.invoke(null);
        }
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(null, args);
    }

    /**
     * 实例化public构造函数
     * @param className
     * @param args
     * @return
     * @throws Exception
     */
    public static Object newInstance(String className, Object[] args) throws Exception {
        Class<?> newoneClass = Class.forName(className);
        Class[] argsClass = null;
        Constructor<?> cons = null;
        try {
            argsClass = new Class[args.length];
        } catch (Exception e) {
            cons = newoneClass.getConstructor();
            return cons.newInstance();
        }
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        cons = newoneClass.getConstructor(argsClass);
        return cons.newInstance(args);
    }

    /**
     * 实例化public+非public构造函数
     * @param className
     * @param args
     * @return
     * @throws Exception
     */
    public static Object newAccessInstance(String className, Object[] args) throws Exception {
        Class<?> newoneClass = Class.forName(className);
        Class[] argsClass = null;
        Constructor<?> cons = null;
        try {
            argsClass = new Class[args.length];
        } catch (Exception e) {
            cons = newoneClass.getDeclaredConstructor();
            cons.setAccessible(true);
            return cons.newInstance();
        }
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        cons = newoneClass.getDeclaredConstructor(argsClass);
        cons.setAccessible(true);
        return cons.newInstance(args);
    }

    /**
     * 判断是否是一个类的实例
     * @param obj
     * @param cls
     * @return
     */
    public static boolean isInstance(Object obj, Class<?> cls) {
        return cls.isInstance(obj);
    }
}