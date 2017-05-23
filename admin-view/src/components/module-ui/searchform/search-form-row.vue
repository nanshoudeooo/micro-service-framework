<template>
    <el-row>
        <el-col :span="24">
           <slot></slot>
        </el-col>
    </el-row>
</template>
<script>
    export default{
        name:'search-form-row'
    }
</script>