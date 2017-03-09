export const ROUTES = {
    getLogKeys: '/api/key/get',
    getLogKeyById: '/api/key/get/{id}',
    getLogDetails: '/api/details/get/{id}'
};

export const STATES = {
    details: 'details',
    home: 'home'
};

export const PAGE_SIZE = [10, 20, 30, 40, 50, 100];
export const STATUS_FILTER = ['ALL', 'PROCESSING', 'DEFERRED', 'SENT', 'BOUNCED'];
export const DEFAULT_STATUS = 'ALL';