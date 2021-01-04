<template>
  <div id="houseEdit">
    <Head></Head>
    <div class="title">
      <div class="title-inner">
        <h1>三桥地铁口，华润央企大楼盘高品质，刚需楼盘欢饮咨询</h1>
        <p>地铁公交站、教育、户型好、视野开阔、楼间距大</p>
      </div>
    </div>
    <div class="content">
      <div class="content-top">
        <div class="fl">
          <img src="../../assets/houseEdit/house-type-img.png">
        </div>
        <div class="fr">
          <div class="value">
            <span>154.5</span>万<p>17199元/平米</p>
          </div>
          <div class="introduce">
            <ul>
              <li>
                <h3>3室2厅</h3>
                <p>低楼层/共33层</p>
              </li>
              <li>
                <h3>南</h3>
                <p>平层/精装</p>
              </li>
              <li>
                <h3>135平米</h3>
                <p>2016年建/板塔结合</p>
              </li>
            </ul>
          </div>
          <div class="details">
            <ul>
              <li>
                小区名称<i>华润二十四城</i>
              </li>
              <li>
                所在区域<i>未央</i>
              </li>
              <li>
                信息编号<i>111113333</i>
              </li>
              <li>
                联系电话<i>13649556688</i>
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
              <li>房屋户型<span>3室2厅2卫</span></li>
              <li>所在楼层<span>低楼层（共33层）</span></li>
              <li>建筑面积<span>120.8㎡</span></li>
              <li>户型结构<span>平层</span></li>
              <li>建筑类型<span>板塔结合</span></li>
              <li>房屋朝向<span>南</span></li>
              <li>建筑结构<span>钢混结构</span></li>
              <li>装修情况<span>精装</span></li>
              <li>梯户比例<span>两梯四户</span></li>
              <li>供暖方式<span>集中供暖</span></li>
              <li>配备电梯<span>有</span></li>
            </ul>
          </div>
        </div>
        <div class="information">
          <div class="basicAttributes">交易属性</div>
          <div class="basicContent">
            <ul>
              <li>挂牌时间<span>2020年12月23日</span></li>
              <li>交易权属<span>商品楼</span></li>
              <li>上次交易<span>暂无数据</span></li>
              <li>房屋用途<span>普通住宅</span></li>
              <li>房屋年限<span>暂无数据</span></li>
              <li>产权所有<span>共有</span></li>
              <li>抵押信息<span>有抵押</span></li>
              <li>房本备件<span>详情咨询卖家</span></li>
            </ul>
          </div>
        </div>
        <div class="tips">
          特别提示：本房源所示信息仅供参考，购房时请以该房屋档案登记信息、产权证信息以及所签订合同条款约定为准；本房源公示信息不做为合同条款，不具有合同约束力。
        </div>
        <div class="tab">此房源照片</div>
        <div class="demo-image__preview">
          <el-image 
            style="width: 100%; height:400px"
            :src="url" 
            :preview-src-list="srcList">
          </el-image>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
  import Head from "../head/Head/Head";
export default {
  components:{
    Head
  },
  methods:{
    open() {
        this.$alert('本站旨在为广大用户提供更丰富的信息，但由于部分信息通过技术手段生成，部分信息由第三方提供，我们持续通过技术和管理手段提升信息的准确度，但我们无法确保信息的真实性、准确性和完整性。房产交易滋事体大，本站信息不应作为您买卖决策的依据，您决策前应与房源业主核实相关信息、并亲自到房屋中核验信息，或以产权证明、政府类网站发布的官方信息为准。本站不对您交易过程中对本网站信息产生的依赖承担任何明示或默示的担保责任或任何责任。', '风险提示', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${ action }`
            });
          }
        });
      }
  },
  data(){
    return{
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      
      srcList: [
        'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
      ]
    }
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
        line-height: 74px;
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
