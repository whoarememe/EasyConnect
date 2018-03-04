var manufactorLock = true;//不允许提交

//获取厂商列表
showAndHideLoadingAnimation(true);
$.ajax({
	data: {},
	url: "/EasyConnect/developer/getManufacturer",
	dataType: "json",
	method: 'get',
	success:  function(data){
		if (data.success == true) {
			var options = '';
			for(var i = 0; i < data.data.length; i++) {
				options += '<option value='+ data.data[i].id +'>' + data.data[i].name + '</option>';
			}
			$('#manufactor').html(options);
			manufactorLock = false;
			showAndHideLoadingAnimation(false);
		}
		else {
			showAndHideMessageBox(true, '厂商列表加载失败，请稍候再试。');
		}
	}
});

$('#submitbutton').click(function (event) {
	event.preventDefault();
	var phone    = $('#phone').val(),
		password = $('#password').val();
		passwordConfirm = $('#passwordconfirm').val();
		name = $('#name').val();
		manufactor = $('#manufactor').val();

	if (phone.length != 11) {
		return hint('手机号格式有误');
	}
	if (password == '') {
		return hint('请输入密码');
	}

	if (password !== passwordConfirm) {
		return hint('两次密码输入不一致');
	}

	if (manufactorLock) {
		return hint('还未加载完厂商列表，请稍后。');
		return;
	}

	if (manufactor == null || manufactor == '') {
		return hint('请选择厂商。');
	}

	$.ajax({
		data:{
			phone: phone,
			password: password,
			manufacturerId: manufactor,
			name: name
		},
		url: "/EasyConnect/developer/developerRegister",
		dataType: "json",
		method: 'get',
		success:  function(data){
			console.log(JSON.stringify(data));
			if (data.success == true) {
				hint('注册成功', function () {
					window.location = './signin.html';
				});
			}
			else {
				hint('注册不成功，手机号已存在');
			}
		}
	});
});
