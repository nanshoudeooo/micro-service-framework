export default{
    /**
     * 排序和翻页传参根据服务器接口转换参数
     */
    paramConverter:function(obj){
                let res = {};
                if(obj.sort){
                    let sortCondition = [];
                    for(let k in obj.sort){
                        let sortItem = {};
                        sortItem["field"] = k;
                        sortItem["direction"] = obj.sort[k];
                        sortCondition.push(sortItem);
                   }
                  res.sortCondition = JSON.stringify(sortCondition);
                }
                if(obj.pageNo && obj.pageSize){
                  res.pageCondition = JSON.stringify({page:obj.pageNo,rows:obj.pageSize});
                }
              return res;
    }
}