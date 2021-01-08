<template>
  <div id="houseBuy">
      <Head></Head>
      <div class="search">
        <div class="search-inner">
            <el-input placeholder="请输入小区名开始找房" v-model="formData.residential" class="input-with-select">
                <el-button slot="append" @click="select" icon="el-icon-search"></el-button>
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
              <dl>
                  <dt>价格</dt>
                  <dd>
                    <el-checkbox-group v-model="formData.totalPrice">
                        <el-checkbox v-for="item in price" :label="item.value" :key="item.value">{{item.label+`万`}}</el-checkbox>
                    </el-checkbox-group>
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
          </div>
      </div>
  </div>
</template>

<script>
import Head from "../head/Head/Head";
import api from "../../api/house.api"

export default {
    components:{
        Head,
    },
    data(){
        return{
          price :[{value:'0-400000',label:'0-40'}, {value:'400000-600000',label:'40-60'}, {value:'600000-800000',label:'60-80'},
            {value:'800000-1000000',label:'80-100'}, {value:'1000000-1500000',label:'100-150'}, {value:'1500000-2000000',label:'150-200'}],
          houseType : [{value:1,label:'一室'}, {value:2,label:'两室'}, {value:3,label:'三室'},
            {value:4,label:'四室'}, {value:5,label:'五室'}, {value:6,label:'五室以上'}],
          size : ['0-50', '50-70', '70-90', '90-120', '120-150', '150-200'],
          orientations:[{value:1,label:'北'}, {value:2,label:'南'}, {value:3,label:'西'},
            {value:4,label:'东'}],//朝向：1-北；2-南；3-西；4-东
          buildingAges:['0-5', '5-10', '10-15', '15-20', '20-100'],//楼龄
          purposes:[{value:1,label:'普通住宅'}, {value:2,label:'商业类'}, {value:3,label:'别墅'},
            {value:4,label:'四合院'},{value:5,label:'车位'},{value:6,label:'其他'}],
          //用途：1-普通住宅；2-商业类；3-别墅；4-四合院；5-车位；6-其他
          heatings:[{value:1,label:'集中供暖'}, {value:2,label:'自供暖'}],//供暖：1-集中供暖；2-自供暖
          options:[],
          formData:{
            residential: '',//小区
            totalPrice: [],//价格
            room:[],//几室
            measureArea:[],//大小
            areaId:[],//区域
            orientation:[],//朝向
            buildingAge:[],//楼龄
            purpose:[],//用途
            heating:[],//供暖
            current:1,
            size:10,
          }
        }
    },
  watch:{
  },
  methods:{
    select(){
      api.getHouseByQuery(this.formData).then(res=>{
      })
    }
  },
  created() {
    api.getChildArea().then(res => {
      this.options=res.data;
    });
  }
}
</script>

<style lang="less">
#houseBuy{
    .search{
        height: 100px;
        background-color: #f5f5f6;
        .search-inner{
            width: 1200px;
            height: 46px;
            padding-top: 27px;
            margin: 0 auto;
            .el-input-group{
                width: 710px;
            }
        }
    }
    .filter{
        width: 1160px;
        background-color: #f5f5f6;
        margin: 20px auto;
        padding: 10px 20px;
        font-size: 12px;
        .el-input__inner{
          border: 0;
          background-color: #f5f5f6;
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
                .el-checkbox{
                    width: 160px;
                    margin: 0;
                }
                .el-checkbox__label{
                    font-size: 12px;
                }
            }
        }
    }
}
</style>
