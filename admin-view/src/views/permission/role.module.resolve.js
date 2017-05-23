  import formateMenu from '@/views/resource/menu.formate.js';
  /**
   * 解析子节点
   * @param {Object} parentNode 
   * @param {Object[]} allNodes 
   */
var resolveChildren = function (parentNode, allNodes) {
     let children = new Array();
     for (let currentNode of allNodes) {
            if (currentNode.parent === parentNode.id) {
                children.push(currentNode);
            }
     }
    return children;
};

 /**
  * 解析父节点
  * @param {*} childNode 
  * @param {*} allNodes 
  */             
 var resolveParents = function(childNode,allNodes){
      let parents = new Array();
      for (let currentNode of allNodes) {
         if(currentNode.id === childNode.parent){
              parents.push(currentNode);
              parents =  resolveParents(currentNode, allNodes).concat(parents);
             //由子找父，肯定只有一个直接父节点
              break;
         }
      }
      return parents;
 };
 //过滤资源类型为menu的叶子节点
var filterMenuLeaf = function(resources){
    var moduleNodes = new Array();
      for(let res of resources){
          if(res.resourceType=='MENU'){
               //如果菜单类型的资源是叶子节点，或者菜单不是目录的
               if(!res.children || res.children.length == 0 || !res.dir){
                   moduleNodes.push(res);
               }else{
                  moduleNodes = moduleNodes.concat(filterMenuLeaf(res.children));
               }
          }
      }
      return moduleNodes;
};

 /**
   * 格式化资源
   * @param {object[]} resources 
   */
var formateResource = function (resources) {
        var moduleNodes = formateMenu(resources);//将扁平化的数据格式化成tree
         moduleNodes = filterMenuLeaf(moduleNodes);
                        
         for (let moduleNode of moduleNodes) {
              //模块的菜单
              let parents = resolveParents(moduleNode,resources);
              moduleNode.parents = parents;
              //模块的操作按钮
              let children = resolveChildren(moduleNode, resources);
              moduleNode.children = children;
         }
         return moduleNodes;
};
var makeGroup = function(moduleNodes){
   moduleNodes = formateResource(moduleNodes);
   
   let group = {};//用对象来进行分组,以当前模块的所有父菜单名称拼接为键{"菜单1/菜单2":[...]}
   for(let module of moduleNodes){
     //如果当前模块有父节点
      if(module.parents && module.parents.length > 0){
                //定义2个变量来拼接当前模块的所有父
                let currentParentNamesStr = "",currentParentIdsStr = "";
                for(let i=0;i < module.parents.length;i++){
                    currentParentNamesStr += module.parents[i].name;
                    currentParentIdsStr += module.parents[i].id;
                    //如果不是最后一个元素，父节点名称用“/”连接，父节点id用","连接
                    if(i < module.parents.length -1){
                        currentParentNamesStr += "/";
                        currentParentIdsStr += ",";
                    }
                }
          
               //将当前节点父id拼接字符串赋值给当前模块
                module.parentIds = currentParentIdsStr;
                //如果已经存在这个分组，则合并到这个分组里去
                if(group[currentParentNamesStr]){
                    group[currentParentNamesStr] = group[currentParentNamesStr].concat(module);
                }else{
                    //如果不存在该分组，则创建一个分组
                    group[currentParentNamesStr] = [module];
                }
          }else if(!module.dir){//如果当前节点是功能且没有挂靠菜单，就是直接挂在根目录下的
                let key = "无挂靠菜单";
                module.parentIds = "";
                if(group[key]){
                    group[key] = group[key].concat(module);
                }else{
                    group[key] = [module];
                }
         }else{//如果当前菜单是目录且没有功能
             group[module.name] = [];
         }
      }
      return group;
};

var convertRenderData = function(moduleNodes){
          let group = makeGroup(moduleNodes);

          //构造页面渲染格式的记录
          let result = new Array();
          let checkedIds = new Array();
          let allIds = new Array();
          for(let key in group){
                //把键当成一条记录添加菜单到结果中
                result.push({id:'',name:key,dir:true});
                if(group[key] && group[key].length > 0){
                       //值为模块数组，遍历模块数组
                        for(let item of group[key]){
                            let id ="MENU-" + item.id;
                           
                            //将当前模块id拼接他的所有父id作为新id值，因为选中子必须选中父，而父没有checkbox按钮
                            if(item.parentIds != ""){
                                id += "," + item.parentIds;
                            }
                            allIds.push({id:id,builtIn:item.builtIn});
                            //如果当前模块选中了则将id放进选中数组
                            if(item.checked){
                                checkedIds.push(id);
                            }
                            //构造按钮
                            let buttons = new Array();
                            for(let btn of item.children){
                                let actId = "ACTION-"+btn.id;
                                allIds.push({id:actId,builtIn:btn.builtIn});
                                //如果按钮选中了则将id放进选中数组里
                                if(btn.checked){
                                    checkedIds.push(actId);
                                }
                                //构造按钮数据
                                buttons.push({id:actId,name:btn.name,dir:false,builtIn:btn.builtIn,parent:"MENU-"+btn.parent})
                            }
                            //添加模块到结果中
                            result.push({id:id,name:item.name,dir:false,builtIn:item.builtIn,children:buttons});
                        }
                }else{//如果分组下面没有东西
                     result.push({id:'',name:"空目录",dir:false});
                }
                
          }
          return {resources:result,authedRes:checkedIds,allIds:allIds};
 };

 export default convertRenderData;