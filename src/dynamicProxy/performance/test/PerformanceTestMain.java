package dynamicProxy.performance.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.TestService;
import common.TestServiceImpl;
import jdk.reflect.demo.dynamicProxy.service.ProxyHandler;
import quiescent.demo.proxy.ProxySubject;

public class PerformanceTestMain {

    public static void main(String[] args) {

        System.out.println(String.format("==================== run test : [java.version=%s] ====================",
                System.getProperty("java.version")));

        // 预热，防止首次加载造成的测试影响
        predo();

        System.out.println("单例测试:\n");
        SingelTestUtil.singleTest(500000, 3);
        System.out.println("\n");
        SingelTestUtil.singleTest(1000000, 3);
        System.out.println("\n\n\n");

        System.out.println("多例测试:\n");
        DuplTestUtil.duplTest(500000, 3);
        System.out.println("\n");
        DuplTestUtil.duplTest(1000000, 3);
        System.out.println("\n\n\n");

        System.out.println("深入寻找差异原因测试：");
        FindReasonTest.findReason(500000, 3);
        System.out.println("\n\n\n");
        FindReasonTest.findReason(1000000, 3);

    }

    /**
     * 预热，防止首次加载造成的测试影响
     */
    private static void predo() {
        for (int i = 0; i < 10000; i++) {
            TestService orgnial = new TestServiceImpl();
            TestService staticProxy = new ProxySubject(orgnial);
            TestService jdkProxy = (TestService) ProxyHandler.getPoxyObject(orgnial);
            TestService cglibProxy = (TestService) cglib.reflect.demo.dynamicProxy.service.ProxyHandler
                    .getPoxyObject(orgnial);

            int num = 1;
            num = orgnial.countInt(num);
            num = staticProxy.countInt(num);
            num = jdkProxy.countInt(num);
            num = cglibProxy.countInt(num);
        }
    }

    /**
     * 单例测试工具类
     */
    private static class SingelTestUtil {

        public static void singleTest(int runCount, int repeatCount) {

            System.out.println("test runCount :" + runCount + "\n");

            Map<String, Long> resultMap = new HashMap<String, Long>();
            resultMap.put("orginal", 0L);
            resultMap.put("static proxy", 0L);
            resultMap.put("jdk proxy", 0L);
            resultMap.put("cglib proxy", 0L);

            for (int i = 0; i < repeatCount; i++) {
                System.out.println("total repeat count is :" + repeatCount + " current is : " + (i + 1));
                resultMap.put("orginal", resultMap.get("orginal") + singleItemTest(runCount, "orginal").get("orginal"));
                resultMap.put("static proxy",
                        resultMap.get("static proxy") + singleItemTest(runCount, "static proxy").get("static proxy"));
                resultMap.put("jdk proxy",
                        resultMap.get("jdk proxy") + singleItemTest(runCount, "jdk proxy").get("jdk proxy"));
                resultMap.put("cglib proxy",
                        resultMap.get("cglib proxy") + singleItemTest(runCount, "cglib proxy").get("cglib proxy"));

                System.out.println("\n");
            }

            for (String key : resultMap.keySet()) {
                System.out.println("execute :" + key + " " + repeatCount + " \'t, average timeCost is : "
                        + resultMap.get(key) / repeatCount);
            }
        }

        private static Map<String, Long> singleItemTest(int runCount, String key) {

            Map<String, Long> resultMap = new HashMap<String, Long>();
            int num = 1;

            long startTime = System.currentTimeMillis();
            TestService nativeTest = new TestServiceImpl();
            TestService exeSetvice = null;

            if ("cglib proxy".equals(key)) {
                exeSetvice = (TestService) cglib.reflect.demo.dynamicProxy.service.ProxyHandler
                        .getPoxyObject(nativeTest);
            } else if ("jdk proxy".equals(key)) {
                exeSetvice = (TestService) ProxyHandler.getPoxyObject(nativeTest);
            } else if ("static proxy".equals(key)) {
                exeSetvice = new ProxySubject(nativeTest);
            } else if ("orginal".equals(key)) {
                exeSetvice = nativeTest;
            }

            for (int i = 0; i < runCount; i++) {
                num = exeSetvice.countInt(num);
            }
            long endTime = System.currentTimeMillis();

            resultMap.put(key, (endTime - startTime));

            System.out.println(" execute " + key + " : " + (endTime - startTime) + " ms");

            return resultMap;
        }
    }

    /**
     * 多例测试工具类
     */
    private static class DuplTestUtil {
        public static void duplTest(int runCount, int repeatCount) {

            System.out.println("test runCount :" + runCount + "\n");

            Map<String, Long> resultMap = new HashMap<String, Long>();
            resultMap.put("orginal", 0L);
            resultMap.put("static proxy", 0L);
            resultMap.put("jdk proxy", 0L);
            resultMap.put("cglib proxy", 0L);

            for (int i = 0; i < repeatCount; i++) {
                System.out.println("total repeat count is :" + repeatCount + " current is : " + (i + 1));
                resultMap.put("orginal", resultMap.get("orginal") + duplItemTest(runCount, "orginal").get("orginal"));
                resultMap.put("static proxy",
                        resultMap.get("static proxy") + duplItemTest(runCount, "static proxy").get("static proxy"));
                resultMap.put("jdk proxy",
                        resultMap.get("jdk proxy") + duplItemTest(runCount, "jdk proxy").get("jdk proxy"));
                resultMap.put("cglib proxy",
                        resultMap.get("cglib proxy") + duplItemTest(runCount, "cglib proxy").get("cglib proxy"));
                System.out.println("\n");
            }
            for (String key : resultMap.keySet()) {
                System.out.println("execute :" + key + " " + repeatCount + " \'t, average timeCost is : "
                        + resultMap.get(key) / repeatCount);
            }
        }

        private static Map<String, Long> duplItemTest(int runCount, String key) {
            int num = 1;
            Map<String, Long> resultMap = new HashMap<String, Long>();

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < runCount; i++) {
                TestService exeSetvice = null;

                if ("cglib proxy".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = (TestService) cglib.reflect.demo.dynamicProxy.service.ProxyHandler
                            .getPoxyObject(nativeTest);
                } else if ("jdk proxy".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = (TestService) ProxyHandler.getPoxyObject(nativeTest);
                } else if ("static proxy".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = new ProxySubject(nativeTest);
                } else if ("orginal".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = nativeTest;
                }

                num = exeSetvice.countInt(num);
            }
            long endTime = System.currentTimeMillis();

            System.out.println(" execute " + key + " : " + (endTime - startTime) + " ms");

            resultMap.put(key, endTime - startTime);
            return resultMap;
        }

    }

    /**
     * 原因深入探索测试工具类
     */
    private static class FindReasonTest {
        public static void findReason(int runCount, int repeatCount) {
            System.out.println("deep test. run Count is :" + runCount);

            final Map<String, Long> resultMap = new HashMap<String, Long>();
            resultMap.put("orginal create", 0L);
            resultMap.put("orginal execute", 0L);
            resultMap.put("static proxy create", 0L);
            resultMap.put("static proxy execute", 0L);
            resultMap.put("jdk proxy create", 0L);
            resultMap.put("jdk proxy execute", 0L);
            resultMap.put("cglib proxy create", 0L);
            resultMap.put("cglib proxy execute", 0L);

            for (int i = 0; i < repeatCount; i++) {
                System.out.println("total repeat count is :" + repeatCount + " current is : " + (i + 1));

                Map<String, Long> tempMap = new HashMap<String, Long>();
                tempMap = findReasonItemTest(runCount, "orginal");
                resultMap.put("orginal create", resultMap.get("orginal create") + tempMap.get("orginal create"));
                resultMap.put("orginal execute", resultMap.get("orginal execute") + tempMap.get("orginal execute"));

                tempMap = findReasonItemTest(runCount, "static proxy");
                resultMap.put("static proxy create",
                        resultMap.get("static proxy create") + tempMap.get("static proxy create"));
                resultMap.put("static proxy execute",
                        resultMap.get("static proxy execute") + tempMap.get("static proxy execute"));

                tempMap = findReasonItemTest(runCount, "jdk proxy");
                resultMap.put("jdk proxy create", resultMap.get("jdk proxy create") + tempMap.get("jdk proxy create"));
                resultMap.put("jdk proxy execute",
                        resultMap.get("jdk proxy execute") + tempMap.get("jdk proxy execute"));

                tempMap = findReasonItemTest(runCount, "cglib proxy");
                resultMap.put("cglib proxy create",
                        resultMap.get("cglib proxy create") + tempMap.get("cglib proxy create"));
                resultMap.put("cglib proxy execute",
                        resultMap.get("cglib proxy execute") + tempMap.get("cglib proxy execute"));

                System.out.println("\n");
            }

            List<String> list = new ArrayList<String>() {
                private static final long serialVersionUID = -574662443316876402L;

                {
                    List<Object> objects = Arrays.asList(resultMap.keySet().toArray());
                    for (Object object : objects) {
                        add((String) object);
                    }
                }
            };
            Collections.sort(list);

            for (int i = 0; i < list.size(); i++) {
                System.out.println("do " + list.get(i) + " " + repeatCount + " \'t, average timeCost is : "
                        + resultMap.get(list.get(i)) / repeatCount);
                if (i % 2 == 1) {
                    System.out.println("");
                }

            }

        }

        private static Map<String, Long> findReasonItemTest(int runCount, String key) {
            TestService exeSetvice = null;

            Map<String, Long> resultMap = new HashMap<String, Long>();

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < runCount; i++) {
                if ("cglib proxy".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = (TestService) cglib.reflect.demo.dynamicProxy.service.ProxyHandler
                            .getPoxyObject(nativeTest);
                } else if ("jdk proxy".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = (TestService) ProxyHandler.getPoxyObject(nativeTest);
                } else if ("static proxy".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = new ProxySubject(nativeTest);
                } else if ("orginal".equals(key)) {
                    TestService nativeTest = new TestServiceImpl();
                    exeSetvice = nativeTest;
                }
            }
            long endTime = System.currentTimeMillis();

            resultMap.put(key + " create", (endTime - startTime));
            System.out.println(" Create " + key + ": " + (endTime - startTime) + " ms");

            int num = 1;
            startTime = System.currentTimeMillis();
            for (int i = 0; i < runCount; i++) {
                exeSetvice.countInt(num);
            }
            endTime = System.currentTimeMillis();
            resultMap.put(key + " execute", (endTime - startTime));
            System.out.println(" execute " + key + ": " + (endTime - startTime) + " ms");
            System.out.println();

            return resultMap;
        }
    }

}
