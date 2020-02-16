package com.shanah.mongotest.mongo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shanah.mongotest.mongo.config.JsonDateValueProcessor;
import com.shanah.mongotest.mongo.dao.ImagesDao;
import com.shanah.mongotest.mongo.dao.UserDao;
import com.shanah.mongotest.mongo.dao.UserFavouriteDao;
import com.shanah.mongotest.mongo.model.*;
import com.shanah.mongotest.mongo.service.CheckFileNameExist;
import com.shanah.mongotest.mongo.service.FindUserAllfavPoster;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ApiController {
    @Autowired
    private ImagesDao imagesDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FindUserAllfavPoster findUserAllfavPoster;
    @Autowired
    private UserFavouriteDao userFavouriteDao;
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/find"})
    @ResponseBody
    public JSONObject findone(@RequestBody PosterName posterName){
    JSONObject jsonObject = new JSONObject();
    Images images = imagesDao.findOneByFileName(posterName.getPoster_name());
    jsonObject.put("data",images);
    return  jsonObject;
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/findUserByKeyWord","/admin/findUserByKeyWord"})
    @ResponseBody
    public JSONObject findUserByKeyWord(@RequestBody Keyword keyWord){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());// 注入处理Date类

        List<User>list = userDao.findUserBykeyWord(keyWord.getKeyword());
        JSONArray jsonArray;
        jsonArray = JSONArray.fromObject(list,jsonConfig);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",list.size());
        jsonObject.put("data",jsonArray);
        return jsonObject;
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @GetMapping(value = {"/api/allPoster","/admin/allPoster"})
    @ResponseBody
    public JSONObject allPoster(){
        List<Images> list = imagesDao.findAllPoster();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());// 注入处理Date类

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
        jsonObject.put("data",jsonArray);
        JSONArray jsonArray2 = new JSONArray();
        for(Images image:list){
            JSONObject jsonObject2 = new JSONObject();
            String postername = image.getPoster_name();
            jsonObject2.put(postername,imagesDao.posterVeiwTime(postername));
            jsonArray2.add(jsonObject2);
        }
        jsonObject.put("count",list.size());
        jsonObject.put("viewTime",jsonArray2);
        return jsonObject;
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/admin/updatePoster_receive","/api/updatePoster_receive"})
    @ResponseBody
    public JSONObject updatePoster(@RequestBody PosterName posterName){
        JSONObject jsonObject = new JSONObject();
        Images images = imagesDao.findOneByFileName2(posterName.getPoster_name());
        if(images!=null){
            jsonObject.put("data",images);
            jsonObject.put("error_code","200");
            return jsonObject;
        }
        jsonObject.put("error_code","400");
        return jsonObject;
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/admin/updatePoster_send","/api/updatePoster_send"})
    @ResponseBody
    public JSONObject updatePoster( Images images){

        boolean flag = imagesDao.updatePoster2(images);
        JSONObject jsonObject = new JSONObject();
        if(flag){
            jsonObject.put("error_code",200);
            jsonObject.put("msg","更新成功");
            return jsonObject;
        }
        jsonObject.put("error_code",400);
        jsonObject.put("msg","更新失败 可能海报名已存在，也可能提交数据类型错误");
        return jsonObject;
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/admin/deleteUser","/api/deleteUser"})
    @ResponseBody
    public JSONObject deleteUser(@RequestBody UserName userName){
        JSONObject jsonObject = new JSONObject();
        boolean flag = userDao.deleteUser(userName.getUsername());
        if(flag){
            jsonObject.put("error_code",200);
            jsonObject.put("msg","删除成功");
            return jsonObject;
        }
        jsonObject.put("error_code",400);
        jsonObject.put("msg","删除失败");
        return jsonObject;
    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @GetMapping(value = {"/admin/allUser","/api/allUser"})
    @ResponseBody
    public  JSONObject findAllUser(){

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());// 注入处理Date类


        JSONObject jsonObject = new JSONObject();
        List<User> userList = userDao.findAllUser();

        JSONArray jsonArray  = JSONArray.fromObject(userList,jsonConfig);

        jsonObject.put("count",userList.size());
        jsonObject.put("user",jsonArray);
        return jsonObject;
    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/searchPosterByAttri"})
    @ResponseBody
    public  JSONObject searchPosterByAttri(@RequestBody SearchPosterByAttri p){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());// 注入处理Date类


        JSONObject jsonObject = new JSONObject();
        List<Images> images = imagesDao.findOneByPosterAttri(p.getLocation(),p.getType(),p.getYear(),p.getStaring());
        JSONArray jsonArray = JSONArray.fromObject(images,jsonConfig);
        jsonObject.put("count",images.size());
        jsonObject.put("data",jsonArray);
        jsonObject.put("error_code",200);
        jsonObject.put("msg","查询成功");
        return jsonObject;
    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/deleteFavPoster"})
    @ResponseBody
    public JSONObject deleteFavPoster(@RequestBody AddUserFavPoster addUserFavPoster){
        boolean rst = userFavouriteDao.deleteFavPoster(addUserFavPoster.getUsername(),addUserFavPoster.getPostername());
        JSONObject jsonObject = new JSONObject();
        if(rst){
            jsonObject.put("error_code",200);
            jsonObject.put("msg","删除成功");
            return  jsonObject;
        }
        else{
            jsonObject.put("error_code",400);
            jsonObject.put("msg","删除失败：可能用户不存在，也可能喜欢的海报不存在");
            return  jsonObject;
        }
    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/user/addFavPoster","/api/addFavPoster"})
    @ResponseBody
    public JSONObject addFavPoster(@RequestBody PosterName posterName){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        JSONObject jsonObject = new JSONObject();
        if(userFavouriteDao.addFavPoster(username,posterName.getPoster_name())){
            jsonObject.put("error_code",200);
            jsonObject.put("msg","添加成功");
            return jsonObject;
        }
        else{
            jsonObject.put("error_code",400);
            jsonObject.put("msg","添加失败");
            return jsonObject;
        }
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/addFavPoster"})
    @ResponseBody
    public JSONObject addFavPoster(@RequestBody AddUserFavPoster addUserFavPoster){
        JSONObject jsonObject = new JSONObject();
        if(userFavouriteDao.addFavPoster(addUserFavPoster.getUsername(),addUserFavPoster.getPostername())){
            jsonObject.put("error_code",200);
            jsonObject.put("msg","添加成功");
            return jsonObject;
        }
        else{
            jsonObject.put("error_code",400);
            jsonObject.put("msg","添加失败");
            return jsonObject;
        }
    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @GetMapping(value = {"/user/userFavPoster","/api/userFavPoster"})
    @ResponseBody
    public JSONObject userFavPoster(){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());// 注入处理Date类

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Images> posters = findUserAllfavPoster.findUserAllfavPoster(username);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(posters,jsonConfig);
        if(posters!=null){
            jsonObject.put("username",username);
            jsonObject.put("poster",jsonArray);
            jsonObject.put("msg","查询成功");
            return jsonObject;
        }
        else{
            jsonObject.put("username",username);
            jsonObject.put("msg","查询失败,该用户不存在");
            return jsonObject;
        }
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/addPosterViewTime"})
    @ResponseBody
    public JSONObject addPosterViewTime(@RequestBody PosterName posterName){
        JSONObject jsonObject = new JSONObject();

        if(imagesDao.addPosterViewTime(posterName.getPoster_name())){
            jsonObject.put("error_code",200);
            jsonObject.put("msg","次数+1成功");
            return jsonObject;
        }
        else{
            jsonObject.put("error_code",400);
            jsonObject.put("msg","次数+1失败，不存在该海报");
            return jsonObject;
        }
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/deletePoster","/admin/deletePoster"})
    @ResponseBody
    public JSONObject deletePoster(@RequestBody PosterName posterName){
        JSONObject jsonObject = new JSONObject();

        boolean success = imagesDao.deleteOneImageByName(posterName.getPoster_name());
        if(success){

            jsonObject.put("error_code",200);
            jsonObject.put("msg","删除成功");
            return jsonObject;
        }
        jsonObject.put("error_code",400);
        jsonObject.put("msg","删除失败，海报不存在");
        return jsonObject;

    }
    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/posterViewTime"})
    @ResponseBody
    public JSONObject posterViewTime(@RequestBody PosterName posterName){
        JSONObject jsonObject = new JSONObject();
       int time = imagesDao.posterVeiwTime(posterName.getPoster_name());
       if(time==-1){
           jsonObject.put("time",-1);
           jsonObject.put("msg","不存在该海报名");
           return jsonObject;
       }
        jsonObject.put("time",time);
        jsonObject.put("msg","查询成功");

        return jsonObject;
    }


    @GetMapping(value = "/loginResultHandle")
    @ResponseBody
    public JSONObject loginResultHandle(){
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("error_code",400);
        jsonObject.put("msg","权限不够");

        return jsonObject;
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @GetMapping(value = {"/user/user_name","/api/user_name"})
    @ResponseBody
    public String getName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @GetMapping(value = "/user/film_name/{filmName}")
    @ResponseBody
    public Images getName(@PathVariable String filmName){
        return imagesDao.findOneByFileName(filmName);
    }

   /* @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @PostMapping("/api/login")
    public JSONObject login(@RequestBody Login login){
        JSONObject jsonObject = new JSONObject();
        User u = userDao.findOne(login.getUserame(),login.getPassword());
        jsonObject.put("data",u);
        if(u==null)jsonObject.put("error_code",400);
        else jsonObject.put("error_code",200);
        return jsonObject;
    }*/

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @PostMapping(value = {"/api/posterFuzzySearch","/admin/posterFuzzySearch"})
    public JSONObject fuzzySearch(@RequestBody PosterName posterName){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());// 注入处理Date类

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray ;
        List<Images> list = imagesDao.NameFuzzySearch(posterName.getPoster_name());
        jsonArray = JSONArray.fromObject(list,jsonConfig);
        jsonObject.put("count",list.size());
        jsonObject.put("data",jsonArray);
        return jsonObject;
    }


    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @PostMapping(value = {"/api/regist","/admin/adminAddUser","/api/adminAddUser"})
    public JSONObject regist(@RequestBody Login login){
        JSONObject jsonObject = new JSONObject();
        User user = userDao.findOneByUsername(login.getUserame());
        if(user==null){
            jsonObject.put("msg","成功");
            jsonObject.put("error_code",200);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            jsonObject.put("data",userDao.registUser(login.getUserame(),bCryptPasswordEncoder.encode(login.getPassword())).getReg_time());
        }
        else {
            jsonObject.put("error_code",400);
            jsonObject.put("msg","失败");
        }
        return jsonObject;
    }

    @Autowired
    CheckFileNameExist checkFileNameExist;

    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @PostMapping(value = {"/api/uploadPoster","/admin/uploadPoster"})
    public JSONObject uploadPoster(@RequestParam("poster_image") MultipartFile file, Images images){

        String fileName = images.getPoster_name()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        System.out.println(fileName);
        File dir = new File("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images");
        File dest = new File(dir.getPath()+ "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            JSONObject jsonObject = new JSONObject();
            if (checkFileNameExist.checkFilmNameExist(images.getPoster_name())){
                jsonObject.put("error_code",500);
                jsonObject.put("msg","该海报名已存在");
                return jsonObject;
            }
            file.transferTo(dest); // 保存文件
            Images images_get = new Images();
            images_get.setDirector(images.getDirector());
            images_get.setYear(images.getYear());
            images_get.setLocation(images.getLocation());
            images_get.setPoster_name(images.getPoster_name());
            images_get.setPoster_url("http://180.160.60.235:2333/poster_images/" + fileName);
            images_get.setStaring(images.getStaring());
            images_get.setType(images.getType());
            imagesDao.saveImage(images_get);
            for(String x:images.getStaring())
                System.out.println(x);
            jsonObject.put("error_code",200);
            jsonObject.put("msg","上传成功");
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error_code",400);
            jsonObject.put("msg","上传参数错误");
            return jsonObject;
        }
    }


    @CrossOrigin(allowedHeaders="*", allowCredentials="false",
            methods={RequestMethod.GET,RequestMethod.POST})
    @PostMapping(value = {"/api/multiUpload","/admin/multiUpload"})
    @ResponseBody
    public JSONObject multiUpload(@RequestParam("posters") MultipartFile[] files, Type type) {
        Images images = new Images();
        images.setDirector("none");
        images.setYear(2019);
        images.setLocation("中国");
        images.setStaring(new String[]{"1","2","3"});
        images.setType(type.getType());
        for(MultipartFile f:files){
            try {
                File dest = new File("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images\\"+f.getOriginalFilename());
                f.transferTo(dest);
                images.setPoster_name(f.getOriginalFilename());
                images.setPoster_url("http://180.160.60.235:2333/poster_images/"+f.getOriginalFilename());
                JSONObject jsonObject = new JSONObject();
                if(imagesDao.findOneByFileName(images.getPoster_name())!=null) {
                    jsonObject.put("error_code", 500);
                    jsonObject.put("msg", f.getOriginalFilename() + "图片重复");
                    return jsonObject;
                }
                imagesDao.saveImage(images);
            }
            catch (Exception e){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("error_code",400);
                jsonObject.put("msg",f.getOriginalFilename()+"上传失败:参数错误");
                return jsonObject;
            }


        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error_code",200);
        jsonObject.put("msg","上传成功");
        return jsonObject;
    }
}
