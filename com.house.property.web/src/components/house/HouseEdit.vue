<template>
  <div id="houseEdit">
    <Head></Head>
    <div class="title">
      <div class="title-inner">
        <h1>{{house.areaName+` `+house.residential}} 高品质，欢迎咨询</h1>
        <p>地铁公交站、教育、户型好、视野开阔、楼间距大{{image.lenght}}</p>
      </div>
    </div>
    <div class="content">
      <div class="content-top">

        <div class="fl" v-if="image.length">
          <img :src="image[0].imageUrl" width="700" >
        </div>
        <el-button style="float: right" @click="doCollect(house.id,house.isCollection)"   size="mini">{{house.isCollection!=='1'?'关注':'已关注'}}</el-button>
        <div class="fl" v-if="!image.length">
          <img src="../../assets/login/login.jpg"  width="700">
        </div>
        <div class="fr">
          <div v-if="house.type==='1'" class="value">
            <span>{{house.totalPrice}}</span>万<p>{{house.unitPrice}}元/平米</p>
          </div>
          <div v-if="house.type==='2'" class="value">
            <span>{{house.rent&&house.rent.substring(0,house.rent.length-3)}}</span>元/月<p>{{house.rentalType==='1'?'整租':'合租'}}</p>
          </div>
          <div class="introduce">
            <ul>
              <li>
                <h3>{{house.room +`室`+house.office+`厅 `}}</h3>
                <p>{{house.floor +`楼`+`/共`+house.sumFloor+`层`}}</p>
              </li>
              <li>
                <h3>{{getOrientation(house.orientation)}}</h3>
                <p>{{house.addTime&&house.addTime.substring(0,10)+`发布`}}</p>
              </li>
              <li>
                <h3>{{house.measureArea}}平米</h3>
                <p>{{(parseInt(house.addTime&&house.addTime.substring(0,4))-parseInt(house.buildingAge))+`年前建`}}</p>
              </li>
            </ul>
          </div>
          <div class="details">
            <ul>
              <li>
                小区名称<i>{{house.residential}}</i>
              </li>
              <li>
                所在区域<i>{{house.areaName}}</i>
              </li>
              <li>
                信息编号<i>{{house.id}}</i>
              </li>
              <li>
                联系电话<i>{{house.phoneNumber}}</i>
              </li>
              <li>
                风险提示
                <el-button type="text" @click="open">用户风险提示</el-button>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="content-bot">
        <div class="tab" >房源基本信息</div>
        <div class="information">
          <div class="basicAttributes">基本属性</div>
          <div class="basicContent">
            <ul>
              <li>房屋户型<span>{{house.room +`室`+house.office+`厅 `}}</span></li>
              <li>所在楼层<span>{{house.floor +`楼`+`/共`+house.sumFloor+`层`}}</span></li>
              <li>建筑面积<span>{{house.measureArea}}m²</span></li>
              <li>房屋朝向<span>{{getOrientation(house.orientation)}}</span></li>
              <li>供暖方式<span>{{house.heating==='1'?'集中供暖':'自供暖'}}</span></li>
              <li>房屋地址<span>{{house.houseAddress}}</span></li>
              <li>挂牌时间<span>{{house.addTime&&house.addTime.substring(0,10)}}</span></li>
              <li>交易权属<span>{{getPurposes(house.purpose)}}</span></li>
            </ul>
          </div>
        </div>
<!--        <div class="information">-->
<!--          <div class="basicAttributes">交易属性</div>-->
<!--          <div class="basicContent">-->
<!--            <ul>-->
<!--              -->
<!--            </ul>-->
<!--          </div>-->
<!--        </div>-->
        <div class="tips">
          特别提示：本房源所示信息仅供参考，购房时请以该房屋档案登记信息、产权证信息以及所签订合同条款约定为准；本房源公示信息不做为合同条款，不具有合同约束力。
        </div>
        <div class="tab">此房源照片</div>
        <div class="demo-image__preview"  v-for="(item, index) in image" :key="index" style="margin-bottom: 20px" >
          <img :src="item.imageUrl" width="700" >
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import Head from "../head/Head/Head";
  import api from "../../api/house.api"
  import collectApi from "../../api/collection.api";
export default {
  components:{
    Head
  },
  data(){
    return{
      purposes: [{value: '1', label: '普通住宅'}, {value: '2', label: '商业类'},
        {value: '3', label: '别墅'}, {value: '4', label: '四合院'},
        {value: '5', label: '车位'}, {value: '6', label: '其他'}],
      orientations:[{value:'1',label:'北'}, {value:'2',label:'南'}, {value:'3',label:'西'},
        {value:'4',label:'东'},{value:'5',label:'南北'}],//朝向：1-北；2-南；3-西；4-东；5-南北

      areaName:'',
      houseId:'',
      userInfo:{},
      house:{areaId:'',//区域id
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
      },
      image:{},
      user:{},
    }
  },
  methods:{
    open() {
        this.$alert('本站旨在为广大用户提供更丰富的信息，但由于部分信息通过技术手段生成，部分信息由第三方提供，我们持续通过技术和管理手段提升信息的准确度，但我们无法确保信息的真实性、准确性和完整性。房产交易滋事体大，本站信息不应作为您买卖决策的依据，您决策前应与房源业主核实相关信息、并亲自到房屋中核验信息，或以产权证明、政府类网站发布的官方信息为准。本站不对您交易过程中对本网站信息产生的依赖承担任何明示或默示的担保责任或任何责任。', '风险提示', {
          confirmButtonText: '确定',
        });
      },
    //关注
    doCollect(houseId,val){
      if(this.userInfo.id){
        let collect = {
          houseId:houseId,
          userId:this.userInfo.id
        };
        if(val==='1'){
          collectApi.deleteCollection(collect).then(res=>{
            if(res.code===0) {
              this.house.isCollection='0';
              this.house.sumCollection--;
            }else{
              this.$message({
                message: res.message,
                type: 'warning'
              })
            }

          })
        }else {
          collectApi.saveCollection(collect).then(res=>{
            if(res.code===0) {
              this.house.isCollection='1';
              this.house.sumCollection++;
            }else{
              this.$message({
                message: res.message,
                type: 'warning'
              })
            }
          })
        }

      }else {
        this.$message({
          message: '请登录',
          type: 'warning'
        });
        this.$router.push('/login')
      }
    },
    getAreaById(val){
      api.getAreaById({id:val}).then(res=>{
        if(res.code===0) {
          this.areaName = res.data.name;
        }else{
          this.$message({
            message: res.message,
            type: 'warning'
          })
        }
      });
    },
    getPurposes(val){
      let purpose = '';
      this.purposes.forEach(item=>{
        if(item.value===val) purpose= item.label
      });
      return purpose;
    },
    getOrientation(val){
      let orientation = '';
      this.orientations.forEach(item=>{
        if(item.value===val) orientation= item.label
      });
      return orientation;
    },
    init(){
      this.houseId=this.$route.query.houseId;
      this.getTableDate();

    },
    getTableDate(){
      api.getHouseById({id:this.houseId,userId:this.userInfo.id}).then(res=>{
        if(res.code===0) {
          this.house=res.data.house;
          this.image=res.data.house.images;
          this.user =res.data.user ;
          //this.getAreaById(this.house.areaId);
        }else{
          this.$message({
            message: res.message,
            type: 'warning'
          })
        }
      })
    }
  },
  created() {
    if (sessionStorage.userInfo) {
      this.userInfo = JSON.parse(sessionStorage.userInfo);
    }
    this.init();
  }

}
</script>

<style lang="less">
#houseEdit{
  .title{
    width: 100%;
    height: 150px;
    background-color: #f5f5f6;
    .title-inner{
      width: 1200px;
      margin: 0 auto;
      h1{
        font-size: 26px;
        color: #101d37;
        font-weight: 700;
        line-height: 40px;
        padding-top: 40px;
      }
      p{
        font-size: 14px;
        color: #aaa;
        line-height: 20px;
        padding-top: 10px;
      }
    }
  }
  .content{
    width: 1200px;
    margin: 0 auto;
    .content-top{
      width: 100%;
      height: 400px;
      overflow: hidden;
      padding-top: 50px;
      .fl{
        width: 700px;
        height: 400px;
        float: left;

        img{
          width: 700px;
          height: 400px;
        }
      }
      .fr{
        width: 450px;
        float: right;
        padding-left: 50px;
        .value{
          font-size: 16px;
          color: #e4393c;
          padding-top:20px;
          span{
            font-family: Tahoma;
            font-size: 42px;
            line-height: 42px;
            color: #fe615a;
            font-weight: 700;
          }
          p{
            display: inline;
            padding-left: 20px;
            font-size: 16px;
            color: #101d37;
          }

        }
        .introduce{
          ul{
            width: 100%;
            overflow: hidden;
            padding: 20px 0;
            border-top: 1px solid #f1f1f1;
            border-bottom: 1px solid #f1f1f1;
            margin-top: 20px;
            li{
              width: 33.33%;
              float: left;
              h3{
                font-size: 22px;
                font-weight: 700;
                color: #101d37;
              }
              p{
                font-size: 12px;
                color: #394043;
              }
            }
          }
        }
        .details{
          ul{
            width: 100%;
            padding: 20px 0;
            li{
              color: #9399a5;
              line-height: 34px;
              i{
                color: #101d37;
                margin-left: 20px;
              }
              span{
                color: #3072f6!important;
                text-decoration:underline;
                margin-left: 20px;
              }
            }
          }
        }
      }
    }
    .content-bot{
      .tab{
        font-weight: 700;
        font-size: 23px;
        line-height: 50px;
        margin-top: 20px;
      }
      .information{
        width: 100%;
        margin: 0 auto;
        overflow: hidden;
        color: #9399a5;
        line-height: 24px;
        font-size: 14px;
        font-weight: 100;
        border-top: 1px solid #eee;
        padding: 20px 0;
        .basicAttributes{
          width: 20%;
          float: left;
        }
        .basicContent{
          width: 80%;
          float: left;
          ul{
            overflow: hidden;
            li{
              width: 50%;
              float: left;
              span{
                color: #101d37;
                padding-left: 30px;
              }
            }
          }
        }
      }
      .tips{
        color: #b0b3b4;
        font-size: 12px;
        padding:10px 0px 30px;
      }
      .demo-image__preview{
        width: 80%;
        margin: 0 auto;
      }

    }

  }

}
</style>
