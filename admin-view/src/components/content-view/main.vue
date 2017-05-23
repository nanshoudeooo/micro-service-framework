<template>
     <component v-bind:is="currentView">
      <!-- 组件在 vm.currentview 变化时改变！ -->
     </component>
</template>

<style scoped>
    
</style>
<script>
     import SingleView from './single-view.vue';
     import TabsView from './tabs-view.vue';
     
     export default {
        name:"bd-content-view",
        props:{
            'type':{
                type: String,
                required: true
            }
        },
        data(){
           return {
              currentView:this.type 
           }
        },
        components: {
             "single":SingleView,
             "tabs":TabsView
         }
     }
</script>