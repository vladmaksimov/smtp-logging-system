import {ROUTES} from "../configs/constants";

/*@ngInject*/
export default function logService($http) {

    return {
        getLogKeys: getLogKeys,
        getLogKeyById: getLogKeyById,
        getLogDetails: getLogDetails
    };

    function getLogKeys(pageable) {
        return $http({
            method: 'GET',
            url: ROUTES.getLogKeys,
            params: pageable
        });
    }

    function getLogKeyById(id) {
        return $http({
            method: 'GET',
            url: ROUTES.getLogKeyById.replace('{id}', id)
        });
    }

    function getLogDetails(id, pageable) {
        return $http({
            method: 'GET',
            url: ROUTES.getLogDetails.replace('{id}', id),
            params: pageable
        });
    }

}