'use strict';

var bsDiv = $('#bs-div');

var monthOperator = 0;

var selectDate = null;

var setBudget = 0,
    setBudgetNo =0;

var aver = 0,
totalGuess = 0;

$(document).ready(() =>{
    requestList(monthOperator);
    
});


$('#select-month').on('click' , (e) =>{
    if($(e.target).attr('id') === 'last-month-btn'){
        console.log('이전 달로 이동');
        monthOperator--;
        console.log(monthOperator);
        $('#bs-div').html('');
        requestList(monthOperator);
    }else if($(e.target).attr('id') === 'next-month-btn' ){
        console.log('다음 달로 이동');
        monthOperator++;
        console.log(monthOperator);
        $('#bs-div').html('');
        requestList(monthOperator);
    }
    
})



function requestList(monthOperator) {
    $.get(`${serverApiAddr}/json/budget/list`,
            { 'monthOperator' : monthOperator },
            
            function(result){
                console.log(result)
                if (result.status === 'login-fail') {
                    swal('로그인 되지 않았습니다.',
                         '로그인 페이지로 이동합니다.',
                         'error'
                     ).then(function() { location.href = '../login.html'; });
                 }
                $('#this-month').html(result.selectDate);
                selectDate = result.selectDate;
                console.log(selectDate);
                let {status, budget} = result;
                if (result.status === 'success'){
                    bsDiv.load('budgetSettingAfter.html');
                    loadList(result.selectDate, monthOperator);
                }
            }
    )
};

// 금액에 1000원 단위로 콤마를 넣어주는 함수.
function numberWithCommas(amount) {
    if (amount === undefined || amount === null)
        return 0;
    return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


bsDiv.on('click', '#budget-btn', (no)=>{
    $.post(`${serverApiAddr}/json/budget/add`,{
        amount: $('#budget-input').val(),
//        month: $('#this-month').html()
        month: selectDate
        
    },
    function(data){
        var {status, list} = data;
        
        if (status === 'login-fail') {
           swal('로그인 되지 않았습니다.',
                '로그인 페이지로 이동합니다.',
                'error'
            )/*
                 * .then(function() { location.href = '../login.html'; });
                 */
            
        }
        
        bsDiv.load('budgetSettingAfter.html');
        requestList(monthOperator);
    })
});

function loadList(month, monthOperator) {
    $.getJSON(`${serverApiAddr}/json/budget/list/`,
            { monthOperator : monthOperator},
            (result) => {
                if (result.budget !== null) {
                    let data = result.budget;
                    let {amount, withdraw, restMoney, percent} = data;
                    console.log("budgetNo",result.budget.budgetNo);
                    $('#b-budget').html(numberWithCommas(amount));
                    $('#b-withdraw').html(numberWithCommas(withdraw));
                    $('#budget-withdraw').html(numberWithCommas(result.restMoney));
                    var s1 = result.percent
                    console.log(s1)
                    
                    $('#demo').jQMeter({
                         goal: "100",
                         raised:`${s1}`,
                          width: "100%",
                          height: "50px",
                          bgColor: "#444",
                          barColor: "#d43f8d",
                          orientation: "horizontal",
                          counterSpeed: 2000,
                          animationSpeed: 2000,
                          displayTotal: true
                    });
                    
                    setBudget = amount;
                    setBudgetNo = data.budgetNo;
                } else {
                    bsDiv.load('budgetSettingBefore.html');
                }

    })
}


//예산 설정 화면
bsDiv.on('click','#budget-setting' , (result)=>{
    bsDiv.load('budgetSetting.html');
    console.log('=>', setBudget);
    $('#set-budget-input').val(setBudget);
})

//삭제
bsDiv.on('click','#budget-delete',()=>{
    $('#set-budgetNo-input').val(setBudgetNo);
    console.log('setBudgetNo',setBudgetNo);
    $.getJSON(`${serverApiAddr}/json/budget/delete`,{
        'no': setBudgetNo
    },(result)=>{
        bsDiv.load('budgetSettingBefore.html');
        swal({
            type: 'success',
            title: '삭제하였습니다.',
            showConfirmButton: false,
            timer: 1500
          })
    })
});

//수정
bsDiv.on('click','#budget-update',()=>{
    $('#set-budgetNo-input').val(setBudgetNo);
    $.post(`${serverApiAddr}/json/budget/update`,{
        amount: $('#budget-input').val(),
        month: selectDate
    },(result)=>{
        console.log(result);
        if(result.status !== 'success') return;
        bsDiv.load('budgetSettingAfter.html');
        requestList(monthOperator);
    },'json')
});





