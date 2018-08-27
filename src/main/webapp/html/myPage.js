"use strict"


//비밀번호 변경
$('#update-password').on('click',()=>{
  (async function getFormValues () {
    const {value: formValues} = await swal({
      title: '비밀번호 변경',
      html:
        '<input id="swal-input1" class="swal2-input" placeholder="현재 비밀번호">' +
        '<input id="swal-input2" class="swal2-input" placeholder="새로운 비밀번호">'+
        '<input id="swal-input2" class="swal2-input" placeholder="새로운 비밀번호">',
      focusConfirm: false,
      preConfirm: () => {
        return [
          document.getElementById('swal-input1').value,
          document.getElementById('swal-input2').value
        ]
      }
    })
    
    if (formValues) {
        swal('성공',
             '비밀번호가 변경되었습니다.',
             'success')
    }else{
        swal('실패',
                '비밀번호 변경에 실패하였습니다.',
                'error')
    }
    
    })()
})

//로그아웃
$('#logout-btn').on('click',()=>{
    swal({
        title: '로그아웃 <br> 하시겠습니까?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'yes'
      }).then((result) => {
        if (result.value) {
          swal(
            '로그아웃 성공',
            '로그아웃 되었습니다.',
            'success'
          )
        }
      })
})

//회원탈퇴
$('#dropout-btn').on('click',()=>{
    swal({
        title: '정말로 <br> 탈퇴하시겠습니까?',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'yes'
      }).then((result) => {
        if (result.value) {
          swal(
            '탈퇴 성공',
            '탈퇴 되었습니다.',
            'success'
          )
        }
      })
})