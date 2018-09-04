"use strict";


/*$(document).ready(
    $.getJSON(`${serverApiAddr}/json/member/list`, function(data){
        console.log(${'name'});
        
    });
);*/

$(document).ready(function() {
    
    $.getJSON(`${serverApiAddr}/json/member/list`, function(data) {
            console.log(data.name);
            $("#name").html(data.name);
    });
});


//비밀번호 변경
$('#update-password').on('click', async ()=>{
    const {value: formValues} = await swal({
      title: '비밀번호 변경',
      html:
        '<input type="password" id="swal-old-password" class="swal2-input" placeholder="현재 비밀번호">' +
        '<input type="password" id="swal-new-password-1" class="swal2-input" placeholder="새로운 비밀번호">'+
        '<input type="password" id="swal-new-password-2" class="swal2-input" placeholder="비밀번호 확인">',
      focusConfirm: false,
      preConfirm: () => {
        return [
            $('#swal-old-password').val(),
            $('#swal-new-password-1').val(),
            $('#swal-new-password-2').val(),
        ]
      }
    });
    if (formValues) {
        if (formValues[1] === formValues[2]) {
            $.post(`${serverApiAddr}/json/member/update`, {
                oldPassword : formValues[0],
                newPassword : formValues[1]
            }, async (result) => {
                console.log(result);
                if (result.status === 'success') {
                   await swal('비밀번호 변경 성공!',
                        '로그인 페이지로 이동합니다..',
                        'success');
                    await $.getJSON(`${serverApiAddr}/json/auth/logout`, (result) => {
                        if (result === 'login.html') {
                            location.href = `${serverApiAddr}/html/${result}`
                        } else {
                            alert("실패!");
                        }
                    })
                } else if (result.status === 'update-fail') {
                    await swal(
                        '현재 비밀번호가 틀렸습니다.',
                        '정확한 현재 비밀번호를 넣어주세요.',
                        'error')
                }
            }, 'json');
        } else {
            swal('비밀번호 변경 실패',
                '새로운 비밀번호를 확인해주세요.',
                'error')
        }
    } else {
        swal('실패',
                '알 수 없는 이유로 비밀번호 변경에 실패하였습니다.',
                'error')
    }
});

//로그아웃
$('#logout-btn').on('click',()=>{
    swal({
        title: '로그아웃 <br> 하시겠습니까?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'yes'
      }).then((swalResult) => {
          if (swalResult.value) {
              $.getJSON(`${serverApiAddr}/json/auth/logout`, (result) => {
                  if (result === 'login.html') {
                      swal(
                          '로그아웃 성공',
                          '로그아웃 되었습니다.',
                          'success'
                      ).then(()=>{
                          location.href = `${serverApiAddr}/html/${result}`
                      })
                  } else {
                      alert("실패!");
                  }
              });
          } else if (swalResult.dismiss === 'cancel') {
                location.reload();
          }
      });
});


//회원탈퇴
$('#dropout-btn').on('click', async () => {
    const {value: formValues} = await swal({
        title: '회원 탈퇴',
        text: '회원 탈퇴를 위해 이메일과 비밀번호를 입력하세요.' ,
        html:
            '<input type="email" class="swal2-input" id="swal-input-email" placeholder="이메일">' +
            '<input type="password" class="swal2-input" id="swal-input-password" placeholder="비밀번호">',
        focusConfirm: false,
        confirmButtonColor: "#e83e8c",
        preConfirm: () => {
            return [
                $('#swal-input-email').val(),
                document.getElementById('swal-input-password').value
            ]
        }
    });
    if (formValues) {
        $.post(`${serverApiAddr}/json/member/exit`, {
            email : formValues[0],
            password : formValues[1]
        }, (result) => {
            console.log(result);
            if (result.status === 'success') {
                location.href = `${serverApiAddr}/html/login.html`;
            } else if (result.status === "exit-fail") {
                swal(
                    '회원탈퇴 실패!',
                    '이메일 또는 비밀번호가 잘못되었습니다.<br>확인해주세요',
                    'error'
                )
            }
        }, 'json');
    }
});