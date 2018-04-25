package com.hd.clc.frss.vo.FaceUtilVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FaceSearchResultVO {
    private String request_id;
    private String error_message;
    private Double e_3;
    private Double e_4;
    private Double e_5;
    private String face_token;
    private Double confidence;
}
