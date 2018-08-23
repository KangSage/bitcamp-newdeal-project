
printList();

function printList() {
    $.getJSON('', (result) => {
        console.log(result);
        if (result.status === 'fail') {
            location.href = '../login.html';
            return;
        }
    })
}
