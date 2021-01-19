<template>
<div id="manage" >
  <Head  />
  <div class="bottom">
    <div class="container">
      <el-aside width="200px"  >
        <el-menu  router>
          <el-menu-item  index="/manage/userList"><i class="el-icon-user-solid"/>用户管理</el-menu-item>
          <el-menu-item  index="/manage/houseList"><i class="el-icon-s-home"/>房源管理</el-menu-item>
        </el-menu>
      </el-aside>
    </div>

    <div class="content-box" >
      <!-- <Breadcrumb></Breadcrumb> -->
      <div class="content">
        <transition name="move" mode="out-in">
          <router-view></router-view>
        </transition>
        <el-backtop target=".content"></el-backtop>
      </div>
    </div>
  </div>

  </div>
</template>

<script>
  import Head from "../head/Head/Head";

  export default {
    name: 'manage',
    components: {
      Head,
    },
  };
</script>

<style lang="less" >
  #manage{
    width: 100%;
    height:100%;
    .bottom{
      height:calc(100% - 38px);
      .container{
        width: 200px;
        float:left;
        display: block;
        height: 100%;
      }
      .content-box {
        width: calc(100% - 200px);
        float: left;
        transition: width 1s;
        height: 100%;
        background: #f0f0f0;
        .content {
          margin:10px 10px 20px;
          height: calc(100% - 50px);
          padding: 10px 20px;
          background: #fff;
        }
      }
    }

  }


</style>
