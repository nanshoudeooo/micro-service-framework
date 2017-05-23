import Vue from 'vue'
import toolbar from './toolbar/tool-bar.vue'
import toolbarItem from './toolbar/tool-bar-item.vue'
import toolbarSplit from './toolbar/tool-bar-split.vue'
import panel from './panel.vue'
import searchForm from './searchform/search-form.vue'
import searchFormRow from './searchform/search-form-row.vue'
import dataGrid from './datagrid/data-grid.vue'
import gridColumn from './datagrid/grid-column.vue'

Vue.component('panel',panel);
Vue.component('tool-bar',toolbar);
Vue.component('tool-bar-item',toolbarItem);
Vue.component('tool-bar-split',toolbarSplit);
Vue.component('search-form',searchForm);
Vue.component('search-form-row',searchFormRow);
Vue.component('data-grid',dataGrid);
Vue.component('grid-column',gridColumn);

