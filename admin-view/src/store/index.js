import Vue from 'vue'
import Vuex from 'vuex'

import {permission,principal} from './auth'
import transition from './transition'


Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        principal,
        permission,
        transition
    }
});
