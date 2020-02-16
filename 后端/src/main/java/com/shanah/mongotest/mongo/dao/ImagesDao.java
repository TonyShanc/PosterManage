package com.shanah.mongotest.mongo.dao;

import com.mongodb.client.result.UpdateResult;
import com.shanah.mongotest.mongo.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.shanah.mongotest.mongo.model.ImageViewTime;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class ImagesDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    public  void saveImage( Images image) {
        if(image.getUpload_time()==null) image.setUpload_time(new Date(System.currentTimeMillis()));
        image.setModify_time(new Date(System.currentTimeMillis()));
        mongoTemplate.save(image);
        ImageViewTime imageViewTime = new ImageViewTime();
        imageViewTime.setPoster_name(image.getPoster_name());
        imageViewTime.setView_time(0);
        mongoTemplate.save(imageViewTime);
    }

    public Images findOneByFileName2(String posterName){
        Query query = new Query(Criteria.where("poster_name").is(posterName));
        Images images = mongoTemplate.findOne(query,Images.class);
        Query query2 = new Query(Criteria.where("poster_name").is("tmp"));
        Update update = new Update().set("director",posterName);
        mongoTemplate.updateFirst(query2,update,Images.class);
        return images;
    }
    public Images findOneByFileName(String posterName){
        Query query = new Query(Criteria.where("poster_name").is(posterName));
        Images images = mongoTemplate.findOne(query,Images.class);
        return images;
    }
    public boolean  updatePoster2(Images images){
        try {
            if(findOneByFileName(images.getPoster_name())!=null)return false;
            Query query = new Query(Criteria.where("poster_name").is("tmp"));
            String doPosterName = mongoTemplate.findOne(query, Images.class).getDirector();
            Images yuanimage = findOneByFileName(doPosterName);
            if (yuanimage != null && yuanimage.getUpload_time() != null)
                images.setUpload_time(yuanimage.getUpload_time());
            else images.setUpload_time(new Date());
            images.setModify_time(new Date(System.currentTimeMillis()));
            System.out.println(yuanimage.getPoster_name());
            System.out.println(yuanimage.getPoster_url());
            System.out.println(yuanimage.getPoster_url().substring(0,yuanimage.getPoster_url().lastIndexOf("/")+1)+images.getPoster_name()+yuanimage.getPoster_url().substring(yuanimage.getPoster_url().lastIndexOf(".")));
            images.setPoster_url(yuanimage.getPoster_url().substring(0,yuanimage.getPoster_url().lastIndexOf("/")+1)+images.getPoster_name()+yuanimage.getPoster_url().substring(yuanimage.getPoster_url().lastIndexOf(".")));
            System.out.println("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images\\" + images.getPoster_name() + yuanimage.getPoster_url().substring(yuanimage.getPoster_url().lastIndexOf(".")));
            System.out.println(images.getPoster_url());
            File file2 = new File("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images\\" + doPosterName + yuanimage.getPoster_url().substring(yuanimage.getPoster_url().lastIndexOf(".")));
            file2.renameTo(new File("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images\\" + images.getPoster_name() + yuanimage.getPoster_url().substring(yuanimage.getPoster_url().lastIndexOf("."))));
            deleteOneImageByName(doPosterName);
            mongoTemplate.save(images);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean updatePoster(MultipartFile file,Images images){
        try {
            Query query = new Query(Criteria.where("poster_name").is("tmp"));
            String doPosterName = mongoTemplate.findOne(query,Images.class).getDirector();
            Images yuanimage = findOneByFileName(doPosterName);
            if(yuanimage!=null&&yuanimage.getUpload_time()!=null)images.setUpload_time(yuanimage.getUpload_time());
            else images.setUpload_time(new Date());
            images.setModify_time(new Date(System.currentTimeMillis()));
            File file2 = new File("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images\\"+doPosterName+".jpg");
            File dest = new File("F:\\JAVA项目\\mongo\\src\\main\\resources\\static\\poster_images\\"+file.getOriginalFilename());
            if(file2.exists()) file2.delete();
            file.transferTo(dest);
            deleteOneImageByName(doPosterName);
            mongoTemplate.save(images);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public List<Images> NameFuzzySearch(String fuzzyName){
        Pattern pattern = Pattern.compile("^.*"+fuzzyName+".*$", Pattern.CASE_INSENSITIVE);
        Query query = Query.query(Criteria.where("poster_name").regex(pattern));
        List<Images> images = mongoTemplate.find(query, Images.class);
        return images;
    }
    public  int posterVeiwTime(String poster_name){
        Query query = new Query(Criteria.where("poster_name").is(poster_name));
        ImageViewTime imageViewTime= mongoTemplate.findOne(query,ImageViewTime.class);
        if (imageViewTime!=null) return imageViewTime.getView_time();
        else return -1;
    }
    public boolean deleteOneImageByName(String poster_name){
        if(findOneByFileName(poster_name)==null)return false;
        Query query=new Query(Criteria.where("poster_name").is(poster_name));
        mongoTemplate.remove(query,Images.class);
        mongoTemplate.remove(query, ImageViewTime.class);
        return true;
    }
    public boolean addPosterViewTime(String poster_name){
        int time = posterVeiwTime(poster_name);
        if(time==-1)return false;
        Query query=new Query(Criteria.where("poster_name").is(poster_name));
        Update update= new Update().set("view_time",time+1);
        mongoTemplate.updateFirst(query,update,ImageViewTime.class);
        return true;
    }
    public List<Images> findOneByPosterAttri(String location,String[] type,int year, String[] staring){

        System.out.println(Arrays.toString(staring));
        Query query = new Query(Criteria.where("location").is(location).and("type").gte(type).and("year").is(year).and("staring").gte(staring));
        List<Images> images = mongoTemplate.find(query, Images.class);
        return images;
    }
    public  List<Images> findAllPoster(){
        List<Images> list = mongoTemplate.findAll(Images.class);
        for(Images images:list)System.out.println(images.getDirector());
        return list;
    }
}
