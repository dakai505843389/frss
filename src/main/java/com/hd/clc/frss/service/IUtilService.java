package com.hd.clc.frss.service;

import com.hd.clc.frss.common.Result;
import org.springframework.web.multipart.MultipartFile;

public interface IUtilService {
    Result uploadImage(MultipartFile image);
}
