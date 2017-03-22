package quiescent.demo.proxy;

import java.util.Date;

import common.TestService;

public class ProxySubject implements TestService {

    // 代理类持有一个委托类的对象引用
    private TestService testService;

    public ProxySubject(TestService testService) {
        this.testService = testService;
    }

    /**
     * 将请求分派给委托类执行，记录任务执行前后的时间，时间差即为任务的处理时间
     * 
     * @param taskName
     */
    @Override
    public void saySomething(String something) {

        Date startDate = new Date();
        System.out.println("开始调用目标类时时间点：" + startDate);

        // 将请求分派给委托类处理
        testService.saySomething(something);

        Date endDate = new Date();
        System.out.println("结束调用目标类时时间点：" + endDate);
    }

    @Override
    public int countInt(int num) {
        return testService.countInt(num);
    }

}
