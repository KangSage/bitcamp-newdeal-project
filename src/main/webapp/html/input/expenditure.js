'use strict'

//var formState = 'view';

$('#add-btn').on('click', () => {
    console.log('추가 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/add`, 
        {
            'memberNo': 1 /*$('#mno').val()*/,
            'amountType': $('#type').val(),
            'history': $('#history').val(),
            'amount': $('#amount').val(),
            'category': $('#category').val(),
            'memo': $('#memo').val(),
            'happenDate': $('#date').val()
        },
        function(data) {
            console.log(data);
            location.href = '../main/list.html';
        },
        'json');
});





// 수입 화면으로 변환
$('#in-btn').on('click',() => {
    $('.form-control').val('');
    $('#type').val('수입');
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
    $('.form-control').val('');
    $('#type').val('지출');
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
