<template>
    <router-view/>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  * {
    padding: 0;
    margin: 0;
    border: 0;
    font-size: 100%;
  }

  html {
    overflow-x: hidden;
    height: 100%;
  }
  body {
  //设置div内的内容不能被选中
  -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    width: 100vw;
    font-family: PingFangSC,Hiragino Sans GB,Microsoft Yahei UI,Microsoft Yahei,\\5FAE\8F6F\96C5\9ED1,Segoe UI,Tahoma,"\5B8B\4F53 b8b\4F53",SimSun,sans-serif;;

  }
  font-face {
    font-family: "PingFangSC";
    src: url("/font/PINGFANG MEDIUM.TTF");
  }
</style>
