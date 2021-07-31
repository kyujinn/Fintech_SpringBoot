package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;


//@Builder   // 사용 못하는 기능이 있음
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


//@NoArgsConstructor
//@AllArgsConstructor
@Data
@Entity
public class Member {
    @Id
    @GeneratedValue
    Long seq;
    String id;
    String name;
    String org;
    Boolean active;
}
