"use strict";

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

