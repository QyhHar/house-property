<template>
  <div id="houseAdd">
    <!--<Head></Head>-->
    <div class="head">
      <div class="title">
        <ul>
          <li @click="$router.push('/houseAdd')">我要卖房</li>
          <li @click="$router.push('/houseAdd')">委托出租</li>
        </ul>
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
#houseAdd{
  width: 100%;
  height: 100%;
  .head{
    height: 87px;
    width: 100%;
    background: #f5f5f6;
  }
  .title{
    width: 20%;
    float: left;
    ul{
      width: 70%;
      float: left;
      text-align: right;
      li{
        font-size: 16px;
        list-style: none;
        display: inline;
        padding: 0 20px;
        cursor: pointer;
      }
    }
  }
}
</style>
