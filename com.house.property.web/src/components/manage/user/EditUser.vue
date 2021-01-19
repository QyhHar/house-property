<template>
  <div>
    <el-dialog
      :title="dlgTitle"
      :visible="true"
      width="30%"
      :before-close="close"
      :close-on-click-modal="false"
    >
      <el-form
        class="submit-form"
        :model="submitForm"
        :rules="submitRules"
        ref="submitForm"
      >

        <el-row>
          <el-col :span="18">
            <el-form-item label="用户姓名" prop="nickName" label-width="100px">
              <el-input
                size="small"
                v-model="submitForm.nickName"
                auto-complete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="18">
            <el-form-item label="登录名" prop="userName" label-width="100px">
              <el-tooltip
                class="item"
                effect="dark"
                :content="`登录名不能重复；若重复，则`+(userData.id?'更新':'创建')+`不成功!`"
                placement="top"
              >
                <el-input
                  size="small"
                  v-model="submitForm.userName"
                  auto-complete="off"
                ></el-input>
              </el-tooltip>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="18">
            <el-form-item label="密码" prop="password" label-width="100px">
              <el-tooltip
                class="item"
                effect="dark"
                content="密码6到30位必须含有英文字母、数字、特殊字符!"
                placement="top"
              >
                <el-input
                  size="small"
                  type="password"
                  v-model="submitForm.password"
                  auto-complete="new-password"
                >
                  <i
                    slot="suffix"
                    class="el-input__icon el-icon-view"
                    @click="passwordType = passwordType ? '' : 'password'"
                    :disabled="!!userData.id"
                  ></i>
                </el-input>
              </el-tooltip>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="18">
            <el-form-item label="是否为管理员" prop="type" label-width="100px">
              <el-switch
                v-model="submitForm.type"
                active-value="1"
                inactive-value="0"
                active-color="#13ce66"
                inactive-color="#aaa">
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="close">取 消</el-button>
        <el-button size="mini" type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import api from "../../../api/user.api";

export default {
  props: {
    userData: {
      // 传入用户信息
      type: Object,
    },
  },
  data() {
    let validateUserName = (rule, value, callback) => {
      if(this.userData.id)  callback();
      // debugger
      if (!value ) {
        callback(new Error('请输入登录名'));
      } else if(!/^[a-zA-Z0-9]+$/.test(value)){
        callback(new Error('请输入英文字母或数字'));
      }else  {
        api.getUserName({userName:value}).then(res=>{
          if(res.code!==0){
            callback(new Error('账户重复，请重新输入'));
          }else {
            callback();
          }
        });
      }
    };
    return {
      submitForm: {
        userName: "", // 登录名
        nickName: "", // 用户姓名
        password: "", // 密码
        type:'',//类型
      },
      passwordType: "password", // 密码输入框类型
      submitRules: {
        userName: [
          { validator: validateUserName, trigger: "blur" },
        ],
        nickName: [
          { required: true, message: "请输入用户姓名", trigger: "blur" },
        ],
        password: [
          { required: true, validator: this.validatePass, trigger: "blur" },
        ],

      },
      currentUser: null, // 当前登录用户
      roleData: [],
      groupData: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },

  computed: {
    dlgTitle() {
      return this.userData.id ? "编辑信息" : "新增用户";
    },
  },
  created() {
    this.currentUser = JSON.parse(sessionStorage.getItem("userInfo"));
    if (this.userData.id) {
      // 判断编辑还是新增
      this.submitForm = this.userData;
    }
  },
  methods: {
    async getAllRoles() {
      // 获取所有角色
      await api.user.getAllRoles().then((res) => {
        let that = this;
        that.roleData = res;
      });
    },
    validatePass: (rule, value, callback) => {
      if(this.userData.id)  callback();
      // 密码校验
      if (value === "") {
        callback(new Error("请输入密码"));
      } else if (!/^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,30}$/.test(value)) {
        callback(new Error("密码6到30位必须含有英文字母、数字、特殊字符"));
      }  else {
        callback();
      }
    },
    submit() {
        if (this.submitForm) {
          this.$confirm("请确认是否提交！", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            this.userData.id
              ? this.editUser().then(res=>{
                this.close();
              })
              : this.addUser().then(res=>{
                this.close();
              });

          });
        } else {
          return false;
        }
    },
    async addUser() {
      api.save(this.submitForm).then(res=>{
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
    async editUser() {
      api.updateUser(this.submitForm).then(res=>{
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

    close() {
      this.$emit("close", "editDlgShow");
    },
  },
};
</script>
<style lang="less" scoped>
.camera-box {
  width: 100%;

  .search-tree {
    width: 100%;
    padding: 10px;
    overflow-y: auto;
    border: 1px solid #ebeef5;
    box-sizing: border-box;
  }
}
</style>
