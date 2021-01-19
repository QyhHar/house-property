<template>
    <div id="house">
        <div class="header">
            <div class="wrap">
                <!-- 导航 -->
                <div class="wrap-top">
                    <div class="fl">
                      <div class="logo" ><img src="../../assets/login/logo1.png"></div>
                    </div>
                    <div class="fr">
                        <ul>
                            <li @click="goHouseBuy('1')">买房</li>
                            <li @click="goHouseAdd('1')" >卖房</li>
                            <li @click="goHouseBuy('2')">租房</li>
                            <li @click="goHouseAdd('2')">出租</li>
                        </ul>
                        <div class="backstage" @click="$router.push('/manage')" v-if="userInfo.type==1">后台管理</div>

                        <div class="loginReg" >

                          <div v-if="userInfo.nickName">
                            <el-dropdown @command="handleCommand">
                              <span> <svg-icon icon-class="head-icon"></svg-icon>{{ userInfo.nickName? `欢迎您，${userInfo.nickName}` : 'admin' }}</span>
                              <el-dropdown-menu slot="dropdown" style="margin-top: -6px">
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
                <!-- banner大标题及搜索框 -->
                <div class="wrap-bot">
                    <p>要找真房源 信息丰富全</p>
                    <h3>开始寻找属于你的家</h3>
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="找买房" name="first"></el-tab-pane>
                        <el-tab-pane label="找租房" name="second"></el-tab-pane>
                        <el-input placeholder="请输入内容" v-model="residential" class="input-with-select">
                            <el-button slot="append" @click="select"  icon="el-icon-search"></el-button>
                        </el-input>
                    </el-tabs>

                </div>
            </div>
        </div>
        <div class="list1">
            <div class="name">品质好房</div>
            <p>好房源那么多，我们为你精选</p>
            <div class="list1-inner">
                <el-row>
                    <el-col :span="8" v-for="(item, index) in qualityHouse.buy" :key="index">
                        <el-card @click.native="goHouseEdit(item.id)" :body-style="{ padding: '0px',}">
                        <img v-if="item.images.length" :src="item.images[0].imageUrl" class="image"  width="323" height="200">
                        <img v-if="!item.images.length" src="../../assets/login/login.jpg"  class="image"  width="323" height="200">
                        <div style="padding: 14px;">
                            <h2>{{item.areaName}}</h2>
                            <h2>{{item.residential}}</h2>
                            <div class="bottom clearfix">
                                <time class="size">{{item.room +`室`+item.office+`厅 `+item.measureArea+`m²`}}</time>
                                <div class="money">{{item.totalPrice}}万</div>
                            </div>
                        </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div class="list1" style="padding-bottom:80px">
            <div class="name">品质租房</div>
            <p>高品质租房体验，从我们开始</p>
            <div class="list1-inner">
                <el-row>
                    <el-col :span="8" v-for="(item, index) in qualityHouse.rent" :key="index" :class="index===3?'':'last'">
                        <el-card @click.native="goHouseEdit(item.id)" :body-style="{ padding: '0px' }">
                          <img v-if="item.images.length" :src="item.images[0].imageUrl" class="image"  width="323" height="200">
                          <img v-if="!item.images.length" src="../../assets/login/login.jpg"  class="image"  width="323" height="200">
                        <div style="padding: 14px;">
                            <h2>{{item.areaName}}</h2>
                            <h2>{{item.residential+item.room +`室`+item.office+`厅 `}}</h2>
                            <div class="bottom clearfix">
                                <time class="size" style="width:30%">{{item.rentalType==='1'?'整租':'合租'}}</time>
                                <div class="money" style="width:70%">{{item.rent&&item.rent.substring(0,item.rent.length-3)}}元/月</div>
                            </div>
                        </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
  import api from "../../api/house.api"
  import userApi from "../../api/user.api"
export default {
    name:'house',
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
          activeName: 'first',
          residential:'',
          userInfo:{},
          rules: {
            nickName: [
              { validator: validateNickName, trigger: 'blur' }
            ],oldPassword: [
              { validator: validatePass, trigger: 'blur' }
            ],newPassword: [
              { validator: validatePass, trigger: 'blur' }
            ],
          },
          qualityHouse:{
            rent:[],
            buy:[],
          }
        }
    },
  created() {
    if (sessionStorage.userInfo) {
      this.userInfo = JSON.parse(sessionStorage.userInfo);
    }
    api.getQualityHouse().then(res=>{
      if(res.code===0) {
        this.qualityHouse=res.data;
      }else{
        this.$message({
          message: res.message,
          type: 'warning'
        })
      }
    })
  },
  methods:{
    changPassword(){
      userApi.updatePassword(this.userInfo).then(res=>{
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
    goHouseEdit(houseId){
      this.$router.push({
        path: "/houseEdit",
        query: { houseId: houseId },
      });
    },
    handleClick(tab, event) {
      this.activeName = tab.name;
    },
    select(){
      this.$router.push({
        path: "/houseBuy",
        query: { residential: this.residential,type: this.activeName==='first'?'1':'2' },
      });
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
        this.$router.push({
          path: "/houseAdd",
          query: { type: val },
        });
      }else {
        this.$message({
          message: '请登录',
          type: 'warning'
        });
        this.$router.push('/login')
      }
    },
    goHouseBuy(val){
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

<style lang="less">
  #house{
    .header{
      width: 100%;
      height: 566px;
      background: #000 url(../../assets/house/bg_header@1x.jpg) ;
      color: #fff;
      padding-top: 15px;
      .wrap{
        padding: 0 50px;
        .wrap-top{
          width: 100%;
          height: 60px;
          line-height: 60px;
          overflow: hidden;
          .fl{
            width: 15%;
            float: left;
            .logo{
              width:150px;
              padding: 5px 0 0 100px;
              img{
                width:150px;
              }
            }
          }
          .fr{
            width: 60% ;
            float: right;
            overflow: hidden;
            ul{
              width: 70%;
              float: left;
              text-align: right;
              li{
                list-style: none;
                display: inline;
                padding: 0 20px;
                cursor: pointer;
              }
            }
            .backstage{
              width: 10%;
              float: left;
              padding-left: 20px
            ;cursor: pointer;

            }
            .loginReg{
              .el-popper[x-placement^=bottom] {
                margin-top: -10px!important;
              }
              width: 15%;
              float: right;
              i{
                font-style: normal;
                cursor: pointer;
              }
              .svg-icon{
                width: 1.3em;
                height: 1.3em;
              }
            }
            .el-dropdown{
              color: #fff;
              cursor: pointer;
            }
          }
        }
        .wrap-bot{
          width: 1200px;
          margin: 120px auto 0px;
          p{
            font-size: 28px;
            font-weight: 300;
            text-align: left;
          }
          h3{
            font-size: 66px;
            font-weight: 600;
            line-height: 80px;
            text-align: left;
            padding: 10px 0px 30px;
            font-family: \\5FAE\8F6F\96C5\9ED1,Hiragino Sans GB;
          }
          .el-tabs__header{
            margin: 0;
          }
          .el-tabs__nav-wrap::after{
            background-color:transparent;
          }
          .el-tabs__active-bar{
            border-left: 9px solid transparent;
            border-right: 9px solid transparent;
            width: 0px!important;
            height: 0;
            transition: all .3s ease-in-out;
            position: absolute;
            top: 44px;
            left: 16px;
            border-bottom: 10px solid #fff;
            z-index: 9;
            background-color: transparent;

          }
          .el-tabs__item.is-active{
            color: #fff;
            font-size: 16px;
          }
          .el-tabs__item{
            color: rgb(180, 180, 180);
            font-size: 16px;
            height: 54px;
          }
          .el-tabs__item:hover{
            color: rgb(221, 221, 221);
          }
          .el-input-group{
            width: 35%;
          }
          .el-input-group__append, .el-input-group__prepend{
            background-color:#3072F6 ;
            color: #fff;
            border: 1px solid #3072F6;

          }

        }
      }
    }
    .list1{
      width: 1200px;
      margin: 80px auto 0;
      .name{
        font-size: 36px;
        line-height: 50px;
        color: #101d37;
        font-weight: 600;
      }
      p{
        color: #9399a5;
        width: 1150px;
        font-size: 16px;
        line-height: 22px;
      }
      .list1-inner{
        width: 100%;
        overflow: hidden;
        margin-top: 26px;
        .el-col-8{
          margin-right: 26px;
        }
        .el-col-8:last-child{
          margin-right: 0;
        }
        .time {
          font-size: 13px;
          color: #999;
        }
        .bottom {
          margin-top: 13px;
          line-height: 12px;
        }
        .button {
          padding: 0;
          float: right;
        }
        .image {
          display: block;
        }
        .clearfix:before,
        .clearfix:after {
          display: table;
          content: "";
        }
        .clearfix:after {
          clear: both
        }
        .el-col-8{
          width: 23%;
          cursor: pointer;
        }
        h2{
          font-size: 18px;
          font-weight: 700;
          line-height: 24px;
          color: #222;
        }
        .bottom{
          width: 100%;
          height: 26px;
          line-height: 26px;
          overflow: hidden;
          .size{
            width: 70%;
            font-size: 12px;
            color: #616669;
            float: left;
          }
          .money{
            width: 30%;
            float: right;
            color: #f00;
            text-align: right;
          }
        }

      }

    }
  }

</style>
