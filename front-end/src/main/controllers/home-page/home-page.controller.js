import "./home-page.controller.scss";
import {STATES, DEFAULT_STATUS, WS_CHANNEL, WS_CHANNEL_PREFIX, WS_TICKET_INTERVAL} from "../../configs/constants";

let vm;

/*@ngInject*/
export default function HomePageController($state, $scope, $interval, logService, utilService, wsService, logKeys) {

    return {
        $onInit: $onInit,
        viewDetails: viewDetails,
        paginate: paginate,
        updateFilter: updateFilter,
        searching: searching,
        // clearSearch: clearSearch,
        toggleFilters: toggleFilters
    };

    function $onInit() {
        vm = this;
        vm.$scope = $scope;
        vm.$interval = $interval;
        vm.logService = logService;
        vm.utilService = utilService;
        vm.wsService = wsService;

        vm.page = logKeys.data;
        vm.page.number += 1;
        vm.search = $state.current.name === 'search' && $state.params.search ? $state.params.search : null;
        vm.searchMode = false;
        vm.showFilters = false;
        vm.status = $state.params.status ? $state.params.status : DEFAULT_STATUS;

        initStomp();
    }

    function paginate() {
        let pageable = vm.utilService.createPageable(vm.page.number, vm.page.size, this.status, vm.search);
        pageable.conditions = JSON.stringify([{field: 'from', value: 'vasanthi', type: 'string', comparison: 'eq'}]);
        $state.go($state.current.name, pageable, {inherit: false});
    }

    function updateFilter() {
        let pageable = vm.utilService.createPageable(null, vm.page.size, this.status, vm.search);
        $state.go($state.current.name, pageable, {inherit: false});
    }

    function searching() {
        if (vm.utilService.checkSearch(vm.search)) {
            let pageable = vm.utilService.createPageable(null, null, null, vm.search);
            $state.go('search', pageable, {inherit: false});
        }
    }

    // function clearSearch() {
    //     let pageable = vm.utilService.createPageable(null, null, null, null);
    //     vm.logService.getLogKeys(pageable)
    //         .then(clearSearchPage)
    //         .catch(vm.utilService.handleError);
    // }

    function viewDetails(id) {
        $state.go(STATES.details, {id: id});
    }
    
    function toggleFilters() {
        vm.showFilters = !vm.showFilters;
    }

    //private function

    function initStomp() {
        let process = false;

        vm.wsService.connect()
            .then((socket) => socket.stomp.subscribe(WS_CHANNEL_PREFIX + WS_CHANNEL, (message) => process = true))
            .catch(() => vm.utilService.showWarning('WebSocket Error!', 'Can\'t connect to server'));

        vm.$interval(function () {
            if (process) {
                vm.paginate();
                process = false;
            }
        }, WS_TICKET_INTERVAL);

        vm.$scope.$on("$destroy", function () {
            vm.wsService.disconnect();
        })
    }

/*    function setPage(response) {
        vm.page = response.data;
        vm.page.number += 1;
    }

    function setSearchPage(response) {
        setPage(response);
        vm.searchMode = true;
        vm.status = DEFAULT_STATUS;
    }

    function clearSearchPage(response) {
        setPage(response);
        vm.searchMode = false;
        vm.search = null;
        vm.status = DEFAULT_STATUS;
    }*/

};