import http from "../http";

//注册
async function register(params) {
  return await http.post('/user/register', params)
}

//注册
async function login(params) {
  return await http.post('/user/login', params)
}


export default{
  register,
  login
}
