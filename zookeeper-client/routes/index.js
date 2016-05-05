/**
 * Created by heshaoyi on 5/4/16.
 */

"use strict";

var express = require('express');
var request = require('request');
var server = require('../util/zookeeper-server');
var router = express.Router();

router.get("/", function (req, res, next) {
    server.getAvaliableAdd(function (err, host) {
        if (err) {
            next(err);
        } else {
            request(`${host}/find-me`, function (error, response, body) {
                if (err) {
                    next(err);
                } else {
                    console.log(`query ${host}/find-me`);
                    res.end(body);
                }
            })
        }
    });
});

module.exports = router;