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
          <div class="details"></div>
          
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
      }
    }
  }

}
</style>
