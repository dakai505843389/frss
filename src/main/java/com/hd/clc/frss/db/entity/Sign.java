package com.hd.clc.frss.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Sign {
    private int id;
    private int studentId;
    private int courseId;
    private Date signTime;
}
