"use strict";

let loginEmail = $('#loginEmail'),
    loginPassword = $('#loginPassword'),
    loginSaveEmail = $('#loginSaveEmail'),
    loginBtn = $('#login-btn');

loginBtn.on("click", () => {

    $('.validate-form').trigger('validate-email');

    
$.post(`${serverApiAddr}/json/auth/login`, {
    'email': loginEmail.val(),
    'password': loginPassword.val(),
    'saveEmail': loginSaveEmail.prop('checked')
}, (result) => {
    if (result.status === 'success') {
        swal('감사합니다!',
            '로그인 되었습니다.',
            'success'
        ).then(function() {
            location.href = 'main/list.html';
        });
    } else if (result.status === 'fail') {
        swal('로그인 실패!',
            '아이디 혹은 비밀번호가 맞지 않습니다.',
            'error'
        )
    }
}, 'json')
    .fail(() => {
        swal({
            title: '로그인 실패!',
            text: '서버와의 통신에 알 수 없는 문제가 생겼습니다.',
            type: 'error',
            confirmButtonColor: "#e83e8c"
        });
    });
});