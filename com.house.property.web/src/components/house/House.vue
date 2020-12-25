<template>
    <div id="house">
        <div class="header">
            <div class="wrap">
                <!-- 导航 -->
                <div class="wrap-top">
                    <div class="fl">房产信息发布系统</div>
                    <div class="fr">
                        <ul>
                            <li>买房</li>
                            <li>卖房</li>
                            <li>租房</li>
                            <li>出租</li>
                        </ul>
                        <div class="backstage">后台管理</div>
                        <div class="loginReg">
                            <svg-icon icon-class="head-icon"></svg-icon>
                            <i>登陆</i> / <i>注册</i>
                        </div>
                    </div>
                </div>
                <!-- banner大标题及搜索框 -->
                <div class="wrap-bot">
                    <p>要找真房源 信息丰富全</p>
                    <h3>开始寻找属于你的家</h3>
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="找买房" name="first"></el-tab-pane>
                        <el-tab-pane label="找租房" name="second"></el-tab-pane>
                        <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
                            <el-button slot="append" icon="el-icon-search"></el-button>
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
                    <el-col :span="8" v-for="(o, index) in 4" :key="o" :offset="index > 0 ? 1 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
                        <div style="padding: 14px;">
                            <h2>未央区</h2>
                            <h2>保利金香槟</h2>
                            <div class="bottom clearfix">
                                <time class="size">3室2厅.91平米</time>
                                <div class="money">147.5万</div>
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
                    <el-col :span="8" v-for="(o, index) in 4" :key="o" :offset="index > 0 ? 1 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                        <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
                        <div style="padding: 14px;">
                            <h2>未央区</h2>
                            <h2>保利金香槟 2室1厅</h2>
                            <div class="bottom clearfix">
                                <time class="size" style="width:30%">整租</time>
                                <div class="money" style="width:70%">2200元/月</div>
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
export default {
    name:'house',
    data(){
        return{
            activeName: 'second',
        }
    },
    methods:{
        handleClick(tab, event) {
            console.log(tab, event);
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
      padding-top: 36px;
      .wrap{
        padding: 0 50px;
        .wrap-top{
          width: 100%;
          height: 30px;
          line-height: 30px;
          overflow: hidden;
          .fl{
            width: 15%;
            float: left;
            font-size: 24px;
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
        width: 1200px;
        overflow: hidden;
        margin-top: 26px;
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
          width: 100%;
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
          width: 21.5%;
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
