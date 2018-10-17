package IntegrationTest;

import org.junit.jupiter.api.Test;

public class test {

    @Test
    public void test() {
        double d = 12.5;
        d = Math.ceil(d);
        int i = (int) d;
        System.out.println(Integer.toString(i));
    }
}
