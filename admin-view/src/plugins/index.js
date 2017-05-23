import Vue from 'vue';
import dialog  from '@/components/module-ui/pop-up/dialog';
 import {$$Http,$$Func} from '@/util';
/**
 * 注册到Vue对象中
 */
Vue.use({
	install(Vue, options) {
        //2.将ajax注册为插件
        Vue.prototype['$$Http'] = $$Http;
        //3.将工具函数注册为插件
        Vue.prototype['$$Func'] = $$Func;
        //4.将dialog注册为插件
        Vue.prototype['$$Dialog'] = dialog;
	}
});