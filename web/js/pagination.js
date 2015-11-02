/**
 * 
 */
function gotoPage(event, gotoPage, totalPage, form, currentPage){
	
	if(!/^\d*$/.test(gotoPage.val())){
		alert("您输入的页码有误，请重新输入");
		Event.stop(event);
		return false;
	}
	
	if(parseFloat(gotoPage.val()) > parseFloat(totalPage)){
		alert("您输入的页码太大，请重新输入");
		gotoPage.val(totalPage);
		Event.stop(event);
		return false;
	}else if(gotoPage.val() < 1){
		gotoPage.val(1);
	}
	
	currentPage.val(gotoPage.val());
	form.submit();
	
}
