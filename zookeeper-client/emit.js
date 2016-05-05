/**
 * Created by heshaoyi on 5/5/16.
 */

"use strict";

var events = require('events');

function test() {
    var emit = new events();
    emit.emit("done", "other");
    return emit;
}

test().on("done", function () {
    console.log(arguments);
});