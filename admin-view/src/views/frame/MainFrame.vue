<template>
   <div id="container">
        <!--頭部-->
        <bd-header></bd-header>
        <!--主體区域-->
        <div id="main">
            <!--右侧菜单-->
            <bd-menubar></bd-menubar>
            <!--内容显示区，一种tabs显示，另外一种就是无tabs，只能操作一个窗口-->
            <bd-content-view type="tabs" style="flex: 1"></bd-content-view>
        </div>
        <!--底部-->
        <bd-footer></bd-footer>
  </div>
</template>

<style scoped>
  #container{
     display: -webkit-flex;
     display: flex;
     flex-direction: column;
     justify-content: space-between;
     height:100%;
  }

  #main {
    flex: 1;
    display: flex;
  }

  #main .menu-bar {
      flex: 0 0 190px;
      background: #F2F2F2;
      border-right: 1px solid #CCCCCC;
  }

</style>

<script>
  import Header from './Header';
  import Footer from './Footer';
  import MenuBar from './MenuBar';
  import ContentView from '@/components/content-view'

  export default {
    name: 'app',
    components:{
      "bd-header":Header,
      "bd-footer":Footer,
      "bd-menubar":MenuBar,
      "bd-content-view":ContentView
    }
  }
</script>
