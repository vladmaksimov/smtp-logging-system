const environment = {
    development: {
        isProduction: false
    },
    production: {
        isProduction: true
    }
}[process.env.NODE_ENV || 'development'];

export const ROUTES = {
    getLogKeys: '/api/key/get',
    getLogKeyById: '/api/key/get/{id}',
    getLogDetails: '/api/details/get/{id}'
};

export const STATES = {
    details: 'details',
    home: 'home'
};

export const KEY_FILTER_LIST = [
    {key: 'status', value: 'Status'},
    {key: 'emailTo', value: 'Email To'},
    {key: 'emailFrom', value: 'Email From'},
    {key: 'date', value: 'Date'}
];

export const KEY_FILTER_OBJECTS = {
    emailTo: {
        filter: 'emailTo',
        value: 'Email To',
        type: 'string',
        key: 'eq',
        use: true,
        keys: [
            {key: 'lk', val: 'contains'},
            {key: 'eq', val: 'equals'},
            // {key: 'notlk', val: 'doesn\'t contain'}
        ]
    },
    emailFrom: {
        filter: 'emailFrom',
        value: 'Email From',
        type: 'string',
        key: 'eq',
        use: true,
        keys: [
            {key: 'lk', val: 'contains'},
            {key: 'eq', val: 'equals'},
            // {key: 'notlk', val: 'doesn\'t contain'}
        ]
    },
    date: {
        filter: 'firstEventDate',
        value: 'Date',
        type: 'date',
        key: 'eq',
        use: true,
        keys: [
            {key: 'eq', val: 'is'},
            {key: 'lt', val: 'less then'},
            {key: 'gt', val: 'greater then'}
        ]
    },
    status: {
        filter: 'status',
        value: 'Status',
        type: 'string',
        key: 'eq',
        use: true,
        val: 'SENT',
        keys: [
            {key: 'eq', val: 'is'},
        ]
    }
};

export const WS_CHANNEL_PREFIX = '/topic/';
export const WS_URL = environment.isProduction ? '/websocket' : 'http://localhost:8086/websocket';
export const WS_CHANNEL = 'key/update';
export const WS_TICKET_INTERVAL = 10000;

export const PAGE_SIZE = [10, 20, 30, 40, 50, 100];
export const STATUS_FILTER = ['SENT', 'PROCESSING', 'DEFERRED', 'BOUNCED'];
export const DEFAULT_STATUS = 'ALL';
export const DEFAULT_PAGE = 1;
export const DEFAULT_PAGE_SIZE = 10;