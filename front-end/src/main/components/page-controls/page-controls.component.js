import PageControlsComponentCtrl from "./page-controls.controller";
import "./page-controls.component.scss";

const PageControlsComponent = {
    template: require('./page-controls.template.html'),
    controller: PageControlsComponentCtrl,
    controllerAs: 'vm',
    bindings: {
        page: '=',
        status: '=',
        search: '=',
        updateFilter: '=',
        paginate: '='
    }
};

export default angular
    .module('pageControlsModule', [])
    .component('pageControlsComponent', PageControlsComponent)
    .name;