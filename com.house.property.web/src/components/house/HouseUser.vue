<template>
  <div id="houseBuy">
      <Head ></Head>
      <div class="search">
        <div class="search-inner">
            <el-input placeholder="请输入小区名开始找房" v-model="formData.residential" class="input-with-select">
                <el-button slot="append" @click="submit" icon="el-icon-search"></el-button>
            </el-input>
        </div>
      </div>
    <div class="content">
      <div class="sum">{{`您发布的房源共 `+((page.total==='')?'0':page.total)+` 套`}}</div>
      <ul class="content-list">
        <li class="content-one" v-for="(item, index) in page.records" :key="index" >
          <div class="content-img" v-if="item.images">
          <img @click="goHouseAdd(item.id,item.type)" :src="item.images[0].imageUrl"  width="300" height="200">
          </div>
          <div class="content-img" v-if="!item.images">
            <img @click="goHouseAdd(item.id,item.type)" src="../../assets/login/login.jpg" width="300" height="200">
          </div>
<!--          <el-button style="float: right" @click="doCollect(item.id,item.isCollection)"   size="mini">{{item.isCollection!=='1'?'关注':'已关注'}}</el-button>-->
          <div class="content-text">
            <div class="content-title" @click="goHouseAdd(item.id,item.type)">
              {{item.residential+` `+item.room +`室`+item.office+`厅 `+getOrientation(item.orientation)}}
            </div>
            <div class="text" style="margin-top: 25px">
              <div class="icon"><i  class="el-icon-location-outline"></i><span  style="margin-left: 10px">{{item.residential}}</span></div>
            </div><br>
            <div class="text">
              <div class="icon"><i  class="el-icon-house"></i><span style="margin-left: 10px">{{item.residential+` `+item.room +`室`+item.office+`厅 | `+item.measureArea+`m²`}}</span></div>
            </div><br>
            <div class="text">
              <div class="icon"><i  class="el-icon-star-off"></i><span style="margin-left: 10px">{{item.sumCollection+`人关注 / `+item.addTime.substring(0,10)+` 发布`}}</span></div>
            </div>
            <div class="content-price">
              <div class="totalPrice">{{item.type==='1'?item.totalPrice+`万`:item.rent.substring(0,item.rent.length-3)+`元/月`}}</div>
              <div v-if="item.type==='1'"  class="unitPrice">{{`单价`+item.unitPrice+`元/平米`}}</div>
              <div v-if="item.type==='2'"  class="rent">{{item.rentalType==='1'?'整租':'合租'}}</div>
            </div>
          </div>
        </li>
      </ul>
      <div v-if="page.total" style="text-align: center;margin-bottom: 100px;margin-top: 30px;">
        <el-pagination
          background
          @current-change="currentChange"
          layout="total, prev, pager, next, jumper"
          :total="page.total">
        </el-pagination>
      </div>

    </div>
  </div>
</template>

<script>
import Head from "../head/Head/Head";
import api from "../../api/house.api"
import collectApi from "../../api/collection.api"

export default {
    components:{
        Head,
    },
    data(){
        return{
          rents :['0-1000', '1000-2000', '2000-3000', '3000-4000', '4000-5000', '5000-6000'],
          price :[{value:'0-40',label:'0-40'}, {value:'40-60',label:'40-60'}, {value:'60-80',label:'60-80'},
            {value:'80-100',label:'80-100'}, {value:'100-150',label:'100-150'}, {value:'150-200',label:'150-200'}],
          houseType : [{value:'1',label:'一室'}, {value:'2',label:'两室'}, {value:'3',label:'三室'},
            {value:'4',label:'四室'}, {value:'5',label:'五室'}, {value:'6',label:'五室以上'}],
          size : ['0-50', '50-70', '70-90', '90-120', '120-150', '150-200'],
          orientations:[{value:'1',label:'北'}, {value:'2',label:'南'}, {value:'3',label:'西'},
            {value:'4',label:'东'},{value:'5',label:'南北'}],//朝向：1-北；2-南；3-西；4-东；5-南北
          buildingAges:['0-5', '5-10', '10-15', '15-20', '20-100'],//楼龄
          purposes:[{value:'1',label:'普通住宅'}, {value:'2',label:'商业类'}, {value:'3',label:'别墅'},
            {value:'4',label:'四合院'},{value:'5',label:'车位'},{value:'6',label:'其他'}],
          //用途：1-普通住宅；2-商业类；3-别墅；4-四合院；5-车位；6-其他
          heatings:[{value:'1',label:'集中供暖'}, {value:'2',label:'自供暖'}],//供暖：1-集中供暖；2-自供暖
          rentalTypes:[{value:'1',label:'整租'}, {value:'2',label:'合租'}],//供暖：1-集中供暖；2-自供暖
          options:[],
          formData:{
            residential: '',//小区
            current:1,
            size:10,
            userId:'',
          },
          userInfo:{},
          page:{
            total:0,
            records:'',
          },
        }
    },
  watch:{
  },
  methods:{
    goHouseAdd(houseId,type){
        this.$router.push({
          path: "/houseAdd",
          query: { houseId: houseId,type: type},
        });
      },
    init(){
      this.select();
    },
    getOrientation(val){
        let orientation = '';
        this.orientations.forEach(item=>{
          if(item.value===val) orientation= item.label
        });
        return orientation;
      },
    submit(){
      this.select();
    },
    currentChange(current){
      this.formData.current=current;
      this.select();
    },
    sizeChange(size){
      this.formData.size=size;
      this.select();
    },
    select(){
      this.page = {
        total:0,
        records:'',};
      this.formData.userId=this.userInfo.id;
      api.getHouseByUserId(this.formData).then(res=>{
        if(res.code===0) {
          this.page = res.data;
        }else{
          this.$message({
            message: res.message,
            type: 'warning'
          })
        }
      })
    },
  },
  created() {
    api.getChildArea().then(res => {
      this.options=res.data;
    });
    if (sessionStorage.userInfo) {
      this.userInfo = JSON.parse(sessionStorage.userInfo);
    }
    this.init();
  }
}
</script>

<style lang="less">
#houseBuy{
   .search{
        height: 100px;
        background-color: #f5f5f6;
        .search-inner{
            width: 1160px;
            height: 46px;
            padding-top: 27px;
            margin: 0 auto;
            .el-input-group{
                width: 710px;
            }
        }
    }
  .filter{
        width: 1120px;
        background-color: #f5f5f6;
        margin: 20px auto;
        padding: 10px 20px;
        font-size: 12px;
       .priceInput{
         width: 50px;height:20px;background-color: white;
       }
        dl{
            overflow: hidden;
            dt{
                float: left;
                width: 5%;
                font-weight: 700;
                line-height: 28px;
                color: #333;
            }
            dd{
                width: 95%;
                float: left;
                line-height: 28px;
                font-size: 12px;
              .el-checkbox-group{
                width: 85%;
                float: left;
              }
                .el-checkbox{
                    width: 150px;
                    margin: 0;
                }
                .el-checkbox__label{
                  font-size: 12px;
                }
            }
          .el-input__inner{
            border: 0;
            background-color: #f5f5f6;
          }
          .checkbox{
            width: 100%; overflow: hidden;
            .price{
              width: 12%; float: left;
            }
          }
        }
    }
  .content{
    .content-list{
      border-bottom: 1px solid #f1f1f1;
      border-top: 1px solid #f1f1f1;
      width: 85%;
      .content-one{
        margin-top: 20px;
        border-bottom: 1px solid #f1f1f1;
        height: 225px;
        .content-img{
          float: left;
        }

        .content-text{
          margin-left: 35px;
          margin-top: 5px;
          font-size: 22px;
          color: #101d37;
          float: left;
          width: 65%;
          .content-title{
            cursor:pointer;
            font-weight: 700;
            font-size: 22px;
            margin-top: 10px;
            margin-left: 5px;
          }
          .content-title:hover{
            color: blue;
          }
        }
          .text{
            margin-top: 15px;
            font-size: 14px;
            .icon{
              margin-left: 5px;float: left;
            }
          }
        .collection{
          float: right;
        }
        .content-price{
          text-align: right;
          float: right;
          margin-top: -50px;
          width: 200px;
          .totalPrice{
            font-size: 22px;
            color: red;
            font-weight: 700;
          }
          .unitPrice{
            font-size: 14px;
            margin-top: 10px;
            color: black;
          }
          .rent{
            font-size: 16px;
            margin-top: 10px;
            color: black;
          }
        }

      }
    }
    .sum{
      width: 100%;
      height: 50px;
      font-weight: 700;
      font-size: 22px;
      color: #101d37;
      margin-top: 20px;
    }
    .el-menu{
      .el-menu-item{
        font-weight: 700;
        width: 100px;
        text-align: center;
      }
    }

    margin: 0 auto ;
    line-height: 1;
    width: 1160px;
    overflow: visible;
  }
}
</style>
