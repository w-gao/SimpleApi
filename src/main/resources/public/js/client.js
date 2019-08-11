"use strict";

const sendMessage = function(from, msg) {
    $("#chat_wrapper").append('<div class="chat_msg"><span class="sender">' + from + ' </span><span class="msg">' + msg + '</span></div>');
    const curMsg = $(".chat_msg:last");
    setTimeout(function () {
        curMsg.remove()
    }, 5000);
};

var webSocket;

setupSocket();

var CommCode = {
    PING: 0x00,
    CONNECT_REQUEST: 0x01,
    CONNECT_RESPONSE: 0x02,
    LOGIN: 0x03,
    TEXT: 0x08,
};

let Packet = {
    // length:
    //  - byte +1
    //  - short +2
    //  - int +4
    //  - double +4
    //  - float +4
    //  - string 2+2*strLen
    output: function (length) {
        this.buffer = new Uint8Array(length);
        this.idx = 0;
        this.writeByte = function (b) {
            this.buffer[this.idx] = b & 255;
            this.idx++;
        };
        this.writeShort = function (s) {
            this.buffer[this.idx] = (s >>> 8) & 255;
            this.buffer[this.idx + 1] = s & 255;
            this.idx += 2;
        };
        this.writeInt = function (i) {
            this.buffer[this.idx] = ((i >>> 24) & 0xFF);
            this.buffer[this.idx + 1] = ((i >>> 16) & 0xFF);
            this.buffer[this.idx + 2] = ((i >>> 8) & 0xFF);
            this.buffer[this.idx + 3] = (i & 0xFF);
            this.idx += 4
        };
        this.writeFloat = function (f) {
            this.writeInt(f * 300)
        };
        this.writeDouble = function (d) {
            this.writeInt(d * 1e6)
        };
        this.writeString = function (s) {
            this.writeShort(s.length);
            for (let i = 0; i < s.length; i++) {
                this.writeShort(s.charCodeAt(i))
            }
        }
    },
    input: function (buffer) {
        this.buffer = new Uint8Array(buffer);
        this.idx = 0;
        this.eof = function () {
            return this.idx < this.buffer.length
        };
        this.readByte = function () {
            const i = this.idx;
            this.idx++;
            return this.buffer[i]
        };
        this.readShort = function () {
            const i = this.idx;
            this.idx += 2;
            return ((this.buffer[i] & 255) << 8) + (this.buffer[i + 1] & 255)
        };
        this.readInt = function () {
            const i = this.idx;
            this.idx += 4;
            return ((this.buffer[i] & 255) << 24)
                + ((this.buffer[i + 1] & 255) << 16)
                + ((this.buffer[i + 2] & 255) << 8)
                + ((this.buffer[i + 3] & 255))
        };
        this.readFloat = function () {
            return this.readInt() / 300
        };
        this.readDouble = function () {
            return this.readInt() / 1e6
        };
        this.readString = function () {

            const l = this.readShort();
            let str = '';
            for (let r = 0; r < l; r++) {
                const d = this.readShort();
                d > 0 && (str += String.fromCharCode(d))
            }
            return str;
        }
    }
};

function setupSocket() {

    $.get("server", function (data) {

        webSocket = new WebSocket(data);
        webSocket.binaryType = "arraybuffer";

        webSocket.onopen = onOpen;
        webSocket.onclose = onClose;
        webSocket.onmessage = onMessage;
        webSocket.onerror = function (ev) { console.log(ev); }

        function onOpen(evt) {

        }

        function onClose(evt) {

        }

        function onMessage(evt) {

            for (let i = new Packet.input(evt.data); i.eof();) {
                switch (i.readByte()) {
                    case CommCode.TEXT:
                        sendMessage(i.readString(), i.readString());
                        break;

                }
            }
        }

    });
}

function login(username) {

    let pk = new Packet.output(1 + 2 + 2 * username.length);
    pk.writeByte(CommCode.LOGIN);
    pk.writeString(username);

    webSocket.send(pk.buffer);
    return true;
}

function chat(message) {

    let username = 'Player';    // Placeholder

    let pk = new Packet.output(1 + 2 + 2 * username.length + 2 + 2 * message.length);
    pk.writeByte(CommCode.TEXT);
    pk.writeString(username);
    pk.writeString(message);

    webSocket.send(pk.buffer);
}
