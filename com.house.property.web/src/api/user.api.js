import http from "../http";

//注册
async function register(params) {
  return await http.post('/user/register', params)
}



export default{
  register
}
