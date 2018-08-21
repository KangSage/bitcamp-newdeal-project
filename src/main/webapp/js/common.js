"use strict";

var serverApiAddr = "http://localhost:8080";

// DOM API가 완성 된 뒤에 실행 하라는 의미.
$(() => {
    $('footer').load(`${serverApiAddr}/footer.html`);
});

