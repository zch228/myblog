package com.alibaba.stream;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streamtest {
    public static void main(String[] args) {

//        使用平时的方法来筛选性别为1的人
     /*   ArrayList list1 = new ArrayList<>();
        for (User user : list) {
            if (user.getSex() == 1) {
                list1.add(user);
            }
        }*/
//        使用stream流来操作集合筛选性别为1的人
//        List<User> list2 = list.stream().filter(user -> user.getSex() == 1).collect(Collectors.toList());
//        discont();
//        sort();
//limit();
//        skip();
//        anymatch();
//        allmatch();
//        nonematch();
        sum_avg();
    }

    /**
     * create
     * @return
     */
    public static List<User> users(){
        return Stream.of(new User("李星云", 12, 0, "渝州", new BigDecimal(1000)),
                new User("陆林轩", 16, 1, "渝州", new BigDecimal(500)),
                new User("姬如雪", 17, 1, "幻音坊", new BigDecimal(800)),
                new User("袁天罡", 99, 0, "藏兵谷", new BigDecimal(100000)),
                new User("张子凡", 19, 0, "天师府", new BigDecimal(900)),
                new User("陆佑劫", 45, 0, "不良人", new BigDecimal(600)),
                new User("张天师", 48, 0, "天师府", new BigDecimal(1100)),
                new User("张天师", 48, 0, "天师府", new BigDecimal(1100)),
                new User("张天师", 48, 0, "天师府", new BigDecimal(1100)),
                new User("张天师", 48, 0, "天师府", new BigDecimal(1100)),
                new User("蚩梦", 18, 1, "万毒窟", new BigDecimal(800))).collect(Collectors.toList());
    }

    /**
     * 去重
     */
    public static void discont() {
        List<User> users = users();
        System.out.println(users.stream().distinct().collect(Collectors.toList()));
    }


    /**
     * 排序
     */
    public static void sort() {
        List<User> users = users();
        List<User> collect = users.stream().sorted(Comparator.comparingInt(user -> user.getAge())).distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 过滤
     */
    public static void filter(){
        List<User> list = users();
        List<User> newlist = list.stream().filter(user -> user.getAge() > 20)
                .collect(Collectors.toList());
        for (User user : newlist) {
            System.out.println(user.getName()+" --> "+ user.getAge());
        }
    }

    /**
     * 使用limit返回指定个数的元素
     */
    public static void limit(){
        List<User> collect = users().stream().distinct().limit(3).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 使用skip跳过指定个数的元素
     */
    public static void skip(){
        List<User> collect = users().stream().distinct().skip(3).collect(Collectors.toList());
        System.out.println(collect);
    }

    /*map(T->R)*/

    /**
     * 选出集合对象中的单个属性组成一个新的集合
     */
    public static void map(){
        List<User> list = users();
        List<String> newlist = list.stream()
                .map(user->user.getName()).distinct().collect(Collectors.toList());
        System.out.println(newlist);
    }
    public static void anymatch() {
        List<User> users = users();
        boolean b = users.stream().anyMatch(user -> user.getAge() > 10);
        System.out.println(b);
    }
    public static void allmatch() {
        List<User> users = users();
        boolean b = users.stream().allMatch(user -> user.getAge() > 10);
        System.out.println(b);
    }
    public static void nonematch() {
        List<User> users = users();
        boolean b = users.stream().noneMatch(user -> user.getAge() >100);
        System.out.println(b);
    }
    public static void sum_avg(){
        List<User>list = users();
        int totalAge = list.stream()
                .collect(Collectors.summingInt(User::getAge));
        System.out.println("totalAge--> "+ totalAge);

        double avgAge = list.stream()
                .collect(Collectors.averagingInt(User::getAge));
        System.out.println("avgAge--> " + avgAge);
    }
}
@Data
class User {
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private Integer sex;
    //地址
    private String address;
    //赏金
    private BigDecimal money;

    public User(String name, Integer age, Integer sex, String address,BigDecimal money) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", money=" + money +
                ", address='" + address + '\'' +
                '}';
    }
}
