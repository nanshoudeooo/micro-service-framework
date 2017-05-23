<template>
     <div class="search-form">
        <el-form :inline="true" :rules='rules' :model='model' :labelWidth='labelWidth'>
           <slot></slot>
        </el-form>
     </div>
</template>
<style>
    .search-form{
       background: #fff;
       padding:0px 10px;
       flex:0;
       border:1px solid #ccc;
       border-top:none;
       border-bottom:none;
   }
   .search-form .el-form-item{
      margin-top:10px;
      margin-bottom: 10px; 
   }
   .search-form .el-form-item .el-input{
       width:150px;
   }
</style>
<script>
    export default{
        name:'search-form',
        props:{
             model: Object,
             rules: Object,
             labelWidth: String
        }
    }
</script>