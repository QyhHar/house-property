<template>
  <div id="userList">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item>
          <el-input
            size="small"
            v-model="searchForm.nickName"
            placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            size="small"
            v-model="searchForm.userName"
            placeholder="登录名"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button size="small" type="primary" @click="getTableData"  icon="el-icon-search" :loading="loading"
          >搜 索</el-button
          >
          <el-button size="small" @click="resetData">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-container">
      <div class="table-btns">
        <el-button type="success" size="mini" @click="openEdit"
        >新 增</el-button
        >
        <el-button type="danger" size="mini" @click="deleteData"
        >删 除</el-button
        >
      </div>
      <div class="table-cont">
        <el-table
          ref="dataTable"
          :data="tableData"
          @selection-change="handleSelectionChange"
          border
          stripe
        >
          <el-table-column
            type="selection"
            :selectable="selectable"
          ></el-table-column>
          <el-table-column prop="nickName" label="用户名"></el-table-column>
          <el-table-column prop="userName" label="登录名"></el-table-column>
          <el-table-column  label="角色">
            <template slot-scope="scope">
              {{scope.row.type==0?'普通用户':'管理员'}}
            </template>
          </el-table-column>
          <el-table-column  label="权限">
            <template slot-scope="scope">
                <el-switch
                  @click.native="updateUser(scope.row)"
                  v-model="scope.row.type"
                  active-value="1"
                  inactive-value="0"
                  active-color="#13ce66"
                  inactive-color="#aaa">
                </el-switch>
            </template>

          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="mini"
                @click="openEdit(scope.row)"
                :disabled="scope.row.id === 1"
              >修改</el-button
              >
              <el-button
                type="text"
                size="mini"
                @click="deleteData(scope.row)"
                :disabled="scope.row.id === 1 || scope.row.id === selfId"
              >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="table-pagination">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="searchForm.pageNum"
            :page-sizes="[10, 15, 30, 50]"
            :page-size="searchForm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalData"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <EditUser
      v-if="dialogs.editDlgShow"
      :userData="editUserData"
      @close="closeDlg"
      @updateData="getTableData"
    ></EditUser>
  </div>
</template>

<script>
  import EditUser from "./EditUser";
  import api from "../../../api/user.api"
  export default {
    name: "userList",
    components: {
      EditUser,
    },
    data() {
      return {
        loading:false,
        dialogs: {
          editDlgShow: false, // 编辑弹框显隐
          resetDlgShow: false, // 重置密码弹框显隐
        },
        areaData: [], // 区域数据
        searchForm: {
          nickName: "",
          userName: "",
          current: 1,
          size: 15,
        },
        editUserData: {}, // 编辑时传入用户信息
        resetUserData: {}, // 重置密码时传入用户信息

        selectDatas: [], // 选中的数据
        tableData: [],
        totalData: 0,
      };
    },
    computed: {
      selfId() {
        let userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
        return userInfo.id;
      },
    },
    created() {
      let userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
      this.getTableData();
    },
    methods: {
      updateUser(user){
        api.updateUser(user).then(res=>{
          if(res.code===0) {
            this.$message({
              message: res.message,
              type: 'success'
            })
          }else{
            this.$message({
              message: res.message,
              type: 'warning'
            })
          }
        })
      },
      async getTableData() {
        // 获取表格数据
        let params = this.searchForm;
        await api.getUserByQuery(params).then((res) => {
          let that = this;
          that.totalData = res.data.total;
          that.tableData = res.data.records;
        });
      },
      handleSelectionChange(val) {
        // 暂存选中数据
        this.selectDatas = val;
      },
      selectable(row, index) {
        return row.id === 1 || row.id === this.selfId ? false : true;
      },

      deleteData(val) {
        if (this.selectDatas.length < 1 && !val.id) {
          this.$message({
            message: "请选择需要删除的数据！",
            type: "warning",
          });
          return false;
        }
        let tip = val.id ? "是否删除本条数据？" : "是否删除所选数据？";
        let ids = val.id
          ? [val.id]
          : this.selectDatas.map((i) => {
            return i.id;
          });
        this.$confirm(tip, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          this.deleteUser(ids).then((res) => {
            this.getTableData();

          });
        });
      },
      async deleteUser(ids) {
        // 删除用户信息
        api.deleteUser(ids.toString()).then(res=>{
          if(res.code===0) {
            this.$message({
              message: res.message,
              type: 'success'
            })
          }else{
            this.$message({
              message: res.message,
              type: 'warning'
            })
          }
        })
      },

      openEdit(data) {
        // 打开编辑/新增弹框
        if (data.id) {
          this.editUserData = data;
        } else {
          this.editUserData = {};
        }
        this.dialogs.editDlgShow = true;
      },

      closeDlg(val) {
        // 关闭弹框方法
        this.dialogs[val] = false;
        this.getTableData();
      },

      handleSizeChange(val) {
        this.searchForm.size = val;
        this.getTableData();
      },
      handleCurrentChange(val) {
        this.searchForm.current = val;
        this.getTableData();
      },
      resetData() {
        // 重置
        this.searchForm.nickName = "";
        this.searchForm.userName = "";
        this.getTableData();
      },
    },
  };
</script>
<style lang="less">
  #userList{
    .search-form{
      .el-form-item{
        margin-bottom: 10px;
        .el-input{
          width: 215px;
        }
      }
    }
    .table-container {
      .table-cont{
        margin-top: 10px;
        .el-table{
          &::before{
            height: 0;
          }
          td, th{
            padding: 6px 0;
          }
          .el-table__empty-block{
            border-bottom: 1px solid #EBEEF5;
          }
        }
        .table-pagination{
          text-align: center;
          margin-top: 10px;
        }
      }
    }
  }

</style>
