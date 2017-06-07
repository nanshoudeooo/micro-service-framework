import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';

Vue.use(VueAxios, axios);

// 导入封装的回调函数
import settings from '@/config';
import store from './store.js';
const querystring = require("querystring");


/**
 * 可指定上下文，防止系统需要访问多个不同主机的接口可以配置多个代理
 * @param {*} type 
 * @param {*} context 指定上下文方便配置多个代理策略
 * @param {*} url 
 * @param {*} data 
 * @param {*} tokenFlag 
 */
var ajaxAnyContext = function(type,url,data,tokenFlag){
	if(typeof data === 'boolean'){
		tokenFlag = data;
	}
    var datas;
 
	if (type === 'get') {
		datas = {
			params: data || {}
		};
	} else {
		datas = data || {};
		datas = querystring.stringify(datas);
	}
    //定义一个拦截器
	Vue.axios.interceptors.request.use(function (config) {
		//改成全局默认形式，这种拦截器写法，过一段时间后发现参数又传递不过去，经排查Content-type配置居然无效，
		//然而清空浏览器缓存后，又有效了
		config.headers.post["Content-Type"] = 'application/x-www-form-urlencoded';
		if(tokenFlag){
           	config.headers.common['token'] = (store.sessionStore.get('token') || '');
		}
		return config;
	});
   
	return  Vue.axios[type](url, datas).then(function(res){
		 return new Promise(function(resolve,reject){
			   if(res.data.code===0){//状态码为0的时候为成功，成功后只返回响应内容体
                   resolve(res.data.content);
			   }else{
                   reject(res.data);
			   }
			});
	}).catch(function(err){
		//如果不是reject进来的
		if(err.name && err.name=='Error'){
            return settings.cbs.statusError.call(self, err);
		}else{
			return Promise.reject(err);
		}
	});
}

/**
 * 封装axios的通用请求
 * @param  {string}   type      get或post
 * @param  {string}   url       请求的接口URL
 * @param  {object}   data      传的参数，没有则传空对象
 * @param  {boolean}   tokenFlag 是否需要携带token参数，为true，不需要；false，需要。一般除了登录，都需要
 */
var ajax = function(type, url, data,tokenFlag = true) {
	if(typeof data === 'boolean'){
		tokenFlag = data;
	}
    var datas,self = this;

	if (type === 'get') {
		datas = {
			params: data || {}
		};
	} else {
		datas = data || {};
		datas = querystring.stringify(datas);
	}
    //定义一个拦截器
	Vue.axios.interceptors.request.use(function (config) {
		config.headers.post["Content-Type"] = 'application/x-www-form-urlencoded';
		if(tokenFlag){
           	config.headers.common['token'] = (store.sessionStore.get('token') || '');
		}
		return config;
	});
    url = settings.gbs.host+url;
	return  Vue.axios[type](url, datas).then(function(res){
		 return new Promise(function(resolve,reject){
			   if(res.data.code===0){//状态码为0的时候为成功，成功后只返回响应内容体
                   resolve(res.data.content);
			   }else{
                   reject(res.data);
			   }
			});
	}).catch(function(err){
		//如果不是reject进来的
		if(err.name && err.name=='Error'){
            return settings.cbs.statusError.call(self, err);
		}else{
			return Promise.reject(err);
		}
	});
};

var get = function(url,data,tokenFlag){
   return ajax.call(this,'get', url, data,tokenFlag);
};

var post = function(url,data,tokenFlag){
    return ajax.call(this,'post', url, data,tokenFlag);
};

var put = function(url,data,tokenFlag){
	return ajax.call(this,'put', url, data,tokenFlag);
};

var remove = function(url,data,tokenFlag){
	return ajax.call(this,'delete', url, data,tokenFlag);
};

export default {
	ajax:ajax,
	get:get,
	post:post,
	put:put,
	delete:remove
};