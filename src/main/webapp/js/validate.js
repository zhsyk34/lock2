(function ($) {
	$.validate = {
		isEmpty: function (str) {
			return str === null || str === undefined || ($.trim(str)).length == 0;
		},
		equals: function (str1, str2) {// 比较字符串
			return str1 == str2;
		},
		equalsIgnoreCase: function (str1, str2) {// 忽略大小写比较字符串
			return (str1 ? str1.toLowerCase() : str1) == (str2 ? str2.toLowerCase() : str2);
		},
		isNumber: function (number) {
			return number != null && !isNaN(number);
		},
		isInteger: function (input) {
			return /^[+-]?\d+$/.test(input);
		},
		isNatural: function (input) {// 自然数
			return /^[+]?[1-9]\d*$/.test(input);
		},
		isPositive: function (input, strict) {// 正数
			return !isNaN(input) && (strict ? input > 0 : input >= 0);
		},
		isCurrency: function (input) {// 货币
			return /^\d+(\.\d{1,2})?$/.test(input);
		},
		validateStrLength: function (value, minLength, maxLength) {
			return value.length >= minLength && value.length <= maxLength;
		},
		validateStrMaxLength: function (value, maxLength) {
			return value.length <= maxLength;
		}
	};
})(jQuery);