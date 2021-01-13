import http from "../http";


//关注
async function saveCollection(params) {
  return await http.post('/houseCollection/save', params)
}
//取消关注
async function deleteCollection(params) {
  return await http.delete('/houseCollection/delete', params)
}

export default{
  deleteCollection,
  saveCollection,
}
