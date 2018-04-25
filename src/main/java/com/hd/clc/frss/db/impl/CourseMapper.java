package com.hd.clc.frss.db.impl;

import com.hd.clc.frss.db.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper {
    int add(Course course);
    Course queryById(@Param("id") int id);
}
