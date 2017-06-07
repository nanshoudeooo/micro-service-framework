var env = process.env;
import Vue from 'vue';
const settings ={

	//全局设置
	gbs: {
		host:(env.NODE_ENV === 'development' ? 'appApi' : 'http://106.14.43.38:10000'),
		db_prefix: 'biz_' //本地存储的key
	},

	//回调
	cbs: {
		/**
		 * ajax请求成功，返回的状态码不是200时调用
		 * @param  {object} err 返回的对象，包含错误码和错误信息
		 */
		statusError(err) { 
			var statusCode = err.status || err.response.status;
			var errorMessage = '';
			switch(statusCode) {
				case 401://需要登录
				  let msg = '';
				  if(err.response && err.response && err.response.data && err.response.data.code==2){
                      msg = "身份认证失败，可能由于您长时间未操作会话已过期，请重新登录后再操作！";
					  this.$confirm(msg, '警告', 
					   {
                         confirmButtonText: '重新登录',
					     cancelButtonText: '取消',
                          type: 'warning'
                        }).then(function(){
                        self.$router.push('/logout');
				       },function(){});
				  }else{
					  msg = `<div style="margin-left:50px;">
				               <h1 style="margin-top:20px;">服务器拒绝访问，您无权执行该操作！</h1><br/> <br/>                                                                                             
				               <div style="color:#F7BA2A"> 温馨提示：</div><br/>
				               <div style='font-size:12px;margin-top:1px;color:#F7BA2A'> 1、如果之前可进行相关操作，可能权限已被收回；</div>
							   <div style='font-size:12px;margin-top:5px;color:#F7BA2A'> 2、如果联系管理员后给您授予了权限，请退出后重新登录；</div>
							</div>`;
					    const self = this;
						this.$$Dialog.open({
							title:"提示",
							contentType:"html",
                              width:"400px",
                              height:"250px",
							content:msg,
							okCallback:function(){
								self.$router.push('/logout');
							}
						});
				  }
			
				   errorMessage = '服务器拒绝访问，您无权进行该操作！';
				   console.error("AllanLoo Tell You:"+err.message);
				   break;
				case 400:// 由于语法格式有误，服务器无法理解此请求	
				case 404:	
				   this.$alert('未知请求，远程服务未提供该服务接口', '提示', {confirmButtonText: '确定'});	
				   errorMessage = "未知请求，远程服务未提供该服务接口";
				   console.error("AllanLoo Tell You:"+err.message);
				   break;				
				case 403://服务器理解客户的请求，但拒绝处理它
				case 500://服务器错误
		           this.$alert('远程服务内部异常', '提示', {confirmButtonText: '确定'});	
				   errorMessage = "远程服务内部异常";
				   console.error("AllanLoo Tell You:"+err.message);
				   break;
				case 504://网关超时
				    this.$alert('连接服务器时，网关超时', '提示', {confirmButtonText: '确定'});	
				   errorMessage = "连接服务器时，网关超时";
				   console.error("AllanLoo Tell You:"+err.message);
				   break;
				default: 
				   errorMessage = "未知请求错误";
				   console.error(err.message);
			}
			return Promise.reject({code:statusCode,message:errorMessage, treated:true});
		}
	}
};

export default settings;