package com.wg.spring.demo.po;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author: wanggang
 * @Date: 2018/10/10 17:28
 * @todo
 */
@Entity

public class Person {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name ="name" ,nullable = true,length = 20)
    private String name;
    @Column(name = "age",nullable = true,length = 4)
    private Integer age;
    public  Person(){}
    public Person(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
