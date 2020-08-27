package com.yoona.springboot_oss.oss.controller;

import com.yoona.springboot_oss.common.response.SystemResponse;
import com.yoona.springboot_oss.oss.util.OssFileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 11:12 上午
 * @Msg:
 */
@RestController
@RequestMapping("/oss/file")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OssFileUploadController {

    private final OssFileUploadUtil ossFileUploadUtil;

    /**
     * 上传并返回Url
     * @param file
     * @return
     * @throws
     */
    @PostMapping("/uploadFileReturnUrl")
    public SystemResponse uploadFileReturnUrl(@RequestParam MultipartFile file) {
        return ossFileUploadUtil.uploadFileReturnUrl(file);
    }

}
