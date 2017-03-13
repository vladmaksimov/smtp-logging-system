export const ROUTES = {
    getLogKeys: '/api/key/get',
    getLogKeyById: '/api/key/get/{id}',
    getLogDetails: '/api/details/get/{id}'
};

export const STATES = {
    details: 'details',
    home: 'home'
};

export const WS_CHANNEL_PREFIX = '/topic/';
export const WS_URL = 'http://localhost:8086/websocket';
export const WS_CHANNEL = 'key/update';
export const WS_TICKET_INTERVAL = 10000;

export const PAGE_SIZE = [10, 20, 30, 40, 50, 100];
export const STATUS_FILTER = ['ALL', 'PROCESSING', 'DEFERRED', 'SENT', 'BOUNCED'];
export const DEFAULT_STATUS = 'ALL';