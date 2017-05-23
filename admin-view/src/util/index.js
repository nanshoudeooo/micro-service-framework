import ajax from './ajax.js';
import store from './store.js';
import {Func} from './functions.js';

export var $$Http = ajax;
export var $$SessionStore = store.sessionStore;
export var $$LocalStore = store.localStore;
export var $$Func = Func;