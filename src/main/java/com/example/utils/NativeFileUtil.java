package com.example.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**功能描述：上传本地文件
 * 该类提供了上传用户头像的功能，允许用户将本地文件上传到指定路径。
 */
public class NativeFileUtil {

    /**
     * 上传头像
     * @param uploadFile
     *        上传文件，类型为MultipartFile，表示用户选择的文件
     * @param filePath
     *           上传路径，表示文件将被保存到的目录
     * @return 返回新文件名，如果上传成功则返回新生成的文件名，否则返回null
     */
    public static String uploadUserIcon(MultipartFile uploadFile, String filePath) {

        // 获取原始名称
        String oldName = uploadFile.getOriginalFilename();
        // 获取文件大小
        long pictureSize = uploadFile.getSize();
        // 获取文件扩展名
        String fileExtension = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        
        // 检查文件扩展名是否为允许的格式
        if (fileExtension.equals(".png") || fileExtension.equals(".jpg") || fileExtension.equals(".gif")) {
            File folder = new File(filePath);
            // 如果目标文件夹不存在，则创建该文件夹
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            // 生成新的文件名，使用UUID确保文件名唯一
            String newName = UUID.randomUUID().toString() +
                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
            // 文件保存操作
            try {
                uploadFile.transferTo(new File(folder, newName));
            } catch (IOException e) {
                e.printStackTrace(); // 打印异常信息
            }
            return newName; // 返回新文件名
        } else {
            return null; // 返回null表示文件格式不支持
        }
    }
}
