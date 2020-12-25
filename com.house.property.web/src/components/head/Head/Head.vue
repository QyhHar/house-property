<template>
      <div id="header">
        <div class="inner">
          <ul>
            <li>买房</li>
            <li>卖房</li>
            <li>租房</li>
            <li>出租</li>
          </ul>
          <div class="loginReg">
            <svg-icon icon-class="head-icon"></svg-icon>
            <i>登陆</i> / <i>注册</i>
          </div>
        </div>
      </div>
</template>


<script>
    export default {}
</script>
<style lang="less" >
#header{
  width: 100%;
  height: 38px;
  line-height: 38px;
  background-color: #101d37;
  font-size: 12px;
  .inner{
    width: 1200px;
    margin: 0 auto;
    overflow: hidden;
    ul{
      width: 70%;
      float: left;
      li{
        display: inline;
        color: #a9abab;
        margin: 0 20px;
        
      }
      li:hover{
        cursor: pointer;
        color: rgb(228, 228, 228);
      }
    }
    .loginReg{
      width: 30%;
      float: right;
      color: rgb(228, 228, 228);
      text-align: right;
      i{
        font-style: normal;
        cursor: pointer;
      }
      .svg-icon{
        width: 1.3em;
        height: 1.3em;
      }

    }
  }
}
</style>
