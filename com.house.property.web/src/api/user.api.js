import http from "../http";

//注册
async function register(params) {
  return await http.post('/user/register', params)
}

//登陆
async function login(params) {
  return await http.post('/user/login', params)
}

//获取验证码
async function getCode(params) {
  return await http.get('/captcha/getCode', params)
}

//校验账号
async function getUserName(params) {
  return await http.get('/user/getUserName', params)
}


//查询用户
async function getUserByQuery(params) {
  return await http.get('/user/getUserByQuery', params)
}
//更新用户
async function updateUser(params) {
  return await http.put('/user/update', params)
}

//新增用户
async function save(params) {
  return await http.post('/user/save', params)
}
//修改密码
async function updatePassword(params) {
  return await http.put('/user/updatePassword', params)
}

//删除用户
async function deleteUser(params) {
  return await http.delete('/user/delete/'+params, params)
}
export default{
  register,
  login,
  getCode,
  getUserName,
  getUserByQuery,
  updateUser,
  save,
  deleteUser,
  updatePassword,
}
