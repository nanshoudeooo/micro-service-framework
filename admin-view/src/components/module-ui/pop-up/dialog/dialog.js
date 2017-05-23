import Vue from 'vue'
import dialogVue from './dialog.vue';


/**
 * 扩展构造函数
 */
const DialogConstructor = Vue.extend(dialogVue);
/**
 * 初始化实例对象
 */
const initInstance = function(){
   return new DialogConstructor({
    el: document.createElement('div')
  });
   
};

/**
 * 合并对象
 * @param {*} target 
 */
const merge = function(target) {
  for (let i = 1, j = arguments.length; i < j; i++) {
    let source = arguments[i] || {};
    for (let prop in source) {
      if (source.hasOwnProperty(prop)) {
        let value = source[prop];
        if (value !== undefined) {
          target[prop] = value;
        }
      }
    }
  }

  return target;
};

const defaults = {
    title:'',
    width:'auto',
    height:'auto',
    contentType:'component',
    content:undefined,
    url:'',
    param:{},
    buttons:[{text:"取消",handle:function(){return true},cls:'btnDefault'}]
}
/**
 * 当存在buttons时忽略okCallback和cancelCallback
 * @param {*} options -全部选项为 {
 *    <!--contentType枚举项为'component'，'url'，'html'-->
 *    contentType:'' ,
 *    content:'',
 *    title:'',
 *    width:'',
 *    height:'',
 *    param:{} 传递参数供内页使用
 *    offset:[top,left],
 *    okCallback:function(){},
 *    cancelCallback:function(){},
 *    buttons:[{..},{..}]
 * } 
 */
const open = function(options){
    const dialogInstance = initInstance();
     
    //当buttons=false时，去除按钮
    if(options.buttons!=undefined && options.buttons === false){
        options.buttons = [];
    }
    //如果存在确定回调函数
    if(options.buttons===undefined && options.okCallback){
        options.buttons = [
             {text:"确认",handle:options.okCallback,cls:'btnPrimary'},
             {text:"取消",handle:function(){return true},cls:'btnDefault'}
        ];
    }
    //如果存在取消回调函数不存在确定回调函数，因为有时我们需要在关闭窗口前做一些事
     if(options.buttons===undefined && !options.okCallback && options.cancelCallback){
           options.buttons = [
             {text:"取消",handle:options.cancelCallback,cls:'btnDefault'}
           ];
     }
     if(options.buttons===undefined && options.okCallback && options.cancelCallback){
            options.buttons = [
             {text:"确认",handle:options.okCallback,cls:'btnPrimary'},
             {text:"取消",handle:options.cancelCallback,cls:'btnDefault'}
           ];
     }
    delete options.cancelCallback;
    delete options.okCallback;

    let opt = merge({},defaults,options);
    
    for(let prop in opt){
        dialogInstance[prop] = opt[prop];
    }
  
    document.body.appendChild(dialogInstance.$el);
    //nextTick元素渲染好后改动数据
    Vue.nextTick(() => {
        dialogInstance.visible = true;
    });
}

export default {
    open:open
};