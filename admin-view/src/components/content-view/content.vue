<!--模块挂载-->
<template>
     <component v-bind:is="currentView">
      <!-- 组件在 vm.currentview 变化时改变！ -->
     </component>
</template>

<script>
    export default{
        name:'content'
    }
</script>