<template>
    <div class="panel" v-if="key === 'main'">
        <slot></slot>
    </div>
     <component ref="currentView" v-bind:is="currentView" v-else></component>
    
</template>
<style scoped>
   .panel{
    background:#fff;
    width:99.5%;
    height: 99%;
    margin: 2.5px auto;
    /*border: 1px solid #ccc;*/
    display:flex;
    flex-direction:column; 
    overflow-y:auto;
   } 
</style>
<script>
    export default {
        name:'panel',
        componentName:'panel',
        data(){
           return {
               key:'main'
           }
        },
        computed:{
            currentView(){
                return this.inject[this.key]
            }
        },
        props:{
            inject:Object
        },
        methods:{
            reload:function(){
                this.key = 'main';
                //切换时组件钩子函数不会再调用，因为是组件内部的子组件在切换而父组件没有进行生命周期的改变，
                //对于component标签部分有自己的生命周期，而slot分发标签无法捕获任何状态改变。
                //为了解决slot部分可能需要在重新加载时做一些处理，给予一个触发事件，父组件可以通过监听panel
                //的reload事件来实现自己的一些效果。
                this.$emit('reload');
            }
        }
    }
</script>