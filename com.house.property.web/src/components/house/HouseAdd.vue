<template>
  <div id="houseAdd">
    <Head @getType="getType"></Head>
    <div class="head">
      <div class="title">
        <el-tabs v-if="!formData.id" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="我要卖房"  name="first"></el-tab-pane>
          <el-tab-pane label="委托出租" name="second"></el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div class="m-jumbotron">
      <div class="tit">{{(formData.id?`修改已`:'')+(type==='1'?`发布出售`:`发布出租`)}}房源</div>
      <div class="sub-tit">快速全城推广 · 数万优质用户 · 专业团队服务</div>
    </div>
    <div class="m-form">
      <div class="form-box">
        <div class="box-l m-entry">
            <dl class="compact">
                <dt>小区所在城市</dt>
                <dd>
                  <el-cascader v-model="formData.areaId" :options="options" style="width:100%" clearable ></el-cascader>
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
                        <el-input v-model="address1"    type="text" >
                          <span slot="suffix">栋</span>
                        </el-input>
                    </div>
                    <div class="u-select u-select-build">
                      <el-input v-model="address2"  type="text">
                      <span slot="suffix">单元</span>
                      </el-input>
                    </div>
                    <div class="u-select u-select-build">
                      <el-input v-model="address3"  type="text">
                      <span slot="suffix">号</span>
                      </el-input>
                    </div>
                </dd>
            </dl>
          <dl>
            <dt>厅室</dt>
            <dd>
              <div class="u-select u-select-build">
              <el-input v-model="formData.room"  type="text" >
                <span slot="suffix">室</span>
              </el-input>
              </div>
              <div class="u-select u-select-build">
                <el-input v-model="formData.office"   type="text" >
                  <span slot="suffix">厅</span>
                </el-input>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>面积</dt>
            <dd>
              <div class="u-select u-select-build">
                <el-input   v-model="formData.measureArea" type="text" >
                <span slot="suffix">m²</span>
                </el-input>
              </div>
              <div class="u-select u-select-build">
                <el-input v-model="formData.floor"  type="text" >
                <span slot="suffix">楼</span>
                </el-input>
              </div>
              <div class="u-select u-select-build">
                <el-input v-model="formData.sumFloor"    type="text" >
                <span slot="suffix">总楼</span>
                </el-input>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>方位信息</dt>
            <dd>
              <div class="u-select u-select-build">
                <el-select v-model="formData.orientation" placeholder="朝向">
                  <el-option
                    v-for="item in orientations"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
              <div class="u-select u-select-build">
                <el-input v-model="formData.buildingAge"   type="text" >
                <span slot="suffix">楼龄</span>
                </el-input>
              </div>

            </dd>
          </dl>
          <dl>
            <dt>房屋类型</dt>
            <dd>
              <div class="u-select u-select-build">
                <el-select v-model="formData.purpose" placeholder="用途">
                  <el-option
                    v-for="item in purposes"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
              <div class="u-select u-select-build">
                <el-select v-model="formData.heating" placeholder="供暖">
                  <el-option
                    v-for="item in heatings"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
            </dd>
          </dl>

            <dl v-if="type=='1'">
                <dt>期望售价</dt>
                <dd>
                  <input  v-model="formData.totalPrice" name="expectation_price" type="text" placeholder="请输入您期望的价格" style="width: 210px;">
                  <div class="unit">万元</div>
                  <div style="margin-left: 60px">单价约 {{(formData.totalPrice&&formData.measureArea)?(formData.totalPrice/formData.measureArea).toFixed(2):0}} 万元/m²</div>
                </dd>
            </dl>

          <dl v-if="type=='2'">
            <dt>期望租金</dt>
            <dd>
              <input v-model="formData.rent" name="expectation_price" type="text" placeholder="请输入您期望的价格" style="width: 210px;">
              <div class="unit">元/月</div>
            </dd>
          </dl>
            <dl v-if="type=='2'">
                <dt>出租方式</dt>
                <dd>
                  <div class="u-select u-select-build">
                    <el-select v-model="formData.rentalType" placeholder="请选择">
                      <el-option
                        v-for="item in rentalTypes"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </div>
                </dd>
            </dl>

            <dl>
                <dt>手机号码</dt>
                <dd>
                    <input name="phone" v-model="formData.phoneNumber" type="text" placeholder="您的联系方式，方便我们及时与您联系" style="width: 250px;">
                </dd>
            </dl>
            <dl>
                <dt>图形验证码</dt>
                <dd>
                    <input v-model="formData.code" name="verify_code" type="text" placeholder="请输入图形验证码中的结果"  style="width: 170px;">
                    <img   @click="getCode":src="captchaCode" alt="验证码图片" style="width: 100px;height: 50px;float: right;">
                </dd>
            </dl>
          <dl>
            <dt>上传图片</dt>
            <dd>
              <el-upload
                action="#"
                :http-request="uploadSectionFile"
                multiple
                limit="5"
                show-file-list
                :on-remove="handleRemove"
                :file-list="formData.images"
                list-type="picture"
              >
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件最多5张，且不超过1M</div>
                <el-button size="mini" >点击上传</el-button>
              </el-upload>
            </dd>
          </dl>
        </div>
      </div>
      <p class="tips">提示：您点击“{{type==='1'?'确认发布':'提交委托'}}”后，若您的房源通过平台初步审核，将会由平台上的经纪人和您取得联系，并对您的房源进行再次核实，核实无误后将与您建立服务关系。贝壳平台仅提供信息展示和网络技术服务。</p>
      <div class="m-submit" @click="handleSubmit()">{{type==='1'?'确认发布':'提交委托'}}</div>
    </div>
  </div>
</template>

<script>
  import Head from "../head/Head/Head";
  import api from "../../api/house.api"
  import userApi from "../../api/user.api"

  export default {
  data() {
      return {
        rentalTypes: [{value: '1', label: '整租'}, {value: '2', label: '合租'}],
        heatings: [{value: '1', label: '集中供暖'}, {value: '2', label: '自供暖'}],
        orientations: [{value: '5', label: '南北'}, {value: '2', label: '朝南'},{value: '1', label: '朝北'},
          {value: '4', label: '朝东'}, {value: '3', label: '朝西'}],
        purposes: [{value: '1', label: '普通住宅'}, {value: '2', label: '商业类'},
          {value: '3', label: '别墅'}, {value: '4', label: '四合院'},
          {value: '5', label: '车位'}, {value: '6', label: '其他'}],
        userInfo:{},
        options:[],
        address1:'',
        address2:'',
        address3:'',

        activeName: 'first',
        type:'1',
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
          sumFloor:'',//总共几楼
          buildingAge:'',//楼龄
          purpose:'',//用途：1-普通住宅；2-商业类；3-别墅；4四合院；5-车位；6-其他
          heating:'',//供暖：1-集中供暖；2-自供暖
          type:'',//类型：1:-买卖；2-出租
          rent:'',//租金(每月)
          phoneNumber:'',//手机号码
          userId:'',//用户Id
          rentalType:'1',//出租类型 1:-整租；2-合租
          uuid:'',
          code:'',
          images:[],
        },
        uuid:'',
        captchaCode:'',
        uploadFile:[],
      };
    },
  props: {
  },

  components:{
    Head,
  },
  methods:{
    handleRemove(file){
      let index = this.formData.images.findIndex(item =>{
        if(item.name===file.name){
          return true
        }
      });
      this.formData.images.splice(index,1);
    },
    uploadSectionFile(param) {
      let fileObj = param.file;
      const isLt2M = fileObj.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 1MB!");
        return;
      }
      if (fileObj.type === "image/jpeg"||fileObj.type === "image/png") {
        this.getBase64(fileObj).then(res => {
          let file  = {
            imageUrl:res,
            url:res,
            name:fileObj.name
          };
          this.formData.images.push(file);
        });
      }else {
        this.$message.error("只能上传jpg/png文件");
        return;
      }

    },
    getBase64(file) {
      return new Promise(function(resolve, reject) {
        let reader = new FileReader();
        let imgResult = "";
        reader.readAsDataURL(file);
        reader.onload = function() {
          imgResult = reader.result;
        };
        reader.onerror = function(error) {
          reject(error);
        };
        reader.onloadend = function() {
          resolve(imgResult);
        };
      });
    },
    getCode() {
      userApi.getCode({width: 200, height: 50}).then(res => {
        this.captchaCode = res.data.captchaCode;
        this.uuid = res.data.uuid;
      });
    },
    getType(val){
      this.type=val;
      if(this.type=='1')this.activeName='first';
      if(this.type=='2')this.activeName='second';
    },
    handleClick(tab, event) {
      if(tab.name==='first') {
        this.type='1';
      }
      if(tab.name==='second') {
        this.type='2';
      }
    },
    handleSubmit(){
      this.initData();
    },
    getAddress(){
      if(this.formData.houseAddress){
        this.address1=this.formData.houseAddress.substring(0,this.formData.houseAddress.indexOf('栋'));
        this.address2=this.formData.houseAddress.substring(this.formData.houseAddress.indexOf('栋')+1,this.formData.houseAddress.indexOf('单元'));
        this.address3=this.formData.houseAddress.substring(this.formData.houseAddress.indexOf('元')+1,this.formData.houseAddress.indexOf('号'));
        this.formData.houseAddress='';
      }else {
        this.formData.houseAddress=this.address1+(this.address1.indexOf('栋')===-1?'栋':'')+this.address2
          +(this.address1.indexOf('单元')===-1?'单元':'')+this.address3+(this.address1.indexOf('号')===-1?'号':'');
      }
    },
    initData(){
      this.getAddress();
      this.formData.userId=this.userInfo.id;
      this.formData.type=this.type;
      this.formData.areaId=this.formData.areaId.length===0?'0':this.formData.areaId[this.formData.areaId.length-1];
      this.formData.unitPrice = (this.formData.totalPrice/this.formData.measureArea).toFixed(2);
      if(this.formData.type==='2'&&!this.formData.totalPrice)this.formData.totalPrice='0';
      if(this.formData.type==='1'&&!this.formData.rent)this.formData.rent='0';
      if(this.uuid)this.formData.uuid=this.uuid;
      for(let key in this.formData) {
        if(key==='sumCollection'||key==='images'||key==='isCollection') continue;
        if (!this.formData[key]){
          this.$message.error("有必填字段需要填写"+key);
          return ;
        }
      }
      if(this.formData.id){
        this.updateHouse();
      }else{
        this.saveHouse();
      }
    },

    saveHouse(){
      api.houseAdd(this.formData).then(res => {
        if(res.code==0){
          this.$message({
            type: 'success',
            message: '新增成功'
          });
          this.$router.push('/')
        }else{
          this.$message({
            message: res.message,
            type: 'warning'
          })
        }
      });
    },
    updateHouse(){
      api.houseUpdate(this.formData).then(res => {
        if(res.code==0){
          this.$message({
            type: 'success',
            message: '更新成功'
          });
          this.$router.push('/')
        }else{
          this.$message({
            message: res.message,
            type: 'warning'
          })
        }
      });
    },
    init(houseId){
      if(houseId){
        api.getHouseById({id:houseId}).then(res=>{
          if(res.code===0) {
            this.formData=res.data.house;
            this.uploadFile=res.data.house.images;
            this.getCode();
            this.getAddress();
            this.formData.images.forEach(item=>{
              if(item.imageUrl) item.url=item.imageUrl;
            });
          }else{
            this.$message({
              message: res.message,
              type: 'warning'
            })
          }
        })
      }
      else {
        this.getCode();
      }
    },
  },
  created(){
    if (sessionStorage.userInfo) {
      this.userInfo = JSON.parse(sessionStorage.userInfo);
    }
    api.getChildArea().then(res => {
      this.options=res.data;
    });
    this.type=this.$route.query.type;
    if(this.type=='1')this.activeName='first';
    if(this.type=='2')this.activeName='second';
    this.init(this.$route.query.houseId);

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
          width: 100px;
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
