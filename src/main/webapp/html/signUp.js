"use strict";

let authBtn = $('#auth-btn'),
    joinBtn = $('#join-btn'),
    signUpName = $('#signUpName'),
    signUpEmail = $('#signUpEmail'),
    signUpPassword = $('#signUpPassword');


authBtn.on('click', (e) => {
    e.preventDefault();
    console.log("인증 버튼 눌림");
});

joinBtn.on('click', () => {
    console.log('회원가입 버튼 클릭');
    $('.validate-form').trigger('validate-email');

    $.post(`${serverApiAddr}/json/member/signUp`, {
        'name': signUpName.val(),
        'email': signUpEmail.val(),
        'password': signUpPassword.val()
    }, (result) => {
        if (result.status === 'success') {
            swal('감사합니다!',
                '회원가입 되었습니다.',
                'success'
            ).then(function() {
                location.href = 'login.html';
            });
        } else if (result.status === 'fail') {
            swal('회원가입 실패!',
                '이미 존재하는 아이디입니다.',
                'error'
            ).then(function() {
                location.reload();
            });
        }
    }, 'json')
        .fail(() => {
            swal({
                title: '회원가입 실패!',
                text: '서버와의 통신에 알 수 없는 문제가 생겼습니다.',
                type: 'error',
                confirmButtonColor: "#e83e8c"
            });
        });
});
