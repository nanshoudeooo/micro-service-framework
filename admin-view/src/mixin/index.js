import Vue from 'vue';
import Methods from './Methods';
/**
 * 递归提取一个对象中的所有函数
 * @param  {object} obj 对象
 * @return {object}     所有函数都将被包装到这个对象中
 */
function mergeManyObjToOneObj(obj) {
	var newObj = {};
	if (obj && typeof obj === 'object') {
		for (var f in obj) {
			if (typeof obj[f] === 'function') {
				newObj[f] = obj[f];
			}
			if (typeof obj[f] === 'object') {
				Object.assign(newObj, mergeManyObjToOneObj(obj[f]));
			}
		}
	}
	return newObj;
}

//导入自定义的全局混合
export var GridMixins = {
	methods: mergeManyObjToOneObj(Methods)
};

//注册全局混合
//举例：如果你混合包含一个钩子而创建组件本身也有一个,两个函数将被调用。
//Mixin钩子按照传入顺序依次调用,并在调用组件自身的钩子之前被调用。
var globalMixins = {
    data:function(){
		return {
			formSubmiting:false
		}
	}
}
Vue.mixin(globalMixins);

