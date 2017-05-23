export default {
    /**
     * 固定写法,注意上下文已经被切换了
     */
    submit:function(grid,data){
         let self = this;
         self.$$Http.post.call(self,'/admin/role/delete',{id:data.id})
            .then(function(content){
              self.$message({message: '删除成功',type: 'success'});
              grid.loadData();
             },function(resp){
                 if(!resp.treated)
                   self.$$MessageBox.alert(resp.message, '提示', {confirmButtonText: '确定'});	
            });
    },
    renderToolButton:function(rowdatas){
        if(rowdatas && rowdatas.length > 0){
            return !rowdatas[0]["builtIn"];//内置记录不给删除
        }
        return true;
    }
}