
$.getJSON(`${serverApiAddr}/json/amount/list`, (result) => {
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
        let headElement = $("<li></li>").addClass("list-group-item list-group-item-action");
        headElement.append($("<span></span>").html(day)).append('<br>');
       for(let j = 0; j < amounts.length; j++){
           let itemNo = $("<span></span>").html(amounts[j].no);
           let itemType = $("<span></span>").attr('data-no');
            headElement.append(itemType, itemNo, "세상에나 마상에나");
        }
        $('#list').append(headElement);
    }
});

