<template>
  <div id="login">
    <div class="login-nav">
      <div class="logo"><img src="../../assets/login/logo.png"></div>
    </div>
    <div class="login-center">
      <div class="inner">
        <h2>账号密码登陆</h2>
        <el-input v-model="userName" placeholder="请输入用户名/账号" prefix-icon="el-icon-user" class="account"></el-input>
        <el-input v-model="password" placeholder="请输入密码"  prefix-icon="el-icon-unlock" class="account" show-password></el-input>
        <div class="optionalRules">
          <el-checkbox v-model="checked">记住密码</el-checkbox>
          <p @click="$router.push('/register')">立即注册</p>
        </div>
        <div class="loginBut" @click="login">登  陆</div>
      </div>
    </div>

  </div>
</template>

<script>
  import api from '../../api/user.api'
  export default {
    name: 'login',
    data() {
      return {
        userName: '',
        password:'',
        checked: true,
      }
    },
    created() {
      //判断是否记住密码
      this.getCookie();
    },
    methods:{
      //读取cookie
      getCookie: function() {
        if (document.cookie.length > 0) {
          let arr = document.cookie.split("; "); //这里显示的格式请根据自己的代码更改
          console.log(arr);
          for (let i = 0; i < arr.length; i++) {
            let arr2 = arr[i].split("="); //根据==切割
            //判断查找相对应的值
            if (arr2[0] == "userName") {
              this.userName = arr2[1];
            } else if (arr2[0] == "password") {
              // Decrypt，将解密后的内容赋值给密码
              this.password = arr2[1];
            }
          }
        }
        if (this.userName && this.password) {
          this.checked = true;
        }
      },
      login(){
        let data={
          userName:this.userName,
          password:this.password
        }
        api.login(data).then(res => {
          if(res.code==0){
            this.$message({
              type: 'success',
              message: '登录成功!'
            });
            let userInfo = res.data;
            sessionStorage.userInfo = JSON.stringify({
              nickName: userInfo.nickName,
              id: userInfo.id,
              userName: userInfo.userName,
              password: userInfo.password,
              type: userInfo.type,
            });
            if (this.checked) {
              //判断用户是否勾选了记住密码选项rememberPsw，传入保存的账号currentPortId，密码password，天数30
              this.setCookie(this.userName, this.password, 30);
            } else {
              this.clearCookie();
            }
            this.$router.push('/house')
          }else{
            this.$message({
              message: res.message,
              type: 'error'
            })
          }

        });
      },
      //清除cookie
      clearCookie: function() {
        this.setCookie("", "", -1);
      },
      //设置cookie
      setCookie(userName, psw, exdays) {
        let exdate = new Date(); //获取时间
        exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
        //字符串拼接cookie，为什么这里用了==，因为加密后的字符串也有个=号，影响下面getcookie的字符串切割，你也可以使用更炫酷的符号。
        window.document.cookie =
          "userName" + "=" + userName + ";path=/;expires=" + exdate.toGMTString();
        window.document.cookie =
          "password" +
          "=" +
          psw +
          ";path=/;expires=" +
          exdate.toGMTString();
      },

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
          color: #3072F6;
          cursor: pointer;
        }
      }
    }
  }
}

</style>
