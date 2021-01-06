<template>
  <div id="houseBuy">
      <Head></Head>
      <div class="search">
        <div class="search-inner">
            <el-input placeholder="请输入小区名开始找房" v-model="input1" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
        </div>
      </div>
      <div class="filter">
          <div class="position">
              <dl>
                  <dt style="margin-top: 5px;">位置</dt>
                <dd>
                  <el-cascader :options="options" style="width:35%" clearable ></el-cascader>
                </dd>
              </dl>
          </div>
          <div class="list-more">
              <dl>
                  <dt>价格</dt>
                  <dd>
                    <el-checkbox-group v-model="checkedPrice">
                        <el-checkbox v-for="item in price" :label="item" :key="item">{{item+`万`}}</el-checkbox>
                    </el-checkbox-group>
                  </dd>
              </dl>
              <dl>
                  <dt>房型</dt>
                  <dd>
                    <el-checkbox-group v-model="checkedHouseType">
                        <el-checkbox v-for="item in houseType" :label="item" :key="item">{{item}}</el-checkbox>
                    </el-checkbox-group>
                  </dd>
              </dl>
              <dl>
                  <dt>建筑面积</dt>
                  <dd>
                    <el-checkbox-group v-model="checkedArea">
                        <el-checkbox v-for="item in area" :label="item" :key="item">{{item+`m²`}}</el-checkbox>
                    </el-checkbox-group>
                  </dd>
              </dl>
          </div>
      </div>
  </div>
</template>

<script>
import Head from "../head/Head/Head";
const priceOptions = ['0-40', '40-60', '60-80', '80-100', '100-150', '150-200'];
const houseTypeOptions = ['一室', '两室', '三室', '四室', '五室', '五室以上'];
const areaOptions = ['0-50', '50-70', '70-90', '90-120', '120-150', '150-200'];
import api from "../../api/house.api"
export default {
    components:{
        Head,
    },
    data(){
        return{
          priceOptions :['0-40', '40-60', '60-80', '80-100', '100-150', '150-200'],
          houseTypeOptions : ['一室', '两室', '三室', '四室', '五室', '五室以上'],
          areaOptions : ['0-50', '50-70', '70-90', '90-120', '120-150', '150-200'],
          input1: '',
          checkedPrice: [],
          price: priceOptions,
          checkedHouseType:[],
          houseType:houseTypeOptions,
          checkedArea:[],
          area:areaOptions,
          options:[],
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
          border: 0px;
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
