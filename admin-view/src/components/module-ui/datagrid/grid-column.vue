<template>
     <el-table-column :resizable='false'
                      :label='label'
                      :prop='prop'
                      :width='width'
                      :minWidth='minWidth'
                      :sortable='sortable ? "custom" : false'
                      :columnKey='columnKey'
                      :align='align'
                      :headerAlign='headerAlign'
                      :showTooltipWhenOverflow='showTooltipWhenOverflow'
                      :showOverflowTooltip='showOverflowTooltip'
                      :formatter='formatter'
                      :reserveSelection='reserveSelection'
                      :filterMethod='filterMethod'
                      :filteredValue='filteredValue'
                      :filters='filters'
                      :filterMultiple='filterMultiple'>
     </el-table-column>
</template>
<script>
    export default{
        name:'grid-column',
         props: {
            label: String,
            prop: String,
            width: {},
            minWidth: {},
            sortable: Boolean,
            columnKey: String,
            align: String,
            headerAlign: String,
            showTooltipWhenOverflow: Boolean,
            showOverflowTooltip: {type:Boolean,default:true},
            formatter: Function,
            reserveSelection: Boolean,
            filterMethod: Function,
            filteredValue: Array,
            filters: Array,
            filterMultiple: {
                type: Boolean,
                default: true
            }
        }
    }
</script>