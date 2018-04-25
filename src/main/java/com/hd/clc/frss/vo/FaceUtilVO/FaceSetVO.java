package com.hd.clc.frss.vo.FaceUtilVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FaceSetVO {
    private String request_id; // 用于区分每一次请求的唯一的字符串
    private String faceset_token; // FaceSet 的标识
    private String outer_id; // 用户自定义的 FaceSet 标识，如果未定义则返回值为空
    private int face_added; // 成功加入 FaceSet 的 face_token 数量
    private int face_count; // 操作结束后 FaceSet 中的 face_token 总数量
    private String error_message; // 当请求失败时才会返回此字符串，否则此字段不存在
    private String reason;
}
