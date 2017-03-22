package jdk.reflect.demo.dynamicProxy.pojo;

import common.IExample;
import common.IUser;

public class JdkPojoMain {

    public static void main(String[] args) {
        IExample example = (IExample) ProxyHandler.newInstance(new Class[] { IExample.class });

        IUser user = (IUser) ProxyHandler.newInstance(new Class[] { IUser.class });

        // aduit bean 1
        example.setName("my example");
        example.setDesc("my proxy example");
        // aduit bean 2
        user.setUserID("jia20003");
        user.setUserName("gloomyfish");

        System.out.println("proxy info:");
        System.out.println("    IExample: " + example.getClass().toString());
        System.out.println("    IUser: " + user.getClass().toString());

        System.out.println("exmaple data:");
        System.out.println("    exmaple name : " + example.getName());
        System.out.println("    exmaple desc : " + example.getDesc());

        System.out.println("user data:");
        System.out.println("    user ID : " + user.getUserID());
        System.out.println("    user name : " + user.getUserName());
    }

}
