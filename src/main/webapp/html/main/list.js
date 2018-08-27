"use strict";

let exampleModalCenter = $('#exampleModalCenter');

exampleModalCenter.load('listModal.html');

var liTemplateSrc = $('#li-template').text();
var template = Handlebars.compile(liTemplateSrc);

$.post(`${serverApiAddr}/json/amount/list`, (result) => {

    if (result.status === 'fail') {
        swal('로그인 되지 않았습니다.',
            '로그인 페이지로 이동합니다.',
            'error'
        ).then(function() {
            location.href = '../login.html';
        });
    }

   console.log(result);
    var {list} = result;
    var list = result.list;
    console.log(list);
    for (let i=0; i < list.length; i++){
        let {day, amounts} = list[i];
        let headElement = $("<div></div>").addClass("list-group-item");
        headElement.append(day);

      for(let j = 0; j < amounts.length; j++) {
          var html = template(amounts[j]);
            headElement.append(html);
        }
        $('#listBody').append(headElement);
    }
});

exampleModalCenter.on('click', '#add-btn', () => {
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

exampleModalCenter.on('click', '#update-btn', () => {
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

exampleModalCenter.on('click', '#delete-btn', () => {
    console.log('삭제 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/delete`, {no:2})
        .done(function(data) {
            console.log(data)
            if (data.status == 'success') {
                swal('감사합니다!',
                    '삭제 되었습니다.',
                    'success'
                ).then(function() {
                    location.reload();
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
exampleModalCenter.on('click', '#in-btn', () => {
    console.log('in-btn');
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
exampleModalCenter.on('click', '#ex-btn', () => {
    console.log('ex-btn');
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

// moodal 창에 값을 넘긴다.
exampleModalCenter.on('show.bs.modal', function (e) {
    var no = $(e.relatedTarget).data('no'); // data-no의 값을 가져온다.
    $.getJSON(`${serverApiAddr}/json/amount/${no}`, (data) => {
        
    });
    $(e.currentTarget).find('#list-no').val(no); //
});

