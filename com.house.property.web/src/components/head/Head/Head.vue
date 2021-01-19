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
            <div class="backstage" @click="$router.push('/manage')" v-if="userInfo.type==1">后台管理</div>
            <div v-if="userInfo.nickName">
              <el-dropdown @command="handleCommand">
                <span> <svg-icon icon-class="head-icon"></svg-icon>{{ userInfo.nickName? `欢迎您，${userInfo.nickName}` : 'admin' }}</span>
                <el-dropdown-menu slot="dropdown" style="margin-top: 0px">
                  <el-dropdown-item   command="changPassword" >修改密码</el-dropdown-item>
                  <el-dropdown-item   command="houseUser" >个人中心</el-dropdown-item>
                  <el-dropdown-item   command="outLogin" >退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <div v-else >
              <svg-icon icon-class="head-icon"></svg-icon>
              <i @click="$router.push('/login')">登陆</i> / <i @click="$router.push('/register')">注册</i></div>
          </div>
        </div>
        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%"
          >

          <el-form  :model="userInfo"  status-icon :rules="rules" ref="user"  class="demo-ruleForm">
            <el-row>
              <el-col :span="18">
                <el-form-item label="用户姓名" prop="nickName" label-width="100px">
                  <el-input
                    size="small"
                    v-model="userInfo.nickName"
                    auto-complete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="18">
                <el-form-item label="登录名" prop="userName" label-width="100px">

                    <el-input
                      size="small"
                      v-model="userInfo.userName"
                      disabled="disabled"
                    ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="18">
                <el-form-item label="旧密码" prop="oldPassword" label-width="100px">
                    <el-input
                      size="small"
                      type="password"
                      v-model="userInfo.oldPassword"
                      show-password
                    >
                    </el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="18">
                <el-form-item label="新密码" prop="newPassword" label-width="100px">
                  <el-input
                    size="small"
                    type="password"
                    v-model="userInfo.newPassword"
                    show-password
                  >
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button size="mini" @click="close">取 消</el-button>
            <el-button size="mini" type="primary" @click="changPassword">确 定</el-button>
          </div>

        </el-dialog>
      </div>
</template>


<script>
    import api from "../../../api/user.api";

    export default {
      name:'head',
      data(){
        let validateNickName = (rule, value, callback) => {
          if (!value ) {
            callback(new Error('请输入用户名'));
          } else {
            callback();
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
        return{
          dialogVisible: false,
          userInfo:{},
          rules: {
            nickName: [
              { validator: validateNickName, trigger: 'blur' }
            ],oldPassword: [
              { validator: validatePass, trigger: 'blur' }
            ],newPassword: [
              { validator: validatePass, trigger: 'blur' }
            ],
          }
        }
      },
      created() {
        if (sessionStorage.userInfo) {
          this.userInfo = JSON.parse(sessionStorage.userInfo);
        }
      },
      methods:{
        changPassword(){
          api.updatePassword(this.userInfo).then(res=>{
            if(res.code===0) {
              let userInfo = res.data;
              sessionStorage.userInfo = JSON.stringify({
                nickName: userInfo.nickName,
                id: userInfo.id,
                userName: userInfo.userName,
                password: userInfo.password,
                type: userInfo.type,
              });
              this.userInfo={};
              this.userInfo=userInfo;
              this.$message({
                message: '修改密码成功',
                type: 'success'
              });
              this.dialogVisible=false;
            }else{
              this.$message({
                message: res.message,
                type: 'warning'
              })
            }
          });
        },
        close(){
          this.dialogVisible=false;
        },
        handleCommand(command){
          if(command === "outLogin"){
            sessionStorage.removeItem("userInfo");
            this.$router.push('/login')
          }else if(command === "houseUser"){
            this.$router.push('/houseUser')
          }else if(command === "changPassword"){
            this.dialogVisible=true;
          }
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
    color: #a9abab;
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
      .backstage{
        float: left;
        padding-left: 20px;
        cursor: pointer;

      }
      width: 18%;
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
