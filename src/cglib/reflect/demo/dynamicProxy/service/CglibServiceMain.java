package cglib.reflect.demo.dynamicProxy.service;

import common.TestService;
import common.TestServiceImpl;

public class CglibServiceMain {
    public static void main(String[] args) {

        TestService service = new TestServiceImpl();
        TestService poxyService = (TestService) ProxyHandler.getPoxyObject(service);

        System.out.println("\n\nexcute info:\n");
        poxyService.saySomething("Manager Zhou: Hello, GentleSong.");
        poxyService.saySomething("Manager Zhou: you are KXF's dream guy.");
        poxyService.saySomething("Manager Zhou: Are you willing to sacrifice for the happniess of KXF's buddy?");
        poxyService.saySomething("GentleSong: Yes, I am.");

    }
}
