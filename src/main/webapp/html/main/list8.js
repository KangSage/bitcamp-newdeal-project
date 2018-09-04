"use strict";

let exampleModalCenter = $('#exampleModalCenter');

exampleModalCenter.load('listModal8.html');

var liTemplateSrc = $('#li-template').text();
var template = Handlebars.compile(liTemplateSrc);

const getYymm = (date, number) => {

    if (number === undefined) {
        number = 1;
    }

    const yyyy = date.getFullYear();
    const mm = date.getMonth() < 9 ? `0${date.getMonth() + number}` :
        (date.getMonth() + number);
    return `${yyyy}-${mm}`
};

let month = getYymm(new Date());
let monthOperator = 0;
console.log(month);

$(document).ready(
    requestList(monthOperator)
);

$('#this-month').html(month);

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
            }

            console.log(result);
            var {list, selectDate} = result;
            /*var list = result.list;*/
            console.log(list);
            console.log(selectDate);
            $('#this-month').html(selectDate);
            for (let i=0; i < list.length; i++){
                let {day, amounts} = list[i];
                let headElement = $("<div></div>").addClass("list-group-item");
                headElement.append(`<div id="day">${day}</div>`);

                for(let j = 0; j < amounts.length; j++) {
                    var html = template(amounts[j]);
                    headElement.append(html);
                }
                $('#listBody').append(headElement);
            }
        });
}



exampleModalCenter.on('click', '#add-btn', () => {
    console.log('추가 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/add`,
        {
            'amountType': $('#amount-type').val(),
            'history': $('#history').val(),
            'amount': $('#amount').val(),
            'category': $('#category').val(),
            'memo': $('#memo').val(),
            'happenDate': $('#happen-date').val()
        },
        function(data) {
            console.log(data);
            $('#listBody').html('');
            requestList(monthOperator);
            $('#list-modal-close-btn').trigger('click');
        },
        'json');
});

let listNo = null;

exampleModalCenter.on('click', '#update-btn', () => {
    console.log('완료 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/update`, {
        'no': listNo,
        'amountType': $('#amount-type').val(),
        'history': $('#history').val(),
        'amount': $('#amount').val(),
        'category': $('#category').val(),
        'memo': $('#memo').val(),
        'happenDate': $('#happen-date').val()
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
})

exampleModalCenter.on('click', '#delete-btn', () => {
    console.log('삭제 버튼 클릭');
    $.post(`${serverApiAddr}/json/amount/delete`, {no:listNo})
        .done(function(data) {
            console.log(data)
            if (data.status == 'success') {
                swal('감사합니다!',
                    '삭제 되었습니다.',
                    'success'
                ).then(function() {
                    $('#listBody').html('');
                    requestList(monthOperator);
                    $('#list-modal-close-btn').trigger('click');
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
    $('#amount-type').val('수입');
    $('#category').val('Choose...');
    console.log($('#amount-type').val());

    $('#ex-btn').css('background', '#e1c5ec');
    $('#in-btn').css('background', '#d33f8d');

    $('#expenditure-input').css('display', 'none');

    $('.ex-category').hide();
    $('.in-category').show();

});

// 지출 화면으로 변환
exampleModalCenter.on('click', '#ex-btn', () => {
    console.log('ex-btn');
    $(".hst").empty();
    $('#amount-type').val('지출');
    $('#category').val('Choose...');
    console.log($('#amount-type').val());

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

    if (typeof no === 'number') {

        $('.new-ctrl').hide();
        $('.view-ctrl').show();
        $.getJSON(`${serverApiAddr}/json/amount/${no}`, (result) => {
            let {data, status} = result;
            if (data.amountType === '수입') {
                $('#in-btn').trigger('click')
            }
            currentTarget.find('#list-no').val(data.no);
            currentTarget.find('#history').val(data.history);
            currentTarget.find('#amount').val(data.amount);
            currentTarget.find('#category').val(data.category);
            currentTarget.find('#memo').val(data.memo);
            currentTarget.find('#happen-date').val(data.happenDate);
        });

    } else {
        $('.view-ctrl').hide();
        $('.new-ctrl').show();
    }
});






/*var imgBox = $('#imgBox');

exampleModalCenter.on('click', '#receipt', function() {
    url: `${serverApiAddr}/json/amount/upload01`,        // 서버에 요청할 URL
    dataType: 'json',         // 서버가 보낸 응답이 JSON임을 지정하기
    sequentialUploads: true,  // 여러 개의 파일을 업로드 할 때 순서대로 요청하기.
    singleFileUploads: false, // 한 요청에 여러 개의 파일을 전송시키기.
    autoUpload: false,        // 파일을 추가할 때 자동 업로딩 하지 않도록 설정.
    disableImageResize: /Android(?!.*Chrome)|Opera/
          .test(window.navigator && navigator.userAgent), // 안드로이드와 오페라 브라우저는 크기 조정 비활성 시키기
    previewMaxWidth: 100,   // 미리보기 이미지 너비
    previewMaxHeight: 100,  // 미리보기 이미지 높이 
    previewCrop: true,      // 미리보기 이미지를 출력할 때 원본에서 지정된 크기로 자르기
    processalways: function(e, data) {
        console.log('fileuploadprocessalways()...');
        console.log(data.files);
        var imagesDiv = $('#images-div');
        imagesDiv.html("");
        for (var i = 0; i < data.files.length; i++) {
          try {
            if (data.files[i].preview.toDataURL) {
              $("<img>")
                .attr('src', data.files[i].preview.toDataURL())
                .css('height', '80px')
                .appendTo(imagesDiv);
            }
          } catch (err) {}
        }
        $('#upload-btn').unbind("click");
        $('#upload-btn').click(function() {
            data.submit();
        });
    }, 
    submit: function (e, data) { // 서버에 전송하기 직전에 호출된다.
      console.log('submit()...');
      data.formData = {
          name: $('#name').val(),
          age: $('#age').val()
      };
    }, 
    done: function (e, data) { // 서버에서 응답이 오면 호출된다. 각 파일 별로 호출된다.
      console.log('done()...');
      console.log(data.result);
      $('<p/>').text("name : " + data.result.name).appendTo(document.body);
      $('<p/>').text("age : " + data.result.age).appendTo(document.body);
      $.each(data.result.filenames, function(index, filename) {
        $('<p/>').text(filename).appendTo(document.body);
      });
    }
  });

*/
/*exampleModalCenter.on('click', '#image-cropper', () => {*/

$('#image-cropper').cropit();

 $('.download-btn').click(function() {
   var imageData = $('#image-cropper').cropit('export');
   window.open(imageData);
 });


