package com.blog.personalblog.controller;

import com.blog.personalblog.utils.AliOssUtil;
import com.blog.personalblog.vo.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class UploadFileController {
    @PostMapping("/upload")
    public Result<String> file(MultipartFile file) throws Exception {
        //获取文件原始文件名字
        String originalFilename = file.getOriginalFilename();
        //写入本地磁盘的位置
        //保证文件名字唯一，uuid实现方式
        String fileName= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("D:\\FileUpload\\"+fileName));
        //aliyunoss方式
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.Success(url);

    }

}
