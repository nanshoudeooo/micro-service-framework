/**
 * json server 使用 RESTful 架构，GET请求可以获取数据，POST 请求则是添加数据。
 * 在开发过程中，有时候想直接模拟获取POST请求返回结果，可以添加 express 中间件 将POST请求转为GET请求。
 */
module.exports = function (req, res, next) {
   req.method = "GET";
   next();
}