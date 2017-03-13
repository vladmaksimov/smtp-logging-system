import "./details.controller.scss";
import {STATES} from "../../configs/constants";

let vm;

/*@ngInject*/
export default function DetailsController($state, $stateParams, logService, utilService, details, logKey) {

    return {
        $onInit: $onInit,
        toMainPage: toMainPage,
        paginate: paginate,
        updateFilter: updateFilter,
        searching: searching,
        clearSearch: clearSearch
    };

    function $onInit() {
        vm = this;
        vm.$stateParams = $stateParams;
        vm.logService = logService;
        vm.utilService = utilService;

        vm.logKey = logKey.data;
        vm.page = details.data;
        vm.page.number += 1;
        vm.search = null;
        vm.searchMode = false;
    }

    function paginate() {
        let pageable = vm.utilService.createPageable(vm.page.number - 1, vm.page.size, null, vm.search);
        vm.logService.getLogDetails($stateParams.id, pageable)
            .then(setPage)
            .catch(vm.utilService.handleError);
    }

    function updateFilter() {
        let pageable = vm.utilService.createPageable(null, vm.page.size, null, vm.search);
        vm.logService.getLogDetails($stateParams.id, pageable)
            .then(setPage)
            .catch(vm.utilService.handleError);
    }

    function searching() {
        if (vm.utilService.checkSearch(vm.search)) {
            let pageable = vm.utilService.createPageable(null, null, null, vm.search);
            vm.logService.getLogDetails($stateParams.id, pageable)
                .then(setSearchPage)
                .catch(vm.utilService.handleError);
        }
    }

    function clearSearch() {
        let pageable = vm.utilService.createPageable(null, null, null, null);
        vm.logService.getLogDetails($stateParams.id, pageable)
            .then(clearSearchPage)
            .catch(vm.utilService.handleError);
    }

    function toMainPage() {
        $state.go(STATES.home)
    }

    //private function

    function setPage(response) {
        vm.page = response.data;
        vm.page.number += 1;
    }

    function setSearchPage(response) {
        setPage(response);
        vm.searchMode = true;
    }

    function clearSearchPage(response) {
        setPage(response);
        vm.searchMode = false;
        vm.search = null;
    }

}