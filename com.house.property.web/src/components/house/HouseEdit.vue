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
          <img src="https://ke-image.ljcdn.com/110000-inspection/apc_ib7f9jRSU_1.jpg!m_fill,w_710,h_400,lg_north_west,lx_0,ly_0,l_fbk,f_jpg,ls_50?from=ke.com">
        </div>
        <div class="fr">
          <div class="value">
            <span>154.5</span>万
          </div>
          
          
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
      .fl{
        width: 700px;
        height: 400px;
        float: left;
      }
      .fr{
        width: 450px;
        float: right;
        padding-left: 50px;
        .value{
          padding: 30px 0;
          font-size: 16px;
          color: #e4393c;
          span{
            font-family: Tahoma;
            font-size: 42px;
            line-height: 42px;
            color: #fe615a;
            font-weight: 700;
          }
          
        }
      }
    }
  }

}
</style>
