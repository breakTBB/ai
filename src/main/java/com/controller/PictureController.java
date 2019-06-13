package com.controller;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.dao.ImageDao;
import com.dao.UserDao;
import com.model.Image;
import com.util.Auth;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author prism17
 * @email 2839296425@qq.com
 * @Date 18/05/2019 13:15
 **/

@RestController
public class PictureController {

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;

    AipImageClassify client = Auth.getClient();
    private static final String filePath = "C:\\Users\\prism\\Desktop\\";

    HashMap<String, String> options = new HashMap<String, String>();

    {
        options.put("baike_num", "5");
    }

    @RequestMapping(value = "picture", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("category") String category,
                         @RequestParam("id_user") String id_user, HttpServletRequest request) {

        Image image = new Image();
        image.setCategory(category);
        image.setUp(0);
        image.setDiss(0);
        image.setCreatedDate(new Date());
        System.out.println("id_user"+ id_user);
        image.setIdUser(Integer.parseInt(id_user));
        image.setUser(userDao.getUserById(Long.parseLong(id_user)));


        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);

        fileName = UUID.randomUUID() + suffixName;
        System.out.println("转换后的名称:" + fileName);
        try {
            StringBuffer ret = new StringBuffer();
            File dest = new File(filePath + fileName);
//            File dest = new File(fileName);
            file.transferTo(dest);

            image.setContent(fileName);

            JSONObject res;
            switch (category) {
                case "植物":
                    res = client.plantDetect(dest.getAbsolutePath(), options);
                    break;
                case "动物":
                    res = client.animalDetect(dest.getAbsolutePath(), options);
                    break;
                case "车辆":
                    res = client.carDetect(dest.getAbsolutePath(), options);
                    break;
                default:
                    res = null;
            }
            System.out.println(res.toString());
            JSONArray items = res.getJSONArray("result");
            String test = items.getJSONObject(0).getString("name");
            image.setTitle(test);
            for (int i = 0; i < items.length(); ++i) {
                String name = items.getJSONObject(i).getString("name");
                double score = items.getJSONObject(i).getDouble("score");
                System.out.println(name + " " + score);
                ret.append(name);
                ret.append("    ");
                ret.append(score);
                ret.append("\n");
            }
            imageDao.addImage(image);
            return ret.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "err";
    }

    @RequestMapping(value = "detect_url", method = RequestMethod.POST)
    @ResponseBody
    public String detect_url(@RequestParam("picUrl") String url, @RequestParam("category") String category,
                             @RequestParam("id_user") String id_user, HttpServletRequest request) {
        Image image = new Image();
        image.setCategory(category);
        image.setUp(0);
        image.setDiss(0);
        image.setCreatedDate(new Date());
        System.out.println("id_user"+ id_user);
        image.setIdUser(Integer.parseInt(id_user));
        image.setUser(userDao.getUserById(Long.parseLong(id_user)));

        String tempName = com.util.Image.writeHttpImgToPath(url, filePath, "jpg");
        String f = filePath + tempName;

        try {
            StringBuffer ret = new StringBuffer();

            image.setContent(tempName);
            System.out.println(category);
            JSONObject res;
            switch (category) {
                case "植物":
                    res = client.plantDetect(f, options);
                    break;
                case "动物":
                    res = client.animalDetect(f, options);
                    break;
                case "车辆":
                    res = client.carDetect(f, options);
                    break;
                default:
                    res = null;
            }
            System.out.println(res.toString());
            JSONArray items = res.getJSONArray("result");
            String test = items.getJSONObject(0).getString("name");
            image.setTitle(test);
            for (int i = 0; i < items.length(); ++i) {
                String name = items.getJSONObject(i).getString("name");
                double score = items.getJSONObject(i).getDouble("score");
                ret.append(name);
                ret.append("    ");
                ret.append(score);
                ret.append("\n");
            }
            imageDao.addImage(image);
            String r = ret.toString();
            System.out.println("r:\n" + r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "idon'tknow";
    }
}
