<template>
  <div id="register">
    <div class="login-nav">
      <div class="logo"><img src="../../assets/login/logo.png"></div>
    </div>
    <div class="login-center">
      <div class="inner">
        <h2>注册</h2>
        <el-input v-model="user.nickName" placeholder="请输入用户名" prefix-icon="el-icon-user" class="account"></el-input>
        <el-input v-model="user.userName" placeholder="请输入账号" prefix-icon="el-icon-user" class="account"></el-input>
        <el-input v-model="user.password" placeholder="请输入密码"  prefix-icon="el-icon-unlock" class="account" show-password></el-input>
        <el-input v-model="user.password2" placeholder="请再次确认密码"  prefix-icon="el-icon-unlock" class="account" show-password></el-input>
        <div class="optionalRules">

          <p>已有账号，请<i @click="$router.push('/login')">登录</i></p>
        </div>
        <div class="loginBut" @click="register">确  定</div>
      </div>
    </div>

  </div>
</template>

<script>
import api from '../../api/user.api'
  export default {
    name: 'register',
    data() {
      return {
          input: '',
          checked: true,
          user:{
            nickName:'',
            userName:'',
            password:'',
            password2:'',
            type:'0',
          },
      }
    },
    methods:{
      register(){
        if(this.user.password!==this.user.password2){
          this.$message({
            type: 'error',
            message: '请输入两次相同的密码!'
          })
          return false;
        }
        let bres=/^[a-zA-Z]+$/.test(this.user.userName);
        console.log(bres,'res');
        if(!bres){
          this.$message({
            type: 'error',
            message: '请输入英文字母!'
          })
          return false;
        }
        api.register(this.user).then(res => {
          if(res.code==0){
            this.$message({
              type: 'success',
              message: '注册成功!'
            })
            setTimeout(()=>{
              this.$router.push('/login')
            },2000) 
          }else{
            this.message({
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
      height: 450px;
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
        margin: 10px 0px;
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
        height: 50px;
        line-height: 50px;
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
