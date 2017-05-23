<template>
  <div id="loader-wrapper">
    <div id="loader"></div>
       <div class="loader-section section-left"></div>
       <div class="loader-section section-right"></div>
       <div class="load_title">{{messageText}}</div>
   </div>
</template>

<style scoped>

</style>

<script>
   export default{
       name:'frame-loading',
       computed:{
          messageText(){
            return this.$store.state.transition.messageText
          }
       },
       mounted:function(){
         const self = this;
          self.$store.dispatch('refreshUser').then(function(){
           return  self.$store.dispatch('refreshRoles');
         }).then(function(){
           return  self.$store.dispatch('refreshPermissions');
         }).then(function(){
           self.$router.push('/mainFrame');
         });
       }
   } 
</script>