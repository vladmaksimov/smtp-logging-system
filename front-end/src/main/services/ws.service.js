import {WS_URL} from "../configs/constants";
import SockJS from "sockjs-client";
import {Stomp} from "stompjs/lib/stomp";

let socket;

/*@ngInject*/
export default function wsService($q) {

    return {
        connect: connect,
        disconnect: disconnect
    };

    function connect() {
        let defer = $q.defer();

        const client = new SockJS(WS_URL, null, null);
        const stomp = Stomp.over(client);
        stomp.heartbeat.outgoing = 0;
        stomp.heartbeat.incoming = 10000;
        stomp.debug = null;
        stomp.connect('guest', 'guest',
            () => defer.resolve(socket = {client, stomp}),
            () => defer.reject());

        return defer.promise;
    }

    function disconnect() {
        if (socket) {
            const {stomp, client} = socket;

            if (stomp != null) {
                stomp.onclose = undefined;
                stomp.disconnect();
            }

            if (client != null) {
                client.close();
            }

            socket = null;
        }
    }
}