"use strict";

let loginEmail = $('#loginEmail'),
    loginPassword = $('#loginPassword'),
    loginSaveEmail = $('#loginSaveEmail'),
    loginBtn = $('#login-btn');



loginBtn.on("click", () => {
    let input = $('.validate-input .input100');

    var check = false;

    (function () {
        check = true;
        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) === false){
                showValidate(input[i]);
                check=false;
            }
        }
        return check;
    })();


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
            hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') === 'email' || $(input).attr('name') === 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() === ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
    }

if (check) {
    console.log('check == true');
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
}
});