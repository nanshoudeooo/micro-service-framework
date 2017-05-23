import {$$SessionStore,$$Func} from '@/util';

export default{
    getRoles(state){
       var roles = state.roles;
       if(!roles){
           return Array.of();
       }else{
           return roles;
       } 
    },
    getUsername(state){
       let user = state.user;
       if($$Func.isEmptyObject(user)){
           return "";
       }else{
           return user.username;
       } 
    },
    getUser(state){
       let user = state.user;
       if($$Func.isEmptyObject(user)){
           return {};
       }else{
           return user;
       } 
    }
}