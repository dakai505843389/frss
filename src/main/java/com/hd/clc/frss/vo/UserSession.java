package com.hd.clc.frss.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserSession {
    private String sessionId;
    private int isTeacher;
    private int userId;
    private Date addTime;
    private Date outTime;
}
