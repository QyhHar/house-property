<template>
  <div id="register">
    <div class="login-nav">
      <div class="logo" @click="$router.push('/house')"><img src="../../assets/login/logo.png"></div>
    </div>
    <div class="login-center">
      <div class="inner">
        <h2>注册</h2>
        <el-form  :model="user" status-icon :rules="rules" ref="user"  class="demo-ruleForm">
          <el-form-item  prop="nickName">
            <el-input v-model="user.nickName" placeholder="请输入用户名"  clearable prefix-icon="el-icon-user" class="account"></el-input>
          </el-form-item>
          <el-form-item  prop="userName">
            <el-input  v-model="user.userName" placeholder="请输入账号" clearable prefix-icon="el-icon-user" class="account"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="user.password" placeholder="请输入密码" clearable  prefix-icon="el-icon-unlock" class="account" show-password></el-input>
          </el-form-item>
          <el-form-item  prop="password2">
            <el-input v-model="user.password2" placeholder="请再次确认密码" clearable  prefix-icon="el-icon-unlock" class="account" show-password></el-input>
          </el-form-item>
          <el-form-item  prop="code">
            <div style="overflow: hidden">
              <el-input v-model="user.code" clearable placeholder="请输入验证码" style="width: 70%;float: left"  prefix-icon="el-icon-link" class="account" ></el-input>
              <img :src="user.captchaCode" @click="getCode"  style="width: 100px;height: 50px;float: right;margin-top: -5px;"/>
            </div> </el-form-item>

        </el-form>

        <div class="optionalRules">

          <p>已有账号，请<i @click="$router.push('/login')">登录</i></p>
        </div>
        <div class="loginBut" @click="submit">确  定</div>
      </div>
    </div>

  </div>
</template>

<script>
import api from '../../api/user.api'
  export default {
    name: 'register',
    data() {
      let validateUserName = (rule, value, callback) => {
        // debugger
        if (!value ) {
          callback(new Error('请输入账户'));
        } else if(!/^[a-zA-Z0-9]+$/.test(value)){
          callback(new Error('请输入英文字母或数字'));
        }else  {
          api.getUserName({userName:value}).then(res=>{
            if(res.code!==0){
              callback(new Error('账户重复，请重新输入'));
            }else {
              callback();
            }
          });
        }
      };
      let validatePass = (rule, value, callback) => {
        if (!value ) {
          callback(new Error('请输入密码'));
        } else if(!/^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,30}$/.test(value)){
          callback(new Error('密码6到30位必须含有英文字母、数字、特殊字符'));
        }else {
          callback();
        }
      };
      let validatePass2 = (rule, value, callback) => {
        if (!value ) {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.user.password) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      };
      let validateCode = (rule, value, callback) => {
        if (!value ) {
          callback(new Error('请输入验证码'));
        } else {
          callback();
        }
      };
      let validateNickName = (rule, value, callback) => {
        if (!value ) {
          callback(new Error('请输入用户名'));
        } else {
          callback();
        }
      };
      return {
          input: '',
          checked: true,
          user:{
            nickName:'',
            userName:'',
            password:'',
            password2:'',
            type:'0',
            code:'',
            uuid:'',
            captchaCode:'',
          },
        rules: {
          nickName: [
            { validator: validateNickName, trigger: 'blur' }
          ], userName: [
            { validator: validateUserName, trigger: 'blur' }
          ],password: [
            { validator: validatePass, trigger: 'blur' }
          ],password2: [
            { validator: validatePass2, trigger: 'blur' }
          ],code: [
            { validator: validateCode, trigger: 'blur' }
          ],
        }
      }
    },
    created() {
      this.getCode();
    },
    methods:{

      getCode() {
        api.getCode({width: 200, height: 50}).then(res => {
          this.user.captchaCode = res.data.captchaCode;
          this.user.uuid = res.data.uuid;
        });
      },
      submit(){
        this.register();
      },
      register(){
        api.register(this.user).then(res => {
          if(res.code==0){
            this.$message({
              type: 'success',
              message: '注册成功,请登录!'
            })
            setTimeout(()=>{
              this.$router.push('/login')
            },500)
          }else{
            this.$message({
              message: res.message,
              type: 'warning'
            })
          }
        });
      }

    }
  }
</script>

<style lang="less">
#register{
  width: 100%;
  height: 100%;

  background-color: #fbfbfb;
  .login-nav{
    width: 1200px;
    height: 70px;
    margin: 0 auto;
    .logo{
      width:150px;
      padding-top: 5px;
      img{
        width:150px;
      }
    }
  }
  .login-center{
    width: 100%;
    height:706px;
    background: url("../../assets/login/login.jpg") no-repeat center top;
    border-top:#ececec 1px solid;
    border-bottom:#ececec 1px solid;
    .inner{
      width: 400px;
      height: 510px;
      padding: 35px 45px;
      background-color: #fff;
      margin: 80px auto 0px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
      h2{
        font-size: 24px;
        color: #43474D;
        font-weight: 400;
        line-height: 30px;
        margin-bottom: 20px;
      }
      .el-input__inner{
        margin: 0px 0px;
        height: 48px!important;
      }
      .loginBut{
        width: 100%;
        height: 48px;
        line-height: 48px;
        color: #fff;
        background-color:#3072F6 ;
        text-align: center;
        border-radius: 4px;
        margin: 10px 0px;
        cursor: pointer;
      }
      .optionalRules{
        width: 100%;
        height: 40px;
        line-height: 40px;
        p{
          width: 100%;
          text-align: right;
          font-size: 14px;
          color: #999;
          cursor: pointer;
          i{
            font-style:normal ;
            color: #3072F6;
          }
        }
      }
    }
  }
}

</style>
