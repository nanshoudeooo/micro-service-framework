/******************************************************
 * 将扁平化数据格式层嵌套的树形结构数据
 *  
 *******************************************************/
     
      /**
       * 判断是否是跟节点
       * @param {object} pid 
       */
      var assertRoot = function (pid) {
        pid = pid + "";
        return pid === "undefined" || pid === "" || pid === "0"
      };
      /**
       * 解析子节点
       * @param {Object} parentNode 
       * @param {Object[]} allNodes 
       */
      var resolveChildren = function (parentNode, allNodes) {
        for (let currentNode of allNodes) {
          if (currentNode.parent === parentNode.id) {
            if (!parentNode.children) {
              parentNode.children = new Array();
            }
            parentNode.children.push(currentNode);
            resolveChildren(currentNode, allNodes);
          }
        }
      };

      /**
       * 格式化组织，将扁平结构格式化成树形结构
       * @param {object[]} orgList 
       */
      var formateOrg = function (orgList) {
        var rootNodes = new Array();
        for (let org of orgList) {
          if (assertRoot(org.parent)) {
            org.root = true;
            rootNodes.push(org);
          }
        }

        for (let rootNode of rootNodes) {
          resolveChildren(rootNode, orgList);
        }
        return rootNodes;
      };

export default formateOrg;