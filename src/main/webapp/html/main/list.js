"use strict";
var currentPicture = null;
let exampleModalCenter = $('#exampleModalCenter');

// exampleModalCenter.load('listModal.html');

loadListModal();

// 모달 창에 띄울 HTML 파일을 불러오는 함수.
function loadListModal(no) {
    if (typeof no === 'number') {
        console.log('listModal.js if');
        exampleModalCenter.load('viewListModal.html');
    } else {
        console.log('listModal.js else');
        exampleModalCenter.load('newListModal.html');
    }
}

// Handlebars로 만들어 낼 템플릿 준비
var liTemplateSrc = $('#li-template').text();
var template = Handlebars.compile(liTemplateSrc);

// 오늘 날짜를 날짜를 YYYY-mm 형식으로 잘라주는 함수.
const getYymm = (date, number) => {
    if (number === undefined) {
        number = 1;
    }
    const yyyy = date.getFullYear();
    const mm = date.getMonth() < 9 ? `0${date.getMonth() + number}` : (date.getMonth() + number);
    return `${yyyy}-${mm}`
};

// 위의 함수를 이용해 이번 달을 YYYY-mm 형식으로 만든다.
let month = getYymm(new Date());

$('#this-month').html(month);

// 1달 단위로 리스트를 변경할 때 서버에 넘겨 줄 숫자
let monthOperator = 0;

// DOM이 생성 되면 실행할 함수.
$(document).ready(
    requestList(monthOperator)
);

// 좌우 버튼으로 월을 변경할 이벤트 리스너
$('#select-month').on('click', (e) => {
    if ($(e.target).attr('id') === 'last-month-btn') {
        console.log('이전 달로 이동');
        monthOperator--;
        $('#listBody').html('');
        requestList(monthOperator);

    } else if ($(e.target).attr('id') === 'next-month-btn') {
        console.log('다음 달로 이동');
        monthOperator++;
        $('#listBody').html('');
        requestList(monthOperator);
    }
});

// 금액에 1000원 단위로 콤마를 넣어주는 함수.
function numberWithCommas(amount) {
    if (amount === undefined || amount === null)
        return 0;
    return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 해당 월의 리스트를 생성할 함수
function requestList(monthOperator) {
    $.post(`${serverApiAddr}/json/amount/list`,
        {
            'monthOperator' : monthOperator
        },
        (result) => {
            if (result.status === 'login-fail') {
                swal('로그인 되지 않았습니다.',
                    '로그인 페이지로 이동합니다.',
                    'error'
                ).then(function() {
                    location.href = '../login.html';
                });
            } else if (result.list.length === 0) {
                let headElement = $("<div></div>").addClass("list-group-item");
                    headElement.append(`<div style="font-size: 120%; text-align: center;">해당 월의 등록된 내역이 없습니다.</div>`);
                $('#this-month').html(result.selectDate);
                $('#total-income-amount').html('원');
                $('#total-budget-amount').html('원');
                $('#monthly-total-amount').html('원');
                $('#listBody').append(headElement);
            } else {
                console.log(result);
                let {list, selectDate, totalIncomeAmount, totalBudgetAmount, monthlyTotalAmount} = result;

                $('#this-month').html(selectDate);
                $('#total-income-amount').html(numberWithCommas(totalIncomeAmount) + '원');
                $('#total-budget-amount').html(numberWithCommas(totalBudgetAmount) + '원');
                $('#monthly-total-amount').html(numberWithCommas(monthlyTotalAmount) + '원');

                for (let i = 0; i < list.length; i++) {
                    let {day, amounts} = list[i];
                    let headElement = $("<div></div>").addClass("list-group-item");
                    headElement.append(`<div class="day">${day}</div>`);

                    for (let j = 0; j < amounts.length; j++) {
                        var html = template(amounts[j]);
                        headElement.append(html);
                    }
                    $('#listBody').append(headElement);
                }
            }
        });
}

// 모달 창에서 새로운 내용을 추가할 '저장' 버튼의 이벤트 리스너
exampleModalCenter.on('click', '#add-btn', () => {
    $('#cutting-btn').trigger('click');

    if (imageData === undefined) {
        imageData = null;
    }

    console.log('추가 버튼 클릭');
    console.log('imageData => ', imageData);

    setTimeout(() => {
        $.post(`${serverApiAddr}/json/amount/add`,
            {
                'amountType': $('#amount-type').val(),
                'history': $('#history').val(),
                'amount': $('#amount').val(),
                'category': $('#category').val(),
                'memo': $('#memo').val(),
                'happenDate': $('#happen-date').val(),
                'base64Image': imageData
            },
            function(data) {
                $('#listBody').html('');
                requestList(monthOperator);
                $('#list-modal-close-btn').trigger('click');
                loadListModal();
            },
            'json');
    }, 500);


});

// 상세 뷰에 필요한 리스트의 번호를 넣어줄 변수
let listNo = null;

// 수정 버튼의 이벤트 리스너
exampleModalCenter.on('click', '#update-btn', () => {
    $('#cutting-btn').trigger('click');

    if (imageData === undefined) {
        imageData = null;
    }

    console.log('추가 버튼 클릭');
    console.log('imageData => ', imageData);
    setTimeout(() => {
        $.post(`${serverApiAddr}/json/amount/update`, {
            'no': listNo,
            'amountType': $('#amount-type').val(),
            'history': $('#history').val(),
            'amount': $('#amount').val(),
            'category': $('#category').val(),
            'memo': $('#memo').val(),
            'happenDate': $('#happen-date').val(),
            'base64Image': imageData
        }, (result) => {
            if (result.status === 'success') {
                swal('감사합니다!',
                    '변경 되었습니다.',
                    'success'
                ).then(function() {
                    $('#listBody').html('');
                    requestList(monthOperator);
                    $('#list-modal-close-btn').trigger('click');
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
    }, 500);


});

// 삭제
exampleModalCenter.on('click', '#delete-btn', () => {
    console.log('삭제 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/delete`, {no:listNo})
        .done(function(data) {
            console.log(data);
            if (data.status === 'success') {
                swal('감사합니다!',
                    '삭제 되었습니다.',
                    'success'
                ).then(function() {
                    $('#listBody').html('');
                    requestList(monthOperator);
                    $('#list-modal-close-btn').trigger('click');
                    $('#imageView').css('background', '');
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

    $('#amount-type').val('수입');
    $('#category').val('Choose...');

    $('#ex-btn').css('background', '#e1c5ec');
    $('#in-btn').css('background', '#d33f8d');

    $('#expenditure-input').css('display', 'none');

    $('.ex-category').hide();
    $('.in-category').show();

});

// 지출 화면으로 변환
exampleModalCenter.on('click', '#ex-btn', () => {

    $(".hst").empty();
    $('#amount-type').val('지출');
    $('#category').val('Choose...');

    $('#in-btn').css('background', '#e1c5ec');
    $('#ex-btn').css('background', '#d33f8d');

    $('#expenditure-input').css('display', '');
    $('.input-group').show();

    $('.in-category').hide();
    $('.ex-category').show();

});

// moodal 창에 값을 넘긴다.
exampleModalCenter.on('show.bs.modal', function (e) {

    $('#ex-btn').trigger('click');
    let currentTarget = $(e.currentTarget);
    let no = $(e.relatedTarget).data('no');
    listNo = no;
    currentTarget.find('.form-control').val('');
    loadListModal(no);

    if (typeof no === 'number') {
        console.log(no);
        $('.new-ctrl').hide();
        $('.view-ctrl').show();
        $.getJSON(`${serverApiAddr}/json/amount/${no}`, (result) => {
            let {data, status} = result;
            console.log('새로 넘어 온 데이터 =>', data);
            if (data.amountType === '수입') {
                $('#in-btn').trigger('click')
            }
            currentTarget.find('#list-no').val(data.no);
            currentTarget.find('#history').val(data.history);
            currentTarget.find('#amount').val(data.amount);
            currentTarget.find('#category').val(data.category);
            currentTarget.find('#memo').val(data.memo);
            currentTarget.find('#happen-date').val(data.happenDate);
            currentPicture = data.receiptFile;

            if (currentPicture) {
                $('#imageView').css("background", `url("../../download/${currentPicture}")`);
            }
        });

    } else {
        $('#imageView').css("background", "white");
    }
});




