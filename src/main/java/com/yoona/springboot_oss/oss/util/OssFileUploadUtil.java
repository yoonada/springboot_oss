package com.yoona.springboot_oss.oss.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.yoona.springboot_oss.common.enums.CommonEnums;
import com.yoona.springboot_oss.common.response.SystemResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import static com.yoona.springboot_oss.common.constants.OssConstants.*;
/**
 * @Email: m15602498163@163.com
 * @Author: yoonada
 * @Date: 2020/8/27
 * @Time: 11:12 上午
 * @Msg:
 */
@Component
public class OssFileUploadUtil {

    private static final String BMP =".bmp";
    private static final String JPG =".jpg";
    private static final String JPEG =".jpeg";
    private static final String GIF =".gif";
    private static final String PNG =".png";
    private static final String HTML =".html";
    private static final String TXT =".txt";
    private static final String VSD =".vsd";
    private static final String PPTX =".pptx";
    private static final String PPT =".ppt";
    private static final String DOCX =".docx";
    private static final String DOC =".doc";
    private static final String XML =".xml";
    private static final String PDF =".pdf";

    /**
     * 文件的类型
     */
    private static final String[] FILE_TYPE = new String[]{BMP,JPG,JPEG,GIF,PNG,HTML,TXT,VSD,PPTX,PPT,DOCX,DOC,XML,PDF};

    /**
     * OSS上的文件夹名字
     */
    private static final String FILE_PATH = "file";

    /**
     * 上传文件并返回一个Url
     * @param file
     * @return
     */
    public SystemResponse uploadFileReturnUrl(MultipartFile file) {
        // 校验文件的格式
        boolean isLegal = false;
        for (String type : FILE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        // 文件格式不正确
        if (!isLegal) {
            SystemResponse.response(CommonEnums.UPLOADED_FORMAT_IS_ILLEGAL);
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String str = file.getOriginalFilename();
        int index = str != null ? str.indexOf(".") : 0;
        String newStr = str.substring(index);
        objectMetadata.setContentType(getContentType(newStr));
        OSSClient client = getOssClient();
        String dateStr = getDateStr();
        // 设置文件路径和名称
        String fileUrl = FILE_PATH + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getOriginalFilename());
        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, fileUrl, new ByteArrayInputStream(file.getBytes()), objectMetadata));
        } catch (Exception e) {
            e.printStackTrace();
            return SystemResponse.response(CommonEnums.UPLOAD_FAILED);
        }
//        文件URL
        String finalFileUrl = "https://" + URL_PREFIX + "/" + fileUrl;
        return SystemResponse.response(CommonEnums.UPLOAD_SUCCESSFULLY, finalFileUrl);

    }

    /**
     * 获取当前时间
     * @return
     */
    private String getDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * 获取一个Oss客户端
     * @return
     */
    private OSSClient getOssClient() {
        return new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 校验文件的类型
     * @param fileType
     * @return
     */
    public static String getContentType(String fileType) {
        if (BMP.equalsIgnoreCase(fileType)) {
            return "image/bmp";
        }
        if (GIF.equalsIgnoreCase(fileType)) {
            return "image/gif";
        }
        if (JPEG.equalsIgnoreCase(fileType) ||
                JPG.equalsIgnoreCase(fileType) ||
                PNG.equalsIgnoreCase(fileType)) {
            return "image/jpg";
        }
        if (HTML.equalsIgnoreCase(fileType)) {
            return "text/html";
        }
        if (TXT.equalsIgnoreCase(fileType)) {
            return "text/plain";
        }
        if (VSD.equalsIgnoreCase(fileType)) {
            return "application/vnd.visio";
        }
        if (PPTX.equalsIgnoreCase(fileType) ||
                PPT.equalsIgnoreCase(fileType)) {
            return "application/vnd.ms-powerpoint";
        }
        if (DOCX.equalsIgnoreCase(fileType) ||
                DOC.equalsIgnoreCase(fileType)) {
            return "application/msword";
        }
        if (XML.equalsIgnoreCase(fileType)) {
            return "text/xml";
        }
        if (PDF.equalsIgnoreCase(fileType)){
            return "application/pdf";
        }
        return "image/jpg";
    }
}
