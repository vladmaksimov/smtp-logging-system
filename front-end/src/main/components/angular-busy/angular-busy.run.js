/*@ngInject*/
export default function registerAngularBusy($templateCache) {
    $templateCache.put('angular-busy-ext.template.html', require('./angular-busy.html'));
}