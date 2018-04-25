package com.hd.clc.frss.vo.FaceUtilVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FaceVO {
    private String request_id;
    private String image_id;
    private String error_message;
    private String face_token;
    private Double threshold; // 有效值
    private Double value; // 当前值

}


