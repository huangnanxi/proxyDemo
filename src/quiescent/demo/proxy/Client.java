package quiescent.demo.proxy;

import common.TestService;
import common.TestServiceImpl;

public class Client {
    public static void main(String[] args) {
        TestService proxy = new ProxySubject(new TestServiceImpl());
        proxy.saySomething("Hello buddy");
    }
}
