package com.wg.spring.demo.dao;

import com.wg.spring.demo.po.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: wanggang
 * @Date: 2018/10/10 17:33
 * @todo
 */
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
}
