package com.hd.clc.frss.db.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Invitation {
    private int id;
    private String code;
    private int inviterId;
    private int belongTeacherId;
    private int status;
}
