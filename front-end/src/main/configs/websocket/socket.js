import SockJS from "sockjs-client";
import {Stomp} from "stompjs/lib/stomp";
import {WS_URL, WS_CHANNELS, WS_CHANNEL_PREFIX} from "../constants";

let socket;

export function connect() {
    const options = {
        headers: ''
    };
    const client = new SockJS('http://localhost:8086/hello', null, null);
    const stomp = Stomp.over(client);
    stomp.heartbeat.outgoing = 0;
    stomp.heartbeat.incoming = 10000;
    stomp.debug = null;
    stomp.connect('guest', 'guest', function(frame) {
        console.log(frame);
        console.log(stomp);
        stomp.subscribe('/topic/greetings', function (data) {
            console.log(data);
        });
        console.log(stomp);
        stomp.send('/app/hello', JSON.stringify({ 'name': 'Joe' }));
    }, function (error) {
        console.log(error);
    });
    //
    //
    //
    // function fnSayHi() {
    //     stomp.send('/app/hello', {}, JSON.stringify("Joe"));
    // }

    // stomp.connect(() => {
    //     WS_CHANNELS.forEach((channel) => {
    //         stomp.subscribe(WS_CHANNEL_PREFIX + channel, (data) => {
    //             console.log(data);
    //             //todo process
    //         });
    //     });
    // });

    // stomp.onclose = onClose;

    socket = {client, stomp};
}

export function disconnect() {
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
