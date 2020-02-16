<template>
  <div>
    <div>
      <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin-top: 1%;">

        <el-form-item label="地区:">
          <el-select v-model="formInline.region" placeholder="选择地区" class="select-box">
            <el-option label="全部" value="all"></el-option>
            <el-option label="华语" value="huayu"></el-option>
            <el-option label="美国" value="meiguo"></el-option>
            <el-option label="欧洲" value="ouzhou"></el-option>
            <el-option label="韩国" value="hanguo"></el-option>
            <el-option label="日本" value="riben"></el-option>
            <el-option label="其他" value="qita"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="类型:">
          <el-select v-model="formInline.leixing" placeholder="电影类型" class="select-box">
            <el-option label="全部" value="all"></el-option>
            <el-option label="喜剧" value="xiju"></el-option>
            <el-option label="悲剧" value="beiju"></el-option>
            <el-option label="爱情" value="aiqing"></el-option>
            <el-option label="动作" value="dongzuo"></el-option>
            <el-option label="枪战" value="qiangzhan"></el-option>
            <el-option label="犯罪" value="fanzui"></el-option>
            <el-option label="其他" value="qita"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="年份:">
          <el-select v-model="formInline.year" placeholder="选择年份" class="select-box">
            <el-option label="全部" value="all"></el-option>
            <el-option label="2019" value="2019"></el-option>
            <el-option label="2018" value="2018"></el-option>
            <el-option label="2017" value="2017"></el-option>
            <el-option label="2016" value="2016"></el-option>
            <el-option label="2015" value="2015"></el-option>
            <el-option label="更早" value="before"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="主演:">
          <el-input v-model="formInline.actor" placeholder="查找主演……" class="select-box"></el-input>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </div>


    <div class="home_container">

      <el-popover
        v-for="(item,index) in url" :key="index"
        placement="top-start"
        title="电影：我是个好人"
        width="180"
        trigger="hover"
        content="主演:黄子韬、赵丽颖、邓超 简介:这部电影很牛逼，你不看不知道一看吓一跳。">
        <div class="demo-image__preview" slot="reference">
          <el-image
            style="width: 200px; height: 240px;"
            :fit="fit"
            :src="item.image"
            :preview-src-list="srcList">
          </el-image>
          <el-row style="margin: 5px 0 5px 0">
            <el-col :span="18"><div>电影名称</div></el-col>
            <el-col :span="6"><div class="el-icon-view">14</div></el-col>
          </el-row>
        </div>
      </el-popover>


    </div>
  </div>

</template>

<script>
    export default {
      name: "home",
      data() {
        return {
          fit:'cover',
          url: [
            {image:require('../assets/langYa.jpg')},
            {image:require('../assets/daoShiXiaShan.jpg')},
            {image:require('../assets/langYa.jpg')},
            {image:require('../assets/daoShiXiaShan.jpg')},
            {image:require('../assets/langYa.jpg')},
            {image:require('../assets/daoShiXiaShan.jpg')},
            {image:require('../assets/langYa.jpg')},
            {image:require('../assets/daoShiXiaShan.jpg')},
            ],
          srcList: [
            require('../assets/langYa.jpg'),
            require('../assets/daoShiXiaShan.jpg'),
            require('../assets/langYa.jpg'),
            require('../assets/daoShiXiaShan.jpg'),
            require('../assets/langYa.jpg'),
            require('../assets/daoShiXiaShan.jpg'),
            require('../assets/langYa.jpg'),
            require('../assets/daoShiXiaShan.jpg'),
          ],
          formInline: {
            user: '',
            region: '',
            leixing:'',
            year:'',
            actor:'',
            key:'',
          },
        }
      }
    }
</script>

<style scoped>
  .demo-image__preview{
    background-color: white;
    padding: 8px;
    border: 1px solid rgb(230,230,230);
    display: inline-block;
    margin: 0 0 2.5% 1%;
  }
  .home_container{
    margin: 0 auto;
    padding-top: 1%;
    width: 75%;

  }
  .select-box{
    width: 160px;
  }



</style>
