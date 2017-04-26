// angular and other global libraries
import angular from 'angular';
import uirouter from 'angular-ui-router';
import translate from 'angular-translate';
import toaster from 'angularjs-toaster';
import ngAnimate from 'angular-animate';
import uibootstrap from 'angular-ui-bootstrap';
import smartTable from 'angular-smart-table';
import 'angular-busy';
import 'bootstrap/dist/js/bootstrap.min.js';

// styles
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/css/bootstrap-theme.min.css';
import 'angularjs-toaster/toaster.scss';
import 'angular-busy/angular-busy.css';

// components and directives
import paginationComponent from './components/pagination/pagination.component';
import pageFilterComponent from './components/page-filter/page-filter.component';
import pageControlsComponent from './components/page-controls/page-controls.component';
import keyFilterComponent from './components/key-filter/key-filter.component';

export default angular
    .module('dependency.vendor', [
        // external dependencies
        uirouter,
        translate,
        toaster,
        ngAnimate,
        uibootstrap,
        smartTable,
        'cgBusy',

        // local dependencies
        paginationComponent,
        pageFilterComponent,
        pageControlsComponent,
        keyFilterComponent
    ])
    .name;