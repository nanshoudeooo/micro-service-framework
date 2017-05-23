export const Func = {
   isEmptyObject:function(obj){
        if(obj){
            for (var name in obj){
　　　　      return false;//返回false，不为空对象
　　        }　　
        }
　　    return true;//返回true，为空对象
   },
   deepCopy :function (source) {
        var result = {},_ = this;
        for (var key in source) {
          result[key] = typeof source[key] === 'object' ? _.deepCopy(source[key]) : source[key];
        }
        return result;
   },
   fillValue:function(fromObj,toObj){
      if(fromObj && toObj){
        for(let toKey in toObj){
          toObj[toKey] = fromObj[toKey];
        }
      }
   }
}

