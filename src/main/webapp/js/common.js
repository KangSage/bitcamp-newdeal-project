"use strict";

var serverApiAddr = "http://localhost:8080";
//var serverApiAddr = "http://192.168.0.73:8080";

// DOM API가 완성 된 뒤에 실행 하라는 의미.
$(() => {
    $('footer').load(`${serverApiAddr}/html/footer.html`);
});

