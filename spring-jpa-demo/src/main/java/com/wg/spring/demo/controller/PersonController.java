package com.wg.spring.demo.controller;

import com.wg.spring.demo.dao.PersonRepository;
import com.wg.spring.demo.po.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wanggang
 * @Date: 2018/10/10 17:35
 * @todo  ddd
 */
@RestController
@RequestMapping(value = "person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("hello1")
    public String hello(){
        return "sdfsdfasfsafasd123adsad";
    }
    @PostMapping(path = "addPerson1")
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @DeleteMapping(path = "deletePerson")
    public void deletePerson(Long id) {
        personRepository.delete(new Person(id));
    }

    @DeleteMapping(path = "deletePerson2")
    public void deletePerson2(Long id) {
        personRepository.delete(new Person(id));
    }


}
