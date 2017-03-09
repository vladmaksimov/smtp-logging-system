import PageFilterComponentCtrl from "./page-filter.controller";
import "./page-filter.component.scss";

const PageFilterComponent = {
    template: require('./page-filter.template.html'),
    controller: PageFilterComponentCtrl,
    controllerAs: 'vm',
    bindings: {
        page: '=',
        status: '=',
        search: '=',
        updateFilter: '='
    }
};

export default angular
    .module('pageFilterModule', [])
    .component('pageFilterComponent', PageFilterComponent)
    .name;