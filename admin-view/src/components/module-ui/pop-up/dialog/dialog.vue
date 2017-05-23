<template>
  <div class="layer-dialog-wrapper" v-if="visible">
    <div class="layer-dialog-shadow"></div>
    <div class="layer-dialog" :style="styleObject"  ref="dialog">
      <div class="layer-dialog-title-wrapper">
        <div class="layer-dialog-title">{{title}}</div> 
        <span class="layer-dialog-setwin">
            <a class="layer-dialog-ico layer-max" href="javascript:;" v-if="winSize=='normal'" @click="makeMaxSize"></a>
             <a class="layer-dialog-ico layer-normal" href="javascript:;" v-else-if="winSize=='max'" @click="makeNormalSize"></a>
            <a class="layer-dialog-ico layer-close" href="javascript:;" @click="close"></a>
        </span>
      </div>
      <div class="layer-dialog-content">
        <component :is="content" v-if="contentType==='component'" ref="content" :param="param"></component>
        <iframe v-else-if="contentType==='url'" :src="content" 
           scrolling="auto" allowtransparency="true" frameborder="0" style="width:100%;height:100%"></iframe>
        <div v-else-if="contentType==='html'" v-html="content"></div>
      </div> 
      <div class="layer-dialog-btnBar" v-if="buttons && buttons.length > 0">
         <template v-for="button in buttons">
            <a href="javascript:;" @click="action(button.handle)" :class="button.cls">{{button.text}}</a>
         </template>
      </div> 
    </div>
  </div>
</template>
<style scoped>
     @import './css/style.css';
</style>
<script>
     export default{
         data:function(){
             return {
                 visible:false,
                 winSize:'normal',
                 currentWidth:this.width,
                 currentHeight:this.height,
                 currentLeft:this.offset[1],
                 currentTop:this.offset[0]
             }
         },
         computed:{
             /**
              * 因为dialogjs中组件实例化后，改变了弹框位置，所以需要计算熟悉，否则位置数据变动后窗口不会响应
              * 而拖拽后的位置数据需要反应到页面元素我们不能直接改变属性（Prop）中的变量，所以定义了current*
              */
             styleObject:function(){
                 return {
                    left:this.currentLeft || this.offset[1],
                    top:this.currentTop || this.offset[0],
                    width:this.currentWidth || this.width,
                    height:this.currentHeight || this.height
                 }
             }
         },
         updated(){
             const self = this;
             const moveObj = self.$refs["dialog"];
             if(!moveObj){
                 return;
             }
             //将标题作为拖拽句柄
             const dragObj=moveObj.querySelector(".layer-dialog-title");
             
             dragObj.onmousedown = function(mousedownEvt){
                    mousedownEvt = mousedownEvt || window.event;
                    //---start禁止文档选中，因为拖拽的时候页面内容可能出现一片蓝色选中状态
                    if(typeof userSelect === "string"){
                      return document.documentElement.style[userSelect] = "none"; 
                    } 
                    document.unselectable  = "on";
                    document.onselectstart = function(){   return false; }
                    //---end

                    let offsetX = mousedownEvt.offsetX;
                    let offsetY = mousedownEvt.offsetY;
                    let w = moveObj.offsetWidth,//窗口的宽度
                        h = moveObj.offsetHeight;//窗口的高度
                    let containerOffsetWidth = moveObj.parentNode.offsetWidth,
                        containerOffsetHeight = moveObj.parentNode.offsetHeight;
                   //绑定鼠标移动事件
                   moveObj.parentNode.onmousemove = function(mousemoveEvt){
                       mousemoveEvt = mousemoveEvt || window.event;

                       let x = mousemoveEvt.pageX;
                       let y = mousemoveEvt.pageY;
                       
                       x -= offsetX;  
                       y -= offsetY; 
                       
                       //因为样式中定义了translate(-50%,-50%)的便宜量，所以移动的时候需要补上 
                       if(x > 0 && x < containerOffsetWidth - w){
                            self.currentLeft = x + w*0.5 +'px';
                       }
                       if(y > 0 && y < containerOffsetHeight - h){
                           self.currentTop = y + h*0.5 + 'px';
                       }
                      
                   };
             };

             dragObj.onmouseup = function(){
                   moveObj.parentNode.onmousemove = null;
                   //恢复文档可选
                   if(typeof userSelect === "string"){  
                     return document.documentElement.style[userSelect] = "text";
                   }
                   document.unselectable  = "off";document.onselectstart = null;
             };
         },
         props:{
             title:String,
             buttons:Array,
             /**
              *弹框内容显示类型 ：component - 组件 ；url - iframe ； html - html文本
              */
             contentType:{type:String,default:'component'},
             /**
              * 弹出框显示的内容
              */
             content:[String,Object],
             width:String,
             height:String,
             /**
              * 弹框的位置 0-top，1-left
              */
             offset:{
                 type:Array,
                 default:function () {
                   return ['50%','50%'];
                 }
            },
            /**
             * 传递的参数
             */
            param:Object
         },
         methods:{
             action:function(callback){
                 let obj;
                 if(this.contentType==='component'){
                     obj = this.$refs.content;
                 }
                 if(this.contentType==='url'){
                     obj = this.$refs["dialog"].querySelector('iframe').contentWindow;
                 }
                 if(this.contentType==='html'){
                     obj = this.$refs["dialog"].querySelector('.layer-dialog-content');
                 }
                if(callback(obj,this.close))
                  this.close();
             },
             close:function(){
                this.visible = false;
             },
             makeMaxSize:function(){
                  let containerOffsetWidth = this.$refs["dialog"].parentNode.offsetWidth,
                        containerOffsetHeight = this.$refs["dialog"].parentNode.offsetHeight;
                 this.currentWidth = containerOffsetWidth + 'px';
                 this.currentHeight = containerOffsetHeight + 'px';
                 this.winSize = "max";
                 this.currentLeft = containerOffsetWidth*0.5 +'px';
                 this.currentTop = containerOffsetHeight*0.5 + 'px';
             },
             makeNormalSize:function(){
                   this.currentWidth = this.width;
                 this.currentHeight = this.height;
                 this.winSize = "normal";
             }
         }
     }  
</script>