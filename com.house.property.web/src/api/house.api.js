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
//卖房/出租
async function houseUpdate(params) {
  return await http.post('/house/update', params)
}

//查询
async function getHouseByQuery(params) {
  return await http.post('/house/getByQuery', params)
}


//用户的房屋
async function getHouseByUserId(params) {
  return await http.get('/house/getByUserId', params)
}

//查询单个房屋
async function getHouseById(params) {
  return await http.get('/house/getById', params)
}

//获取品质房屋
async function getQualityHouse(params) {
  return await http.get('/house/getQualityHouse', params)
}

//获取品质房屋
async function getHouseByPage(params) {
  return await http.post('/house/getHouseByPage', params)
}

//获取品质房屋
async function deleteHouse(params) {
  return await http.get('/house/delete/'+params, params)
}

export default{
  getChildArea,
  houseAdd,
  getHouseByQuery,
  getHouseById,
  getAreaById,
  getQualityHouse,
  getHouseByUserId,
  houseUpdate,
  getHouseByPage,
  deleteHouse,
}
