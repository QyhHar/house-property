<template>
      <div id="header">
        <div class="inner">
          <ul>
            <li @click="$router.push('/house')">首页</li>
            <li @click="goHouseBuy('1')">买房</li>
            <li @click="goHouseAdd('1')">卖房</li>
            <li @click="goHouseBuy('2')">租房</li>
            <li @click="goHouseAdd('2')">出租</li>
          </ul>
          <div class="loginReg" >

            <div v-if="userInfo.nickName">
              <el-dropdown @command="handleCommand">
                <span>{{ userInfo.nickName? `欢迎您，${userInfo.nickName}` : 'admin' }}</span>
                <el-dropdown-menu slot="dropdown" style="margin-top: 0px">
                  <el-dropdown-item   command="outLogin" >退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <div v-else >
              <svg-icon icon-class="head-icon"></svg-icon>
              <i @click="$router.push('/login')">登陆</i> / <i @click="$router.push('/register')">注册</i></div>
          </div>
        </div>
      </div>
</template>


<script>
    export default {
      name:'head',
      data(){
        return{
          userInfo:{},
        }
      },
      created() {
        if (sessionStorage.userInfo) {
          this.userInfo = JSON.parse(sessionStorage.userInfo);
        }
      },
      methods:{
        handleCommand(command){
          if(command == "outLogin")
            sessionStorage.removeItem("userInfo");
          this.$router.push('/login')
        },
        goHouseAdd(val){
          if(this.userInfo.nickName){
            if(this.$route.path==='/houseAdd'){
              this.$emit("getType", val);
            }else {
              this.$router.push({
                path: "/houseAdd",
                query: { type: val },
              });
            }
          }else {
            this.$message({
              message: '请登录',
              type: 'warning'
            });
            this.$router.push('/login')
          }
        },goHouseBuy(val){
          if(this.$route.path==='/houseBuy'){
            this.$emit("getType", val);
          }else {
            this.$router.push({
              path: "/houseBuy",
              query: { type: val },
            });
          }
        }
      }
    }
</script>
<style lang="less" >
#header{
  width: 100%;
  height: 38px;
  line-height: 38px;
  background-color: #101d37;
  font-size: 12px;
  .inner{
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;
    ul{
      width: 70%;
      float: left;
      li{
        display: inline;
        color: #a9abab;
        margin-right: 40px;
      }
      li:hover{
        cursor: pointer;
        color: rgb(228, 228, 228);
      }
    }
    .loginReg{
      width: 30%;
      float: right;
      color: rgb(228, 228, 228);
      text-align: right;
      .el-dropdown{
        color: rgb(228, 228, 228);
      }
      i{
        font-style: normal;
        cursor: pointer;
      }
      .svg-icon{
        width: 1.3em;
        height: 1.3em;
      }

    }
  }
}
</style>
