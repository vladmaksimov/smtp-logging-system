import {KEY_FILTER_LIST, KEY_FILTER_OBJECTS, STATES, STATUS_FILTER} from "../../configs/constants";

let vm;

/*@ngInject*/
export default function KeyFilterComponentCtrl($state) {

    return {
        $onInit: $onInit,
        $onDestroy: $onDestroy,
        toggleFilters: toggleFilters,
        buildFilter: buildFilter,
        applyFilter: applyFilter
    };

    function $onInit() {
        vm = this;
        vm.conditions = $state.params.conditions ? JSON.parse($state.params.conditions) : null;
        vm.showFilters = false;
        vm.keyList = KEY_FILTER_LIST;
        vm.statusArray = STATUS_FILTER;
        vm.key = null;
        vm.filterList = [];

        if (vm.conditions) {
            initFilters();
            vm.showFilters = true;
        }
    }

    function $onDestroy() {
    }

    function toggleFilters() {
        vm.showFilters = !vm.showFilters;
    }

    function buildFilter() {
        let item = KEY_FILTER_OBJECTS[vm.key];

        if (vm.filterList.indexOf(item) === -1 && item) {
            vm.filterList.push(item);
            vm.keyList.forEach(function (key) {
                if (key.key === vm.key) {
                    key.disabled = true;
                }
            })
        }
    }

    function applyFilter() {
        let filters = [];
        vm.filterList.forEach(function (filter) {
            let value;
            if (filter.filter === 'firstEventDate') {
                value = filter.val.toDateString().slice(4);
            } else {
                value = filter.val;
            }
            filters.push({field: filter.filter, value: value, type: filter.type, comparison: filter.key})
        });

        console.log(filters);
        $state.go(STATES.home, {conditions: JSON.stringify(filters)});
    }

    function initFilters() {
        vm.conditions.forEach(function (condition) {
            let filter = KEY_FILTER_OBJECTS[condition.field];
            let value;
            if (filter.type === 'date') {
                value = new Date(condition.value);
            } else {
                value = condition.value;
            }
            filter.key = condition.comparison;
            filter.val = value;
            vm.filterList.push(filter);

            vm.keyList.forEach(function (key) {
                if (key.key === condition.field) {
                    key.disabled = true;
                }
            })
        })
    }

}