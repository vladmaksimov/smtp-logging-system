'use strict';

// local configuration files
import routeConfig from './configs/route.config';
import stateConfig from './configs/state.config';

// controllers
import HomePageController from './controllers/home-page/home-page.controller';
import DetailsController from './controllers/details/details.controller';

// services
import logService from './services/log.service';
import utilService from './services/util.service';
import wsService from './services/ws.service';

// run functions
import registerAngularBusy from './components/angular-busy/angular-busy.run';

// vendor
import vendorModule from './vendor';

const moduleName = angular
    .module('app', [vendorModule])
    .config(routeConfig)
    .config(stateConfig)
    .controller('HomePageController', HomePageController)
    .controller('DetailsController', DetailsController)
    .service('logService', logService)
    .service('utilService', utilService)
    .service('wsService', wsService)
    .run(registerAngularBusy)
    .name;


export default angular.bootstrap(document, [moduleName])