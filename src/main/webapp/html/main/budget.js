'use strict'


var bsDiv = $('#bs-div');

var monthOperator = 0;

var selectDate = null;

requestList(monthOperator);

//
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
                $('#this-month').html(result.selectDate);
                selectDate = result.selectDate;
                console.log(selectDate);
                let {status, budget} = result;
                if (result.status === 'success'){
                    bsDiv.load('budgetSettingAfter.html');
                    loadList(budget);
                } else {
                    bsDiv.load('budgetSettingBefore.html');
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


$('#bs-div').on('click', '#budget-btn', (no)=>{
    $.post('http://localhost:8080/json/budget/add',{
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

function loadList(budget) {
    $.getJSON(`${serverApiAddr}/json/budget/${budget.budgetNo}`,
            (result) => {
                console.log(result);
                let data = result.data;
                let { amount , withdraw , restMoney , percent} = data;
                $('#b-budget').html(numberWithCommas(amount));
                $('#b-withdraw').html(numberWithCommas(withdraw));
                $('#budget-withdraw').html(numberWithCommas(result.restMoney));
                $('#statistics').html(result.percent);
                console.log(result.restMoney);
                var sss= result.restMoney
              //예산 설정 화면
              $('#bs-div').on('click','#budget-setting' , (result)=>{
                  console.log("aa")
                  console.log("예산 설정 화면으로 가기");
                  console.log(sss);
                  console.log(sss);
                  bsDiv.load('budgetSetting.html');
                  //loadList(result.budget);
              })
        



    })
}







