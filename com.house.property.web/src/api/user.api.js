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

export default{
  register,
  login,
  getCode,
}
