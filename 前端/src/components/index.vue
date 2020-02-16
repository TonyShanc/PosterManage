<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :span="2">
          <img src="../assets/logo.jpg" alt="">
        </el-col>

        <el-col :span="9" :offset="6">
          <el-input
            style="margin-top: 1%;"
            placeholder="请输入模糊查询内容……"
            v-model="input">
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
<!--          <div style="text-align: center">-->
<!--            <el-menu :default-active="$route.path"-->
<!--                     router-->
<!--                     class="el-menu-demo"-->
<!--                     mode="horizontal"-->
<!--                     @select="handleSelect"-->
<!--                     style="border-bottom: 0;margin: auto"-->
<!--            >-->
<!--              <el-menu-item index="/">首页</el-menu-item>-->
<!--              <el-menu-item index="/juQing">剧情</el-menu-item>-->
<!--              <el-menu-item index="/dongZuo">动作</el-menu-item>-->
<!--              <el-menu-item index="/xiJu">喜剧</el-menu-item>-->
<!--              <el-menu-item index="/jingSong">惊悚</el-menu-item>-->
<!--              <el-menu-item index="/fanZui">犯罪</el-menu-item>-->
<!--              <el-menu-item index="/more">更多</el-menu-item>-->
<!--            </el-menu>-->
<!--          </div>-->
        </el-col>

        <el-col :span="4" style="float: right;">
          <el-button type="primary" @click="login()" class="login-button">登录 | 注册</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="show-picture"><router-view></router-view></div>
  </div>


</template>

<script>
  import home from '../components/home'
  export default {
    name: "Nav",
    data(){
      return{
        input:'',
      }
    },
    methods:{
      login(){
        this.$router.push({path: '/login'})
      },
      handleSelect(){
        console.log('测试成功');
      }
    }
  }
</script>

<style scoped>
  .container{
    /*position: fixed;*/
    background-color: white;
    border-bottom: 1px solid rgb(230,230,230);
  }
  .login-button{
    margin-top: 3%;
  }
  img{
    width: 200%;
  }
  .el-menu-demo{
    width: 100%;
  }
  .show,.knowledge{
    width: 70%;
    height: 500px;
    background-color: white;
    border: 1px solid rgb(230,230,230);
    margin:2% auto auto auto;

  }

</style>
