import KeyFilterComponentCtrl from "./key-filter.controller";
import "./key-filter.component.scss";

const KeyFilterComponent = {
    template: require('./key-filter.template.html'),
    controller: KeyFilterComponentCtrl,
    controllerAs: 'vm',
    bindings: {
    }
};

export default angular
    .module('keyFilterModule', [])
    .component('keyFilterComponent', KeyFilterComponent)
    .name;