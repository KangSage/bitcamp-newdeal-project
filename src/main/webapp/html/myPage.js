"use strict"



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