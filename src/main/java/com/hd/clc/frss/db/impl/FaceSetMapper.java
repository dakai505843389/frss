package com.hd.clc.frss.db.impl;

import com.hd.clc.frss.db.entity.FaceSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceSetMapper {
    int add(FaceSet faceSet);
    FaceSet queryById(@Param("id") int id);
    int update(FaceSet faceSet);
}
