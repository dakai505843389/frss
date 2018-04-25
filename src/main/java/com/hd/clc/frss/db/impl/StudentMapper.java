package com.hd.clc.frss.db.impl;

import com.hd.clc.frss.db.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    int add(Student student);
    Student queryByNumber(@Param("number") int number);
    Student queryByFace_Token(@Param("face_token") String face_token);
}
