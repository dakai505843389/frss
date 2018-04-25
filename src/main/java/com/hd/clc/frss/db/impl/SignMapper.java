package com.hd.clc.frss.db.impl;

import com.hd.clc.frss.db.entity.Sign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignMapper {
    int sign(Sign sign);
    List<Sign> queryByCourseId(@Param("courseId") int courseId);
}
