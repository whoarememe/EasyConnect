import Vue from 'vue';

import management from './management.js';

import CSS from '../../sass/style.scss';

import a1 from "../../js/jquery.easing.1.3.js";
import a2 from "../../js/bootstrap.min.js";
import a3 from "../../js/jquery.waypoints.min.js";
import a4 from "../../js/hoverIntent.js";
import a5 from "../../js/superfish.js";
import a6 from "../../js/jquery.flexslider-min.js";
// import a7 from "../../js/jquery.stellar.min.js";
import a8 from "../../js/jquery.countTo.js";







var app = new Vue({
	el: '#app',
	data: {
		manufactorer: '',
		deviceTypeList: [{
			pic: null,
			typeId: null,
			typeName: "请选择设备类型"
		}],
		deviceType: null,
		deviceModel: null,
		deviceDescription: null,
		deviceFunction: null
	},
	methods: {
		addNewDevice: function () {
			$.ajax({
				url: '/EasyConnect/developer/addDeveloperDevice',
				method: 'GET',
				dataType: 'json',
				data: {
					typeId: app.deviceType,
					model: app.deviceModel,
					description: app.deviceDescription,
					function: app.deviceFunction,
					state: 1
				},
				success: function (data) {
					if (data.success == false) {
						app.$refs.messageBox.showBox('添加设备失败，请稍后再试。');
					}
					else {
						app.$refs.messageBox.showBox('添加成功！', function () {
							window.location = 'index.html';
						});
					}
				}
			})
		}
	}
});



//获取所有设备类型
app.$refs.loadingAnimation.showOrHide(true);
$.ajax({
	url: '/EasyConnect/developer/getAllDeviceType',
	method: 'GET',
	dataType: 'json',
	data: {},
	success: function (data) {
		app.deviceTypeList = data.data;
		app.$refs.loadingAnimation.showOrHide(false);
	}
});


