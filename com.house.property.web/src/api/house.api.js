import http from "../http";

//获取区域树
async function getChildArea(params) {
  return await http.get('area/getTreeArea', params)
}

//卖房/出租
async function houseAdd(params) {
  return await http.post('/house/save', params)
}


export default{
  getChildArea,
  houseAdd,
}
