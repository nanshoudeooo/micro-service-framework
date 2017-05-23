var fakeData = function(){
    return {
      //登录模拟 
      "login": {
         "token":"123123"
      },
      "user":[
          {"id":"1","username":"admin","email":"sdevil507@163.com","available":true},
          {"id":"2","username":"allanloo","email":"845730148@qq.com","available":true}
      ],
      "role":[
          {"id":"1","rolename":"admin"}
      ]
    };
}

module.exports = function () {
  var mockData =fakeData();

  //实际后台响应格式
  var result = {};
  for(var key in mockData){
      //创建响应对象
      var resp = {"code":0,message:"",content:null};
      //获取数据中当前的值
      var v = mockData[key];
      //将值赋值给响应对象
      resp.content = v;
      result[key] = resp;
  }
  return result
}