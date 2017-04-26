/*@ngInject*/
export default function stateConfig($stateProvider) {
    $stateProvider
        .state('details', {
            url: '/details/:id',
            template: require('../controllers/details/details.controller.html'),
            controller: 'DetailsController',
            controllerAs: 'vm',
            params: {
                page: 0,
            },
            resolve: {
                details: ($stateParams, $state, logService) => {
                    return logService.getLogDetails($stateParams.id);
                },
                logKey: ($stateParams, logService) => {
                    return logService.getLogKeyById($stateParams.id);
                },
            }
        })
        .state('home', {
            url: '/?page&size&status&conditions',
            template: require('../controllers/home-page/home-page.controller.html'),
            controller: 'HomePageController',
            controllerAs: 'vm',
            resolve: {
                logKeys: ($stateParams, logService) => {
                    const page = $stateParams.page ? $stateParams.page - 1 : null,
                        size = $stateParams.size,
                        status = $stateParams.status,
                        conditions = $stateParams.conditions;

                    return logService.getLogKeys({page: page, size: size, status: status}, conditions);
                }
            }
        })
        .state('search', {
            url: '/search/:search?page&size&status&conditions',
            template: require('../controllers/home-page/home-page.controller.html'),
            controller: 'HomePageController',
            controllerAs: 'vm',
            resolve: {
                logKeys: ($stateParams, $location, logService) => {
                    const page = $stateParams.page - 1,
                        size = $stateParams.size,
                        status = $stateParams.status,
                        search = $stateParams.search;
                    return logService.getLogKeys({page: page, size: size, status: status, search: search});
                }
            }
        });
}