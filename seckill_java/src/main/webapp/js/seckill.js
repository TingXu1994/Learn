var seckill = {
	URL : {
		basePath : function() {
			return $('#basePath').val();
		},
		now : function() {
			return seckill.URL.basePath() + 'time/now';
		},
		exposer : function(seckillId) {
			return seckill.URL.basePath() + seckillId + '/exposer';
		},
		execution : function(seckillId, md5String) {
			return seckill.URL.basePath() + seckillId + '/excution/'
					+ md5String;
		}
	},

	handleSeckill : function(seckillId, seckillBox) {
		seckillBox
				.html('<button class="btn btn-primary btn-lg" id="SeckillBtn">开始秒杀</button>');
		var seckillURL = seckill.URL.exposer(seckillId);
		$
				.post(
						seckillURL,
						{},
						function(result) {
							if (result && result['flag']) {
								var exposer = result['data'];
								// TODO:???????怎么还把我的变量名字给变了？？？isExpose变成了expose？哪里来的，难道是之前的变量名
								// console.log(exposer);
								if (exposer['expose']) {
									// console.log("isExpose");
									var md5String = exposer['md5String'];
									var seckillURL = seckill.URL.execution(
											seckillId, md5String);
									// =======================================
									// TODO:采用one是为了减少用户多次点击向服务器发送过多请求？
									// =======================================
									$('#SeckillBtn')
											.one(
													'click',
													function() {
														$(this).addClass(
																'disabled');
														$
																.post(
																		seckillURL,
																		{},
																		function(
																				result) {
																			console.log(result);
																			console.log(result['flag']);
																			if (result
																					&& result['flag']) {
																				var seckillResult = result['data'];
																				var stateID = seckillResult['stateID'];
																				var stateInfo = seckillResult['stateInfo'];
																				console.log(result['data']);
																				seckillBox
																						.html('<span class="label label-success">'
																								+ stateInfo
																								+ '</span>');
																			}
																		});
													});
									seckillBox.show();
								} else {
									// 秒杀未开启
									// TODO
									console.log("exposer:" + exposer);
								}
							} else {
								// console.log("result:" + result);
								alert("非法路径！");
								// window.location.href = seckill.URL.basePath +
								// 'list.jsp';
							}
						});
	},

	// 这里简单验证一下，大型的可以用JAVA的验证模块。
	validatePhone : function(userPhone) {
		if (userPhone && userPhone.length == 11 && !isNaN(userPhone))
			return true;
		else
			return false;
	},

	// 只判断时间，属于时间范围内则跳到handleSeckill处理
	countDown : function(seckillId, startTime, endTime, nowTime) {
		var seckillBox = $('#seckillCountDownBox');
		if (nowTime > endTime) {
			seckillBox.html('秒杀结束！');
		} else if (nowTime < startTime) {
			// 1000ms表示1s
			var killTime = new Date(startTime + 1000);
			seckillBox.countdown(killTime, function(event) {
				var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
				seckillBox.html(format);
			}).on('finish.countDown', function() {
				// 倒计时结束后，获取秒杀地址，处理秒杀
				seckill.handleSeckill(seckillId, seckillBox);
			});
		} else {
			seckill.handleSeckill(seckillId, seckillBox);
		}
	},

	detail : {
		init : function(params) {
			var userPhone = $.cookie('userPhone');
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];

			// 验证手机号 -- 这里的功能是一定要登陆完才能秒杀
			if (!seckill.validatePhone(userPhone)) {
				BootstrapDialog
						.show({
							title : '未登录！',
							message : '手机号: <input type="text" name="userPhone" id="userPhone" placeholder="请输入您的手机号..." class="form-control">',
							closable : false,
							buttons : [ {
								label : '提交',
								action : function(dialog) {
									var inputPhone = $('#userPhone').val();

									// console.log('inputPhone'+inputPhone);
									if (seckill.validatePhone(inputPhone)) {
										// 将输入手机号存入Cookie中
										$.cookie('userPhone', inputPhone, {
											expires : 7,
											path : '/'
										});
										window.location.reload();
									} else {
										alert("请输入正确的11位数字手机号！");
									}
								}
							} ]
						});
			}

			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(), {}, function(result) {
				if (result && result['flag']) {
					var nowTime = result['data'];
					seckill.countDown(seckillId, startTime, endTime, nowTime);
				} else {
					// ======================
					console.log("result:" + result);
				}
			});
		}
	}

}