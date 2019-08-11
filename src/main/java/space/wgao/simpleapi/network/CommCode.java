package space.wgao.simpleapi.network;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public interface CommCode {

    //
    //
    byte PING = 0x00,

    // request to join a game server
    // C->S
    // payload: version(int)
    CONNECT_REQUEST = 0x01,

    //
    // S->C
    // payload: status(int)
    //  status: 0 = success, 1 = failed_full
    CONNECT_RESPONSE = 0x02,

    // Log into a game instance
    // C->S
    // payload: name(string)
    LOGIN = 0x03,

    // C->S
    // S->C
    // payload: username(string)
    //          payload(string)
    TEXT = 0x08;

}
