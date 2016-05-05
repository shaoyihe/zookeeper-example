/**
 * Created by heshaoyi on 5/4/16.
 */

"use strict";

var http = require('http');
var express = require('express');
var bodyParser = require('body-parser');
var index = require('./routes');
var favicon = require('serve-favicon');

var app = express();

app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use("/", index);

app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

app.use(function (err, req, res, next) {
    console.log(err);
    res.json({message: err.message});
});

var server = http.createServer(app);
var port = 3000;
server.listen(port);
console.log(`listening ${port}`);