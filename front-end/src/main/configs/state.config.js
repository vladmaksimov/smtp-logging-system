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
            url: '/',
            template: require('../controllers/home-page/home-page.controller.html'),
            controller: 'HomePageController',
            controllerAs: 'vm',
            resolve: {
                logKeys: ($stateParams, logService) => {
                    return logService.getLogKeys();
                }
            }
        });
}