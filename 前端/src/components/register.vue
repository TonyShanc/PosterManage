<template>
  <div>
    <el-card class="box-card">
      <div class="title"><i class="el-icon-user-solid"></i><strong>用户注册</strong></div>
      <br>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm" label-width="20%" :label-position="labelPosition">
        <el-form-item>
          <el-input v-model="ruleForm.name"  prefix-icon="el-icon-user" placeholder="请输入您的用户名"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" prefix-icon="el-icon-lock"  placeholder="请输入您的密码"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" prefix-icon="el-icon-lock"  placeholder="请再次输入您的密码"></el-input>
        </el-form-item>
        <el-form-item>
<!--          <el-button @click="back()" round type="primary" plain>返回</el-button>-->
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "register",
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
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };

      return {
        labelPosition: 'right',
        ruleForm: {
          pass: '',
          checkPass: '',
          name: ''
        },
        rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      back(){
        this.$router.push({path: '/login'})
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
