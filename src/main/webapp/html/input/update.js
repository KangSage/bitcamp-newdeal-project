'use strict'

$('#update-btn').on('click',() => {
    
})


// 수입 화면으로 변환
$('#ex-btn').on('click',() => {
    $('.form-control').val('');
    $('#type').val('수입');
    console.log($('#type').val());
    
    $('#ex-btn').css('background', '#8bc1ef');
    $('#in-btn').css('background', '#efcfe2');
    
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
    
    $('#in-btn').css('background', '#8bc1ef');
    $('#ex-btn').css('background', '#efcfe2');
    
    $('.input-group').css('visibility', 'visible');
});


$('#cancel-btn').on('click',() => {
    location.href = '../main/list.html';
});
