package com.hd.clc.frss.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class FaceSet {
    private int id;
    private String description;
    private String faceset_token;
    private int face_count;
    private Date addTime;
    private Date updateTime;
}
