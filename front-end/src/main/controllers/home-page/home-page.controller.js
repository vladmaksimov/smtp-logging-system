import "./home-page.controller.scss";
import {STATES, DEFAULT_STATUS} from "../../configs/constants";
import {connect} from "../../configs/websocket/socket";


/*@ngInject*/
export default function HomePageController($state, $stateParams, logService, utilService, toaster, logKeys) {

    return {
        $onInit: $onInit,
        initWebSocket: initWebSocket,
        viewDetails: viewDetails,
        paginate: paginate,
        updateFilter: updateFilter,
        searching: searching,
        clearSearch: clearSearch
    };

    function $onInit() {
        this.page = logKeys.data;
        this.page.number += 1;
        this.search = null;
        this.searchMode = false;
        this.status = DEFAULT_STATUS;
        connect();
        // this.socket = new SockJS('http://localhost:8086/websocket')
    }

    function initWebSocket() {
        // this.socket.onmessage(function (e) {
        //     console.log(e);
        // })
    }

    function paginate() {
        let pageable = utilService.createPageable(this.page.number - 1, this.page.size, this.status, this.search);
        logService.getLogKeys(pageable)
            .then((response) => {
                this.page = response.data;
                this.page.number += 1;
            })
            .catch((error) => {
                toaster.warning('Load data error!', error.data.error);
            });
    }

    function updateFilter() {
        let pageable = utilService.createPageable(null, this.page.size, this.status, this.search);
        logService.getLogKeys(pageable)
            .then((response) => {
                this.page = response.data;
                this.page.number += 1;
            })
            .catch((error) => {
                toaster.warning('Load data error!', error.data.error);
            });
    }

    function searching() {
        if (utilService.checkSearch(this.search)) {
            let pageable = utilService.createPageable(null, null, null, this.search);
            logService.getLogKeys(pageable)
                .then((response) => {
                    this.page = response.data;
                    this.page.number += 1;
                    this.searchMode = true;
                    this.status = DEFAULT_STATUS;
                })
                .catch((error) => {
                    toaster.warning('Load data error!', error.data.error);
                });
        } else {
            toaster.warning('Incorrect search data', 'Can\'t search by empty value!')
        }
    }

    function clearSearch() {
        let pageable = utilService.createPageable(null, null, null, null);
        logService.getLogKeys(pageable)
            .then((response) => {
                this.page = response.data;
                this.page.number += 1;
                this.searchMode = false;
                this.search = null;
                this.status = DEFAULT_STATUS;
            })
            .catch((error) => {
                toaster.warning('Load data error!', error.data.error);
            });
    }

    function viewDetails(id) {
        $state.go(STATES.details, {id: id});
    }

};