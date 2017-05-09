var imagecount = 1;
var total =4;
function slide(x) {
	var Image =document.getElementsByClassName('img');
	imagecount = imagecount =x;
	Image.src ="Images/img"+ imagecount +"jpg";
}