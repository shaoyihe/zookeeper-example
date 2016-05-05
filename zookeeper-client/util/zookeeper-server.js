/**
 * Created by heshaoyi on 5/5/16.
 */

"use strict";

var zookeeper = require('node-zookeeper-client');
var async = require('async');
var fs = require('fs');
var events = require('events');


var client = zookeeper.createClient('localhost:2181');
const SERVER_PATH = '/servers/test-me';

function pathWatch(event) {
    if (event.getType() == zookeeper.Event.NODE_CHILDREN_CHANGED) {
        console.log("serverDatas changed");
        pathQuery();
    }
}

var serverDatas = [];

function pathQuery() {
    var emit = new events();
    client.getChildren(SERVER_PATH, pathWatch, function (error, children, stats) {
        if (error) {
            emit.emit("done", error);
            return;
        }
        children = children.map(a=>`${SERVER_PATH}/${a}`);
        async.map(children, client.getData.bind(client), function (err, datas) {
            if (err) {
                emit.emit("done", err);
                return;
            }
            serverDatas = datas.map(a=> {
                var result = JSON.parse(a.toString());
                return `${result.protrol}://${result.host}:${result.port}`
            });
            console.log("find new serverDatas " + serverDatas);
            emit.emit("done");
        });

    });
    return emit;
}
client.once('connected', pathQuery);

client.connect();


var lastQuery = 0;
function serverDataNext() {
    return serverDatas[lastQuery++ % serverDatas.length];
}

exports.getAvaliableAdd = function (cb) {
    if (serverDatas.length == 0) {
        pathQuery().on("done", function (err) {
            cb(err, serverDataNext());
        });
    } else {
        cb(undefined, serverDataNext());
    }
};