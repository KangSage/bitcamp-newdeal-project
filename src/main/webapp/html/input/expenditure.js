'use strict'

//var formState = 'view';

$('#add-btn').on('click', () => {
    console.log('추가 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/add`, 
        {
            
            'mno':11/*$('#mno').val()*/,
            'type': $('#type').val(),
            'history': $('#history').val(),
            'amount': $('#amount').val(),
            'category': $('#category').val(),
            'memo': $('#memo').val(),
            'date': $('#date').val()
        },
        function(data) {
            location.href = '../main/list.html';
        },
        'json');
});





// 수입 화면으로 변환
$('#ex-btn').on('click',() => {
    $('.form-control').val('');
    $('#type').val('수입');
    console.log($('#type').val());
    
    $('#ex-btn').css('background', '#d33f8d');
    $('#in-btn').css('background', '#ef93c3');
    
    $('#input-plcnm').css('visibility', 'hidden');
    $('#input-plc').css('visibility', 'hidden');
    $('#input-rcpt').css('visibility', 'hidden');
    
});

// 지출 화면으로 변환
$('#in-btn').on('click',() => {
    $(".hst").empty();
    $('.form-control').val('');
    $('#type').val('지출');
    console.log($('#type').val());
    
    $('#in-btn').css('background', '#d33f8d');
    $('#ex-btn').css('background', '#ef93c3');
    
    $('.input-group').css('visibility', 'visible');
});


$('#cancel-btn').on('click',() => {
    location.href = '../main/list.html';
});
