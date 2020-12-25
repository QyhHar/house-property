<template>
  <div id="login">
    <div class="login-nav">房产信息发布系统</div>
    <div class="login-center">
      <div class="inner">
        <h2>账号密码登陆</h2>
        <el-input v-model="input" placeholder="请输入用户名/账号" prefix-icon="el-icon-user" class="account"></el-input>
        <el-input v-model="input" placeholder="请输入密码"  prefix-icon="el-icon-unlock" class="account" show-password></el-input>
        <div class="optionalRules">
          <el-checkbox v-model="checked">记住密码</el-checkbox>
          <p @click="$router.push('/register')">立即注册</p>
        </div>
        <div class="loginBut">登  陆</div>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'login',
    data() {
      return {
          input: '',
          checked: true,
      }
    }
  }
</script>

<style lang="less">
#login{
  width: 100%;
  height: 100%;

  background-color: #fbfbfb;
  .login-nav{
    width: 1200px;
    height: 70px;
    margin: 0 auto;
    font-size: 24px;
    line-height: 70px;
    color: #353638;
  }
  .login-center{
    width: 100%;
    height:706px;
    background: url("src/assets/login/login.jpg") no-repeat center top;
    border-top:#ececec 1px solid;
    border-bottom:#ececec 1px solid;
    .inner{
      width: 400px;
      height: 320px;
      padding: 35px 45px;
      background-color: #fff;
      margin: 100px auto 0px;
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
        overflow: hidden;
        height: 50px;
        line-height: 50px;
        .el-checkbox{
          width: 50%;
          float: left;
        }
        p{
          width: 50%;
          float: left;
          text-align: right;
          font-size: 14px;
          color: #999;
          cursor: pointer;
        }
      }
    }
  }
}

</style>
