'use strict'


$('#update-btn').on('click',() => {
    console.log('완료 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/update`, {
        'no':3/*$('#no').val()*/,
        'amountType': $('#type').val(),
        'history': $('#history').val(),
        'amount': $('#amount').val(),
        'category': $('#category').val(),
        'memo': $('#memo').val(),
        'happenDate': $('#date').val()
    }, (result) => {
        if (result.status === 'success') {
            swal('감사합니다!',
                '변경 되었습니다.',
                'success'
            ).then(function() {
                /*location.href = '../main/list.html';*/
            });
        } else {
            swal('변경 실패!',
                'error')
        }
    }, 'json')
    .fail(() => {
        swal({
            title: '변경 실패!',
            text: '서버와의 통신에 알 수 없는 문제가 생겼습니다.',
            type: 'error',
            confirmButtonColor: "#e83e8c"
        });
    });
})

$('#delete-btn').on('click',() => {
    console.log('삭제 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/delete`, {no:2})
        .done(function(data) {
            console.log(data)
                if (data.status == 'success') {
                swal('감사합니다!',
                        '삭제 되었습니다.',
                        'success'
                    ).then(function() {
                        location.href = '../main/list.html';
                    });
            } else {
                console.log(data.message);
                swal('삭제 실패!',
                     'error')
            }
        }).fail(() => {
            swal({
                title: '삭제 실패!',
                text: '서버와의 통신에 알 수 없는 문제가 생겼습니다.',
                type: 'error',
                confirmButtonColor: "#e83e8c"
            });
        });
});

// 수입 화면으로 변환
$('#in-btn').on('click',() => {
    $('#type').val('수입');
    $('#category').val('Choose...');
    console.log($('#type').val());
    
    $('#ex-btn').css('background', '#e1c5ec');
    $('#in-btn').css('background', '#d33f8d');
    
    $('#input-plcnm').css('visibility', 'hidden');
    $('#input-plc').css('visibility', 'hidden');
    $('#input-rcpt').css('visibility', 'hidden');
    
    $('.ex-category').hide();
    $('.in-category').show();
    
});

// 지출 화면으로 변환
$('#ex-btn').on('click',() => {
    $(".hst").empty();
    $('#type').val('지출');
    $('#category').val('Choose...');
    console.log($('#type').val());
    
    $('#in-btn').css('background', '#e1c5ec');
    $('#ex-btn').css('background', '#d33f8d');
    
    $('.input-group').css('visibility', 'visible');
    
    $('.in-category').hide();
    $('.ex-category').show();
    
});


$('#back-btn').on('click',() => {
    location.href = '../main/list.html';
});