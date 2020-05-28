package com.alibaba.Build;

import lombok.Builder;

public class Build2 {
    @Builder
    class User2 {
        private String  name;
        private String password;
        private String nickName;
        private int age;
    }

    public static void main(String[] args) {
        User2 build = User2.builder().age(12).name("aaa").nickName("bbb").build();
    }
}

