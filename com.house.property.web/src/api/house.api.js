import http from "../http";

//获取区域树
async function getChildArea(params) {
  return await http.get('area/getTreeArea', params)
}

//获取区域树
async function getAreaById(params) {
  return await http.get('area/getAreaById', params)
}

//卖房/出租
async function houseAdd(params) {
  return await http.post('/house/save', params)
}

//查询
async function getHouseByQuery(params) {
  return await http.post('/house/getByQuery', params)
}

//查询单个房屋
async function getHouseById(params) {
  return await http.get('/house/getById', params)
}


export default{
  getChildArea,
  houseAdd,
  getHouseByQuery,
  getHouseById,
  getAreaById,
}
