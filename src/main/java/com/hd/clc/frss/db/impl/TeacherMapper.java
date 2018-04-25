package com.hd.clc.frss.db.impl;

import com.hd.clc.frss.db.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper {
    int add(Teacher teacher);
    Teacher queryById(@Param("id") int id);
}
