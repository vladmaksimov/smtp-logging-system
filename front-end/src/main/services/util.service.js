import {DEFAULT_STATUS, DEFAULT_PAGE, DEFAULT_PAGE_SIZE} from "../configs/constants";

/*@ngInject*/
export default function utilService(toaster) {

    return {
        createPageable: createPageable,
        checkSearch: checkSearch,
        errorHandler: errorHandler,
        showWarning: showWarning
    };

    function createPageable(page, size, status, search) {
        let pageable = {};

        if (page && page !== DEFAULT_PAGE) pageable.page = page;
        if (size && size !== DEFAULT_PAGE_SIZE) pageable.size = size;
        if (status && status !== DEFAULT_STATUS) pageable.status = status;
        if (search) pageable.search = search;

        return pageable;
    }

    function checkSearch(search) {
        if (typeof search === 'string') {
            return search && search.trim().length > 0
        } else {
            toaster.warning('Incorrect type of search value!', search);
            return false;
        }
    }

    function errorHandler(error) {
        toaster.error('Load data error!', error.data.error);
    }

    function showWarning(title, message) {
        toaster.warning(title, message);
    }

}