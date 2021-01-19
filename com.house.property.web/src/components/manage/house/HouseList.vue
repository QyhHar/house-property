<template>
  <div id="houseList">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item>
          <el-cascader
            size="small"
            v-model="searchForm.areaId" :options="options" style="width:35%" clearable
            :props="{ checkStrictly: true,multiple:true }"
          ></el-cascader>
        </el-form-item>
        <el-form-item>
          <el-input
            size="small"
            v-model="searchForm.residential"
            placeholder="小区名"
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
          <el-table-column prop="areaName" label="区域名字"></el-table-column>
          <el-table-column prop="residential" label="小区"></el-table-column>
          <el-table-column prop="houseAddress" label="房屋地址"></el-table-column>
          <el-table-column prop="measureArea" label="面积"></el-table-column>
          <el-table-column  label="室">
            <template slot-scope="scope">
              {{scope.row.room+'室'}}
            </template>
          </el-table-column>
          <el-table-column  label="厅">
            <template slot-scope="scope">
              {{scope.row.office+'厅'}}
            </template>
          </el-table-column>
          <el-table-column  label="交易权属">
            <template slot-scope="scope">
              {{getPurposes(scope.row.purpose)}}
            </template>
          </el-table-column>
          <el-table-column  label="方式">
            <template slot-scope="scope">
              {{scope.row.type==='1'?'出售':'出租'}}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="mini"
                @click="goHouseEdit(scope.row.id)"
                :disabled="scope.row.id === 1"
              >详情</el-button
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

  </div>
</template>

<script>
  import api from "../../../api/house.api"
  export default {
    name: "houseList",
    components: {
    },
    data() {
      return {
        orientations:[{value:'1',label:'北'}, {value:'2',label:'南'}, {value:'3',label:'西'},
          {value:'4',label:'东'},{value:'5',label:'南北'}],//朝向：1-北；2-南；3-西；4-东；5-南北
        purposes:[{value:'1',label:'普通住宅'}, {value:'2',label:'商业类'}, {value:'3',label:'别墅'},
          {value:'4',label:'四合院'},{value:'5',label:'车位'},{value:'6',label:'其他'}],
        options:[],
        loading:false,
        dialogs: {
          editDlgShow: false, // 编辑弹框显隐
          resetDlgShow: false, // 重置密码弹框显隐
        },
        areaData: [], // 区域数据
        searchForm: {
          areaId: "",
          residential: "",
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
      api.getChildArea().then(res => {
        this.options=res.data;
      });
      this.getTableData();
    },
    methods: {
      goHouseEdit(houseId){
        this.$router.push({
          path: "/houseEdit",
          query: { houseId: houseId },
        });
      },
      getPurposes(val){
        let purpose = '';
        this.purposes.forEach(item=>{
          if(item.value===val) purpose= item.label
        });
        return purpose;
      },
      async getTableData() {
        // 获取表格数据
        let params = this.searchForm;
        await api.getHouseByPage(params).then((res) => {
          if(res.code===0) {
            let that = this;
            that.totalData = res.data.total;
            that.tableData = res.data.records;
          }else{
            this.$message({
              message: res.message,
              type: 'warning'
            })
          }

        });
      },
      selectable(row, index) {
        return row.id === 1 || row.id === this.selfId ? false : true;
      },
      handleSelectionChange(val) {
        // 暂存选中数据
        this.selectDatas = val;
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
          this.deleteHouse(ids).then((res) => {
            this.getTableData();
          });
        });
      },
      async deleteHouse(ids) {
        // 删除用户信息
        api.deleteHouse(ids.toString()).then(res=>{
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
        this.searchForm.areaId = "";
        this.searchForm.residential = "";
        this.getTableData();
      },
    },
  };
</script>
<style lang="less">
  #houseList{
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
