<template>
  <div id="houseBuy">
      <Head @getType="getType"></Head>
      <div class="search">
        <div class="search-inner">
            <el-input placeholder="请输入小区名开始找房" v-model="formData.residential" class="input-with-select">
                <el-button slot="append" @click="submit" icon="el-icon-search"></el-button>
            </el-input>
        </div>
      </div>
      <div class="filter">
          <div class="position">
              <dl>
                  <dt style="margin-top: 5px;">位置</dt>
                <dd>
                  <el-cascader
                    v-model="formData.areaId" :options="options" style="width:35%" clearable
                    :props="{ checkStrictly: true,multiple:true }"
                  ></el-cascader>
                </dd>
              </dl>
          </div>
          <div class="list-more">
              <dl v-if="type==='1'">
                  <dt>价格</dt>
                  <dd>
                    <div  class="checkbox" >
                      <el-checkbox-group v-model="formData.totalPrice" >
                        <el-checkbox v-for="item in price" :label="item.value" :key="item.value">{{item.label+`万`}}</el-checkbox>
                      </el-checkbox-group>
                      <span class="price"  v-if="!moneyPrice" @click="moneyPrice=true">+自定义价格</span>
                      <div v-if="moneyPrice" >
                        <input class="priceInput"    v-model="minPrice"></input> -
                        <input class="priceInput"   v-model="maxPrice" ></input> 万
                      </div>
                    </div>
                  </dd>

              </dl>
              <dl v-if="type==='2'">
                  <dt >租金</dt>
                  <dd>
                    <div  class="checkbox" >
                      <el-checkbox-group v-model="formData.rent" >
                        <el-checkbox v-for="item in rents" :label="item" :key="item">{{item+`元/月`}}</el-checkbox>
                      </el-checkbox-group>
                      <span class="price"  v-if="!moneyRent" @click="moneyRent=true">+自定义价格</span>
                      <div v-if="moneyRent" >
                        <input class="priceInput"    v-model="minRent"></input> -
                        <input class="priceInput"   v-model="maxRent" ></input> 元/月
                      </div>
                    </div>
                  </dd>

              </dl>
              <dl>
                  <dt>房型</dt>
                  <dd>
                    <el-checkbox-group v-model="formData.room">
                        <el-checkbox v-for="item in houseType" :label="item.value" :key="item.value">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                  </dd>
              </dl>
              <dl>
                  <dt>建筑面积</dt>
                  <dd>
                    <el-checkbox-group v-model="formData.measureArea">
                        <el-checkbox v-for="item in size" :label="item" :key="item">{{item+`m²`}}</el-checkbox>
                    </el-checkbox-group>
                    <span  class="price" v-if="!moneySize" @click="moneySize=true" >+自定义面积</span>
                    <div v-if="moneySize" >
                      <input class="priceInput"    v-model="minSize"></input> -
                      <input class="priceInput"   v-model="maxSize" ></input> m²
                    </div>
                  </dd>
              </dl>
            <dl>
              <dt>朝向</dt>
              <dd>
                <el-checkbox-group v-model="formData.orientation">
                  <el-checkbox v-for="item in orientations" :label="item.value" :key="item.value">{{item.label}}</el-checkbox>
                </el-checkbox-group>
              </dd>
            </dl>
            <dl>
              <dt>楼龄</dt>
              <dd>
                <el-checkbox-group v-model="formData.buildingAge">
                  <el-checkbox v-for="item in buildingAges" :label="item" :key="item">{{item+`年`}}</el-checkbox>
                </el-checkbox-group>
              </dd>
            </dl>
            <dl>
              <dt>用途</dt>
              <dd>
                <el-checkbox-group v-model="formData.purpose">
                  <el-checkbox v-for="item in purposes" :label="item.value" :key="item.value">{{item.label}}</el-checkbox>
                </el-checkbox-group>
              </dd>
            </dl>
            <dl>
              <dt>供暖</dt>
              <dd>
                <el-checkbox-group v-model="formData.heating">
                  <el-checkbox v-for="item in heatings" :label="item.value" :key="item.value">{{item.label}}</el-checkbox>
                </el-checkbox-group>
              </dd>
            </dl>
          <dl  v-if="type==='2'">
              <dt >出租方式</dt>
              <dd>
                <el-checkbox-group v-model="formData.rentalType">
                  <el-checkbox v-for="item in rentalTypes" :label="item.value" :key="item.value">{{item.label}}</el-checkbox>
                </el-checkbox-group>
              </dd>
            </dl>
          </div>
      </div>
    <div class="content">

      <el-menu
        :default-active="'0'"
        background-color="#f5f5f6"
        text-color="#000"
        active-text-color="#3072f6"
        active-background-color="#fff"
         class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item  index="0">默认排序</el-menu-item>
        <el-menu-item index="1">最新发布</el-menu-item>
        <el-menu-item v-if="type==='2'" index="5">租金<span v-if="selectKey==='5'"><i v-if="selectType==='5-1'" class="el-icon-top"></i><i v-if="selectType==='5-2'" class="el-icon-bottom"></i></span></el-menu-item>
        <el-menu-item v-if="type==='1'" index="2">总价<span v-if="selectKey==='2'"><i v-if="selectType==='2-1'" class="el-icon-top"></i><i v-if="selectType==='2-2'" class="el-icon-bottom"></i></span></el-menu-item>
        <el-menu-item v-if="type==='1'" index="3">单价<span v-if="selectKey==='3'"><i v-if="selectType==='3-1'" class="el-icon-top"></i><i v-if="selectType==='3-2'" class="el-icon-bottom"></i></span></el-menu-item>
        <el-menu-item index="4">面积<span v-if="selectKey==='4'"><i v-if="selectType==='4-1'" class="el-icon-top"></i><i v-if="selectType==='4-2'" class="el-icon-bottom"></i></span></el-menu-item>
        </el-menu>
      <div class="sum">{{`共找到 `+((page.total==='')?'0':page.total)+` 套房`}}</div>
      <ul class="content-list">
        <li class="content-one" v-for="(item, index) in page.records" :key="index" >
          <div class="content-img" v-if="item.images.length">
          <img @click="goHouseEdit(item.id)" :src="item.images[0].imageUrl"  width="300" height="200">
          </div>
          <div class="content-img" v-if="!item.images.length">
            <img @click="goHouseEdit(item.id)" src="../../assets/login/login.jpg" width="300" height="200">
          </div>
          <el-button style="float: right" @click="doCollect(item.id,item.isCollection)"   size="mini">{{item.isCollection!=='1'?'关注':'已关注'}}</el-button>
          <div class="content-text">
            <div class="content-title" @click="goHouseEdit(item.id)">
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
              <div class="totalPrice">{{type==='1'?item.totalPrice+`万`:item.rent.substring(0,item.rent.length-3)+`元/月`}}</div>
              <div v-if="type==='1'"  class="unitPrice">{{`单价`+item.unitPrice+`元/平米`}}</div>
              <div v-if="type==='2'"  class="rent">{{item.rentalType==='1'?'整租':'合租'}}</div>
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
            //出售独有属性
            totalPrice: [],//价格

            type:'',
            room:[],//几室
            measureArea:[],//大小
            areaId:[],//区域
            orientation:[],//朝向
            buildingAge:[],//楼龄
            purpose:[],//用途
            heating:[],//供暖
            current:1,
            size:10,
            asc:'',
            desc:'',
            collectionUserId:'',

            //出租独有属性
            rent:[],
            rentalType:'',
          },
          type:'1',
          userInfo:{},
          selectKey:'',
          selectType:'',


          moneyPrice:false,
          minPrice:'',
          maxPrice:'',

          moneyRent:false,
          minRent:'',
          maxRent:'',



          moneySize:false,
          minSize:'',
          maxSize:'',
          page:{
            total:0,
            records:'',
          },
        }
    },
  watch:{
  },
  methods:{
      goHouseEdit(houseId){
        this.$router.push({
          path: "/houseEdit",
          query: { houseId: houseId },
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
              this.page.records.forEach(item=>{
                if(item.id===houseId) {
                  item.isCollection='0';
                  item.sumCollection--;
                }
              })
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
              this.page.records.forEach(item=>{
                if(item.id===houseId) {
                  item.isCollection='1';
                  item.sumCollection++;
                }
              })
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
    getType(val){
      this.type=val;
      this.init();
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
      if(this.minPrice!==''&&this.maxPrice){
        this.formData.totalPrice.push(this.minPrice+'-'+this.maxPrice);
      }
      if(this.minSize!==''&&this.maxSize){
        this.formData.measureArea.push(this.minSize+'-'+this.maxSize);
      }
      if(this.minRent!==''&&this.maxRent){
        this.formData.rent.push(this.minRent+'-'+this.maxRent);
      }
      this.formData.collectionUserId=this.userInfo.id;
      this.formData.type=this.type;
      if(this.type==='1'){
        this.formData.rent=[];
        this.formData.rentalType='';
      }else {
        this.formData.totalPrice=[];
      }
      api.getHouseByQuery(this.formData).then(res=>{
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
    handleSelect(key, keyPath){
      this.selectKey = key;
      this.selectType=this.selectType===key+'-'+'1'?key+'-'+'2':key+'-'+'1';
      if(key==='1') this.formData.desc='addTime';
      else {
        if(this.selectType.indexOf('1')!==-1){
          this.formData.desc='';
          if(key==='2')this.formData.asc='totalPrice';
          if(key==='3')this.formData.asc='unitPrice';
          if(key==='4')this.formData.asc='measureArea';
          if(key==='5')this.formData.asc='rent';
        }else {
          this.formData.asc='';
          if(key==='2')this.formData.desc='totalPrice';
          if(key==='3')this.formData.desc='unitPrice';
          if(key==='4')this.formData.desc='measureArea';
          if(key==='5')this.formData.desc='rent';
        }
      }
      this.select();
    },
  },
  created() {
    api.getChildArea().then(res => {
      this.options=res.data;
    });
    if (sessionStorage.userInfo) {
      this.userInfo = JSON.parse(sessionStorage.userInfo);
    }
    this.type=this.$route.query.type;
    this.formData.residential=this.$route.query.residential;
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
