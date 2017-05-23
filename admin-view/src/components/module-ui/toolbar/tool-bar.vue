<template>
      <div class="tool-bar">
         <slot></slot>
      </div>
</template>
<style scoped>
.tool-bar{
    height: 45px;
    background: #F7F7F7;
    border:1px solid #ccc;
}
</style>
<script>
    export default{
        name:'tool-bar',
        componentName:'tool-bar'
    }
</script>