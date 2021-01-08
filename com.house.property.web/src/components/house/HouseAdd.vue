<template>
  <div id="houseAdd">
    <Head @getType="getType"></Head>
    <div class="head">
      <div class="title">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="我要卖房"  name="first"></el-tab-pane>
          <el-tab-pane label="委托出租" name="second"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="m-jumbotron">
      <div class="tit">发布{{type==0?`出售`:`出租`}}房源</div>
      <div class="sub-tit">快速全城推广 · 数万优质用户 · 专业团队服务</div>
    </div>
    <div class="m-form">
      <div class="form-box">
        <div class="box-l m-entry">
            <dl class="compact">
                <dt>小区所在城市</dt>
                <dd>
                  <el-cascader :options="options" style="width:100%" ></el-cascader>
                </dd>
            </dl>
            <dl>
                <dt>小区</dt>
                <dd class="wrap-sug">
                    <div class="u-sug" id="u-sug">
                        <input v-model="formData.residential"  type="text" autocomplete="off" placeholder="请输入小区名">
                    </div>
                </dd>
            </dl>
            <dl>
                <dt>房屋地址</dt>
                <dd>
                    <div class="u-select u-select-build">
                        <input v-model="address1"  placeholder="楼栋号"  type="text" >
                    </div>
                    <div class="u-select u-select-build">
                        <input v-model="address2" placeholder="单元号" type="text">
                    </div>
                    <div class="u-select u-select-build">
                        <input v-model="address3" placeholder="门牌号" type="text">
                    </div>
                </dd>
            </dl>
            <dl>
                <dt>期望{{type==0?'售价':'租金'}}</dt>
                <dd>
                  <input name="expectation_price" type="text" placeholder="请输入您期望的价格" style="width: 210px;">
                  <div class="unit">{{type==0?'万元':'元/月'}}</div>
                </dd>
            </dl>
            <dl v-if="type==1">
                <dt>出租方式</dt>
                <dd>
                    <div class="u-select u-select-build">
                        <el-select v-model="formData.rentType" placeholder="请选择">
                          <el-option
                            v-for="item in options1"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                    </div>
                </dd>
            </dl>
            <dl>
              <dt>称呼</dt>
              <dd>
                <input name="contact" type="text" placeholder="我们应该如何称呼您"  style="width: 250px;">
              </dd>
            </dl>
            <dl>
                <dt>手机号码</dt>
                <dd>
                    <input name="phone" type="text" placeholder="您的联系方式，方便我们及时与您联系" style="width: 250px;">
                </dd>
            </dl>
            <dl>
                <dt>图形验证码</dt>
                <dd>
                    <input name="verify_code" type="text" placeholder="请输入图形验证码中的结果"  style="width: 170px;">
                    <div class="btn-getcode-sms">
                      <img src="/aj/get/imgCode" alt="验证码图片">
                    </div>
                </dd>
            </dl>
        </div>
      </div>
      <p class="tips">提示：您点击“{{type==0?'确认发布':'提交委托'}}”后，若您的房源通过平台初步审核，将会由平台上的经纪人和您取得联系，并对您的房源进行再次核实，核实无误后将与您建立服务关系。贝壳平台仅提供信息展示和网络技术服务。</p>
      <div class="m-submit" @click="handleSubmit()">{{type==0?'确认发布':'提交委托'}}</div>
    </div>
  </div>
</template>

<script>
import Head from "../head/Head/Head";
import api from "../../api/house.api"
export default {
  data() {
      return {
        options1: [{
          value: '1',
          label: '整租'
        }, {
          value: '2',
          label: '合租'
        }],


        house:{

        },
        options:[{
          value: 'zhinan',
          label: '指南',
          children: [{
            value: 'shejiyuanze',
            label: '设计原则',
            }],
        },{
          value: 'zhinan',
          label: '指南',
          children: [{
            value: 'shejiyuanze',
            label: '设计原则',
            }],
        }],
        address1:'',
        address2:'',
        address3:'',

        activeName: 'first',
        type:'0',
        formData:{
          areaId:'',//区域id
          residential:'',//小区
          houseAddress:'',//房屋地址
          unitPrice:'',//单价
          measureArea:'',//面积
          totalPrice:'',//总价
          room:'',//几室
          office:'',//几厅
          orientation:'',//朝向：1-北；2-南；3-西；4-东
          floor:'',//几楼
          buildingAge:'',//楼龄
          purpose:'',//用途：1-普通住宅；2-商业类；3-别墅；4四合院；5-车位；6-其他
          heating:'',//供暖：1-集中供暖；2-自供暖
          type:'',//类型：1:-买卖；2-出租
          rent:'',//租金(每月)
          phoneNumber:'',//手机号码
          userId:'',//用户Id
          rentalType:'1',//出租类型 1:-整租；2-合租
        }
      };
    },
  props: {
  },

  components:{
    Head,
  },
  methods:{
    getType(val){
      this.type=val;
    },
    handleClick(tab, event) {
      if(tab.name==='first') this.type='0';
      if(tab.name==='second') this.type='1';
    },
    handleSubmit(){
      this.initData();
    },
    initData(){
      this.houseAddress=address1+address2+address3;
      api.houseAdd(this.formData).then(res => {
          //console.log(res,'res111111');
          // if(res.code===0){
          //    
          // }else{

          // }
      });

    }
  },
  created(){
    api.getChildArea().then(res => {
      this.options=res.data;
    });
    this.type=this.$route.query.type;
    if(this.type==1)this.activeName='second'
  }
}
</script>

<style lang="less">
#houseAdd{
  width: 100%;
  height: 100%;
  .head{
    height: 87px;
    width: 100%;
    background: #f5f5f6;
    .title{
    width: 1200px;
    margin: 0 auto;
    .el-tabs__active-bar{
      background-color: transparent;
    }
    .el-tabs__nav-wrap::after{
      background-color: transparent;
    }
    .el-tabs__header{
      margin: 0;
      height: 87px;
      line-height: 87px;
    }
    .el-tabs__item{
      font-weight: 600;
      font-size: 16px;
    }
    .el-tabs__item:hover{
      color: #303133;
    }
    .el-tabs__item.is-active{
      color: #3072F6;
    }

  }
  }
  .m-jumbotron {
    padding-top: 20px;
    padding-bottom: 50px;
    line-height: 1;
    text-align: center;
    background-color: #f5f5f6;
    .tit {
    font-weight: 700;
    color: #101d37;
    font-size: 33px;
    }
    .sub-tit {
    margin-top: 15px;
    color: #878787;
    font-size: 16px;
}
  }
  .m-form{
    padding-bottom: 50px;
    .m-entry {
      color: #9399a5;
      margin: 0 auto;
      width: 590px;
      padding-top: 30px;
      dl{
        padding: 12px 0;
        line-height: 40px;
        border-bottom: 1px solid #eee;
        overflow: hidden;
        compact {
          padding: 0 0 25px;
          line-height: 1;
        }
        dt{
          float: left;
          color: #101d37;
          font-weight: 700;
          width: 120px;
        }
        dd{
          position: relative;
          float: left;
          width: 470px;
        }
        dd>*{
          float: left;
        }
        input {
          border: none;
          height: 40px;
          line-height: 20px;
        }
        .u-select{
          position: relative;
          float: left;
          width: 136px;
          height: 40px;
          padding-left: 10px;
          line-height: 20px;
          margin-right: 16px;
          border-radius: 1px;
          color: #aaa;
          font-size: 14px;
        }
        .u-select:last-child{
          margin-right: 0!important;
        }
        .u-select.u-select-build input {
          height: 40px;
          line-height: 40px;
          background-color: transparent;
        }
        .u-select, .u-sug li:hover {
          background-color: #f5f5f6;
        }
        .unit {
          font-weight: 700;
          color: #101d37;
        }
        .btn-getcode-sms {
          float: right;
          width: 145px;
          line-height: 38px;
          text-align: center;
          border: 1px solid #ddd;
          border-radius: 1px;
          cursor: pointer;
          font-size: 12px;
          background-color: #fdfdfd;
          color: #666;
          height: 45px;
          box-sizing: border-box;
        }
        .el-input__inner{
          padding: 0;
        }
      }
    }
    .tips{
      color: #9399a5;
      width: 590px;
      margin: 30px auto 0;
      line-height: 20px;
    }
    .m-submit {
      margin:50px auto 0px;
      width: 190px;
      line-height: 55px;
      font-size: 18px;
      color: #fff;
      text-align: center;
      border-radius: 2px;
      background-color: #3072f6;
      cursor: pointer;
    }
  }
}
</style>
