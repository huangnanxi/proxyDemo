package common;

public class TestServiceImpl implements TestService {

    @Override
    public void saySomething(String str) {
        System.out.println(str);
    }

    public int countInt(int num) {
        return (num++);
    }

}
