'use strict'


var bsDiv = $('#bs-div');

var monthOperator = 0;

var selectDate = null;

requestList(monthOperator);

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
    $.get('http://localhost:8080/json/budget/list',
            { 'monthOperator' : monthOperator },
            function(result){
                
                $('#this-month').html(result.selectDate);
                selectDate = result.selectDate;
                console.log(selectDate);
                let {status, list} = result; 
                if (result.status === 'success'){
                    
                    bsDiv.load('budgetSettingAfter.html');
                    loadList(list);
                } else {
                    
                    bsDiv.load('budgetSettingBefore.html');
                }
                
                
            }
    )
};

//금액에 1000원 단위로 콤마를 넣어주는 함수.
function numberWithCommas(amount) {
    if (amount === undefined || amount === null)
        return 0;
    return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


$('#bs-div').on('click', '#budget-btn', (no)=>{
    $.post('http://localhost:8080/json/budget/add',{
        amount: $('#budget-input').html(),
//        month: $('#this-month').html()
        month: selectDate
        
    },
    function(data){
        var {status, list} = data;
        
        if (status === 'login-fail') {
           swal('로그인 되지 않았습니다.',
                '로그인 페이지로 이동합니다.',
                'error'
            )/*.then(function() {
                location.href = '../login.html';
            });*/
            
        }
        
        bsDiv.load('budgetSettingAfter.html');
        requestList(monthOperator);
    })
});

function loadList(list) {
    $.getJSON(`http://localhost:8080/json/budget/${list[0].budgetNo}`,
            (result) => {
                console.log(result)
                $('#b-budget').html(result.data.amount);
                $('#b-withdraw').html(result.data.withdraw);
                $('#budget-withdraw').html(result.restMoney);
    })
}











