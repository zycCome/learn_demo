package com.javacode2018.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zyc66
 * @date 2024/11/05 15:41
 **/
@Data
@AllArgsConstructor
public class Person {

    private String name;

    private int age;


    public void initial() {
        System.out.println("Person initial");
    }
}
