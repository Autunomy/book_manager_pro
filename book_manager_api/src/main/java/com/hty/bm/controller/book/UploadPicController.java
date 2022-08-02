package com.hty.bm.controller.book;

import com.alibaba.fastjson.JSON;
import com.hty.bm.constant.ResponseMessage;
import com.hty.bm.entity.vo.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

//上传图片的controller
@WebServlet("/book/uploadPic")
@MultipartConfig
public class UploadPicController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response response = null;
        try {
            Part pic = req.getPart("pic");
            //判断资源目录是否存在
            String realPath = getServletContext().getRealPath("/images");
            File file = new File(realPath);
            if (!file.exists()) {//不存在就创建
                file.mkdirs();
            }
            String uploadFileName = pic.getSubmittedFileName();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //文件名应该是最后一个点开始到后面
            String fileName = uuid + uploadFileName.substring(uploadFileName.lastIndexOf('.'));
            pic.write(file.getPath() + "/" + fileName);
            HashMap<String, String> picMap = new HashMap<>();
            picMap.put("pic_path", fileName);
            response = new Response(ResponseMessage.SUCCESS).data(picMap);
        } catch (Exception e) {
            response = new Response(ResponseMessage.ERROR);
            e.printStackTrace();
        }
        resp.getWriter().write(JSON.toJSONString(response));
    }
}
