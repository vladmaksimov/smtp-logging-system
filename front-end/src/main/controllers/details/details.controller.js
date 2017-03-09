import "./details.controller.scss";
import {STATES} from "../../configs/constants";


/*@ngInject*/
export default function DetailsController($state, $stateParams, logService, utilService, toaster, details, logKey) {

    return {
        $onInit: $onInit,
        toMainPage: toMainPage,
        paginate: paginate,
        updateFilter: updateFilter,
        searching: searching,
        clearSearch: clearSearch
    };

    function $onInit() {
        this.logKey = logKey.data;
        this.page = details.data;
        this.page.number += 1;
        this.search = null;
        this.searchMode = false;
    }

    function paginate() {
        let pageable = utilService.createPageable(this.page.number - 1, this.page.size, null, this.search);
        logService.getLogDetails($stateParams.id, pageable)
            .then((response) => {
                this.page = response.data;
                this.page.number += 1;
            });
    }

    function updateFilter() {
        let pageable = utilService.createPageable(null, this.page.size, null, this.search);
        logService.getLogDetails($stateParams.id, pageable)
            .then((response) => {
                this.page = response.data;
                this.page.number += 1;
            });
    }

    function searching() {
        if (utilService.checkSearch(this.search)) {
            let pageable = utilService.createPageable(null, null, null, this.search);
            logService.getLogDetails($stateParams.id, pageable)
                .then((response) => {
                    this.page = response.data;
                    this.page.number += 1;
                    this.searchMode = true;
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
        logService.getLogDetails($stateParams.id, pageable)
            .then((response) => {
                this.page = response.data;
                this.page.number += 1;
                this.searchMode = false;
                this.search = null;
            })
            .catch((error) => {
                toaster.warning('Load data error!', error.data.error);
            });
    }

    function toMainPage() {
        $state.go(STATES.home)
    }


}