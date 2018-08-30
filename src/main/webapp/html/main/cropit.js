/* 크로핏 테스트 */
$('#image-cropper').cropit({
    imageBackground:true,
    imageBackgroundBorderWidth: 15,
    allowDragNDrop:true,
    smallImage:"allow",
});

/*exampleModalCenter.on('click', '#cutting-btn', function (e) {
    console.log('사진 자르기 버튼 클릭');
    var imageData = $('#image-cropper').cropit('export', {
        type: 'image/jpeg',
        quality: 1,
        originalSize: true
    });

    $('#hidden-image-data').val(imageData);

    if ($('#hidden-image-data').val() != "") {
        $('fakeChkImageMsg').hide;
        chkImageItem.html('<p class="checkMessages" id="cutPhoto">사진 편집이 완료 되었습니다.</p>')
    }
});*/

/* 크로핏 테스트 함수 끝 */