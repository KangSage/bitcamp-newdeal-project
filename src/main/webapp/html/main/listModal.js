var imageData = null;
var currentPicture = null;

cropit(currentPicture);

function cropit() {
    $('#image-cropper').cropit({
        imageBackground:true,
        imageBackgroundBorderWidth: 15,
        allowDragNDrop:true,
        smallImage:"allow"
    });
}

exampleModalCenter.on('click', '#cutting-btn', function (e) {
    console.log('사진 자르기 버튼 클릭');
    imageData = $('#image-cropper').cropit('export', {
        type: 'image/jpeg',
        quality: 1,
        originalSize: true
    });

    if ($('#hidden-image-data').val() !== "") {
        $('#chkImageMsg').html('<p class="checkMessages" id="cutPhoto">사진 편집이 완료 되었습니다.</p>')
    }
});