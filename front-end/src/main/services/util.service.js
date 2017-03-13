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

        if (page) pageable.page = page;
        if (size) pageable.size = size;
        if (status) pageable.status = status;
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