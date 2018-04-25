package com.hd.clc.frss.db.impl;

import com.hd.clc.frss.db.entity.Invitation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationMapper {
    int add(Invitation invitation);
    Invitation queryByCode(@Param("code") String code);
    int update(Invitation invitation);
}
