function isAllNumber(input)	{
	var isAllNumber = /^[\d]+$/;
	if(isAllNumber.test(input)){
		return true;
	}
	return false;
}