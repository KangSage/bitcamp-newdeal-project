"use strict";

var eclipseServerApiAddr = "http://localhost:8080/bitcamp-web-18";
var serverApiAddr = "http://localhost:8004/";

// DOM API가 완성 된 뒤에 실행 하라는 의미.
$(() => {
    $('.container > header').load(`${serverApiAddr}/html/header.html`);
    $('.container > footer').load(`${serverApiAddr}/html/footer2.html`);
});

