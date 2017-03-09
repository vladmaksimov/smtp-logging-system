import PaginationComponentCtrl from "./pagination.controller";
import "./pagination.component.scss";

const PaginationComponent = {
    template: require('./pagination.template.html'),
    controller: PaginationComponentCtrl,
    controllerAs: 'vm',
    bindings: {
        page: '=',
        status: '=',
        search: '=',
        paginate: '='
    }
};

export default angular
    .module('paginationModule', [])
    .component('paginationComponent', PaginationComponent)
    .name;