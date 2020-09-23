package com.zsy.domain.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zsy.domain.ARequest;
import com.zsy.enums.SomeEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class MainTest {

    StringBuilder sb = new StringBuilder("Hello world!");
    private Pattern pattern = Pattern.compile("[\\w_-]+");

    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
    }

    public void replaceSb() {
        sb.replace(0, sb.length(), "你好");
    }

    public Pattern namePattern = Pattern.compile("");

    @Test
    public void replaceSbTest() {
        System.out.println(sb);
        replaceSb();
        System.out.println(sb);
    }

    @Test
    public void calendarTest() {
        Date date = new Date();
        System.out.println(date.getTime());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        System.out.println(calendar.getTimeInMillis());
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);
        System.out.println(calendar.getTime());
        System.out.println(calendar.getTimeInMillis());
    }

    @Test
    public void arrTest() {
        char[] chars = new char[4];
        Arrays.fill(chars, '*');
        System.out.println(new String(chars));
    }

    @Test
    public void streamTest() {
        IntStream.range(0, 10).boxed().parallel().forEach(System.out::println);
        System.out.println("结束");
        List<Integer> list = IntStream.range(0, 1000).boxed().collect(Collectors.toList());
        log.info("{}", list.toString());
        list.parallelStream().filter(i -> i < 500);
        log.info("{}", list);
        list = list.parallelStream().filter(i -> i < 500).collect(Collectors.toList());
        log.info("{}", list);
    }

    @Test
    public void switchTest() {
        String s = "apple";
        switch (s) {
            case "apple":
                System.out.println("apple");
                break;
            case "banana":
                System.out.println("banana");
                break;
            default:
                System.out.println("peach");
                break;
        }
    }

    @Test
    public void jsonTest() {
        SomeEnum someEnum1 = SomeEnum.getSomeEnum("type1");
        SomeEnum someEnum2 = SomeEnum.getSomeEnum("type2");
        String enum1JsonString = JSON.toJSONString(someEnum1);
        SomeEnum jsonSomeEnum1 = JSON.parseObject(enum1JsonString, SomeEnum.class);
        System.out.println(jsonSomeEnum1.getName());
    }

    @Test
    public void integerTest() {
        Integer i = new Integer(10000);
        System.out.println(i > 9999);
    }

    @Test
    public void finalNameTest() {
        System.out.println(new ARequest().getName());
    }

    @Test
    public void exampleTest() {
        System.out.println(Optional.ofNullable(null).isPresent());
    }

    @Test
    public void leapYearTest() {
        Calendar s = Calendar.getInstance();
        List<Integer> res = IntStream.range(0, 10000).boxed().parallel().filter(year -> (year % 4 == 0 && year % 400 != 0) || year % 100 == 0).collect(Collectors.toList());
        Calendar e = Calendar.getInstance();
        System.out.println(e.getTimeInMillis() - s.getTimeInMillis());
        System.out.println(res);
        System.out.println();
    }

    @Test
    public void patternTest() {
        StringBuilder sb = new StringBuilder("dhkjahfkjasjkdfddbank");
        String test = null;
        Matcher matcher = pattern.matcher("..ewrew");
        System.out.println(matcher.matches());
        System.out.println("99a1ec3618b276e1659f77e4eb9d2c352f0dcbee2f0bef3cf42fd27c4ec8b58e".length());
        Pattern robotUrlPatternPattern = Pattern.compile("https://oapi.dingtalk.com/robot/send\\?access_token=[0-9a-z]{64}");
        System.out.println(robotUrlPatternPattern.matcher("https://oapi.dingtalk.com/robot/send?access_token=99a1ec3618b276e1659f77e4eb9d2c352f0dcbee2f0bef3cf42fd27c4ec8b58e").matches());
        Pattern workNumPattern = Pattern.compile("[AY][0-9]{7}");
        System.out.println(workNumPattern.matcher("Y10191013").matches());
    }

    @Test
    public void exceptionTest() {
        Exception exception = new NullPointerException();
        System.out.println(exception.getClass().getName());
    }

    @Test
    public void streamTest2() {
        log.info("{}", Lists.newArrayListWithCapacity(0).parallelStream().
                filter(o -> o.toString().length() > 0)
                .collect(Collectors.toList()));
        List<String> list1 = Lists.newArrayList("1", "2", "3");
        List<String> list2 = Lists.newArrayList("4", "5", "6");
        List<List<String>> lists = Lists.newArrayList(list1, list2);
        log.info(lists.parallelStream()
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList()));
        String s = "hello world";
    }

    @Test
    public void exceptionTest2() {
        try {
            List<Integer> list = Lists.newArrayList(1, 2, 3, 4, null);
            list.stream().forEach(i -> {
                try {
                    log.info(division(i));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            log.info("有null值");
        }
    }

    private int division(Integer i) throws Exception {
        if (i == null) {
            throw new Exception("null错误");
        }
        return 20 / i.intValue();
    }

    @Test
    public void patternTest2() {
        String regex = "[^0-9-]+";
        String preffix = "^([^0-9-]+.*)";
        String param = "1231 45231 123 1213124  4123";
        param = param.trim();
        Long startPayTradeNo = null;
        Long endPayTradeNo = null;
        List<Long> payTradeNos = null;
        if (StringUtils.isBlank(param)) {
            startPayTradeNo = -1L;
        } else {
            String[] params = param.split(regex);
            if (param.matches(preffix) && params.length == 3) {
                try {
                    startPayTradeNo = Long.parseLong(params[1]);
                    long param2 = Long.parseLong(params[2]);
                    if (param2 != -1L) {
                        endPayTradeNo = param2;
                    }
                } catch (Exception e) {
                    log.info("参数输入错误");
                    throw e;
                }
            } else {
                payTradeNos = Arrays.stream(params).parallel()
                        .map(this::string2Long)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        }
        log.info("{}:{}:{}", startPayTradeNo, endPayTradeNo, payTradeNos);
    }

    private Long string2Long(String s) {
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void string2LongTest() {
        log.info("{}", string2Long("0123"));
    }

    @Test
    public void jsonTest2() {
        JSONArray arr = JSON.parseArray("[\"菜鸟教程\",\"RUNOOB\"]\n");
        arr.stream().forEach(log::info);
    }

    @Test
    public void forTest() {
        String[] strs = new String[]{"hello", "world"};
        for (String s : strs)
            if (s != null || s.length() < 0) {
                s += "111";
                System.out.println(s);
            }
    }

    @Test
    public void labelTest() {
        label:
        {
            try {
                System.out.println(1);
                int i = 1 / 0;
                break label;
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(2);
        }
    }

    @Test
    public void threadTest() {
        int[] sum = new int[1];
        IntStream.range(0, 1000000).boxed().parallel().forEach(i -> sumAdd(sum));
        System.out.println(sum[0]);
    }

    private static synchronized void sumAdd(int[] sum) {
        sum[0]++;
    }

    @Test
    public void intStreamTest() {
        List<Integer> list = IntStream.range(0, 0).boxed().parallel().collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void containsNullTest() {
        Set<Integer> set = Sets.newHashSet();
        System.out.println(set.contains(null));
    }

    @Test
    public void nullCompareNum() {
        Integer i = null;
        System.out.println(i < 1);
    }

}
