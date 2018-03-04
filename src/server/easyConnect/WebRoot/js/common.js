//提示框
$('.hintBoxWrapper').hide();
function showAndHideMessageBox (ifShow, message, callback) {
	if (callback) {
		showAndHideMessageBox.callback = callback;
	}
	if (ifShow) {
		$('#messageBoxContent').text(message);
		$('.hintBoxWrapper').show();
	}
	else {
		$('.hintBoxWrapper').hide();
		showAndHideMessageBox.callback();
	}
}
$('#messageBoxButton').click(function () {
	showAndHideMessageBox(false);
});

function hint (message, callback) {
	showAndHideMessageBox(true, message, callback);
	return false;
}

//加载等待动画
function showAndHideLoadingAnimation (ifShow) {
	if (ifShow) {
		$('.loadAnimationWrapper').show();
	}
	else {
		$('.loadAnimationWrapper').hide();
	}
}