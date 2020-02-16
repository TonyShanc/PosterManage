<template>
  <div>
    <el-card class="box-card">
      <div class="title"><i class="el-icon-user-solid"></i><strong>用户登录</strong></div>
      <br>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm" label-width="20%" :label-position="labelPosition">
        <el-form-item>
          <el-input v-model="ruleForm.name"  prefix-icon="el-icon-user" placeholder="请输入您的用户名"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" prefix-icon="el-icon-lock"  placeholder="请输入您的密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
          <el-button @click="register()">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "login",
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      return {
        labelPosition: 'right',
        ruleForm: {
          pass: '',
          name: ''
        },
        rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let url = 'http://180.160.54.129:2333/api/login';
            fetch(url, {//fetch请求发送的内容
              method: 'POST',//请求方式
              body: JSON.stringify({name: this.name, password: this.pass}),//请求发送的参数，具体数据以json格式放在body里面
              headers: {//请求头，表明发送数据的类型
                "Content-type": "application/json",
              }
            }).then(res => {
              res.json().then(data => {
                this.name = data.name;
                alert(this.name);
              })
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      register(){
        this.$router.push({path: '/register'})
      }
    }
  }
</script>

<style scoped>
  .title{
    text-align: center;
    color: #409EFF;
    font-size: 1.5em;
    border-bottom: 0.05em solid #DCDFE6;
    margin-bottom: 1em;
    margin-top: 3%;
    padding-bottom: 3%;
  }
  .demo-ruleForm{
    width: 80%;
  }
  .box-card{
    width: 40%;
    margin:10% auto auto auto;
  }


</style>
