function addNum(num) {
	document.getElementById("schermpje").value += num;
	console.log(num);
}
let opper = "";
function addOpp(opp) {
	document.getElementById("schermpje").value += opp;
	opper = opp;

}

function calc() {
	som = document.getElementById("schermpje").value;
	console.log(som);
	str = som.toString();
	nums = str.split(opper);
	console.log(nums);
	result=0;
	if (opper == "*") 
		result = nums[0] * nums[1];
	else if (opper == "/")
		result = nums[0] / nums[1];
	else if (opper == "-")
		result = nums[0] - nums[1];
	else if (opper == "+")
		result = nums[0] + nums[1];
	document.getElementById("schermpje").value = result;
}

