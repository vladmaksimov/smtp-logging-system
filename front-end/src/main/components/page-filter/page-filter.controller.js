import {STATUS_FILTER, PAGE_SIZE} from "../../configs/constants";

/*@ngInject*/
export default function PageFilterComponentCtrl() {

    return {
        $onInit: $onInit,
        $onDestroy: $onDestroy
    };

    function $onInit() {
        this.statusArray = STATUS_FILTER;
        this.pageSizeArray = PAGE_SIZE;
    }

    function $onDestroy() {

    }
}