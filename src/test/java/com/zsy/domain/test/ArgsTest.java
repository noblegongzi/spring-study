package com.zsy.domain.test;

import com.zsy.args.Args;
import com.zsy.args.ArgsException;
import org.junit.Test;

public class ArgsTest extends BaseSpringTest {
    @Test
    public void test() throws ArgsException {
        try {
            Args args = new Args("l,p##,d*", new String[]{"-lpd", "123", "hello"});
            boolean logging = args.getBoolean('l');
            int port = args.getInt('p');
            String directory = args.getString('d');
            System.out.println(String.format("%s_%s_%s", logging, port, directory));
        } catch (ArgsException e) {
            System.out.println(e.errorMessage());
        }
    }
}
