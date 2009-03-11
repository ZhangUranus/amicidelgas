/**
 * Creato da Valerio.ops 
 **/
var current_infolevel = 0;
var current_docslevel = 0;
var current_groupslevel = 0;
var current_walllevel = 0;
var current_dockplus = 0;
var current_eurolevel = 0;
var current_calendarlevel = 0; 
var current_area = 0;


function TabDispaly(obj, cond) {
	
	Ext.get(obj).setDisplayed(cond);
}

function displayNone(obj) {
	
	document.getElementById(obj).style.display = 'none';
}

function changeImageB(div, url) {
  	div.style.backgroundImage = "url('"+url+"')";
}

function sizescreen()
{
		if(screen.availWidth < 1100){
        document.getElementById('body_inter').width= "970px";
       } else if (screen.availWidth < 1300) {
        document.getElementById('body_inter').width= "1170px";
       } else if (screen.availWidth < 1400) {
        document.getElementById('body_inter').width= "1300px";
       } else {
        document.getElementById('body_inter').width= "90%";
       }
}

function ricorsivo()
{
	   if(screen.availWidth < 500 || screen.width < 500){
	   		var p = document.getElementsByTagName('img');
			for (var i=0; i<p.length; i++){
				p[i].style.maxWidth="30%";
				p[i].style.maxHeight="30%";
				//p[i].style.width="20%";
				//p[i].style.height="20%";
			}
       }
}

function private_area()
{
	
	if (current_area == 0) {
		document.getElementById('login_table').style.display = '';
		current_area = 1;
	} else {
		document.getElementById('login_table').style.display = 'none';
		current_area = 0;
	}
}


function infolevel()
{
	
	if (current_infolevel == 0) {
		document.getElementById('table_show_info').style.display = '';
		current_infolevel = 1;
	} else {
		document.getElementById('table_show_info').style.display = 'none';
		current_infolevel = 0;
	}
}

function show_infolevel(cond)
{
	
	if (current_infolevel == 1) {
		
		if(cond){
			document.getElementById('plus_img_infolevel').src = "/SeamAmiciDelGas/img/plus_off.png";
		} else {
			document.getElementById('plus_img_infolevel').src = "/SeamAmiciDelGas/img/plus.png";
		}
	} else {
		if(cond){
			document.getElementById('plus_img_infolevel').src = "/SeamAmiciDelGas/img/minus_off.png";
		} else {		
			document.getElementById('plus_img_infolevel').src = "/SeamAmiciDelGas/img/minus.png";
		}
	}
}

function docs()
{
	
	if (current_docslevel == 0) {
		document.getElementById('idi_133760042').style.display = '';
		document.getElementById('plus_img_docs').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		current_docslevel = 1;
	} else {
		document.getElementById('idi_133760042').style.display = 'none';
		document.getElementById('plus_img_docs').src = "/SeamAmiciDelGas/img/minus_off.jpg";	
		current_docslevel = 0;
	}
}

function show_docs(cond)
{
	
	if (current_docslevel == 1) {
		
		if(cond){
			document.getElementById('plus_img_docs').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		} else {
			document.getElementById('plus_img_docs').src = "/SeamAmiciDelGas/img/plus.jpg";
		}
	} else {
		if(cond){
			document.getElementById('plus_img_docs').src = "/SeamAmiciDelGas/img/minus_off.jpg";
		} else {		
			document.getElementById('plus_img_docs').src = "/SeamAmiciDelGas/img/minus.jpg";
		}
	}
}

function euro()
{
	
	if (current_eurolevel == 0) {
		document.getElementById('euro').style.display = '';
		document.getElementById('plus_img_euro').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		current_eurolevel = 1;
	} else {
		document.getElementById('euro').style.display = 'none';
		document.getElementById('plus_img_euro').src = "/SeamAmiciDelGas/img/minus_off.jpg";	
		current_eurolevel = 0;
	}
}

function show_euro(cond)
{
	
	if (current_eurolevel == 1) {
		
		if(cond){
			document.getElementById('plus_img_euro').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		} else {
			document.getElementById('plus_img_euro').src = "/SeamAmiciDelGas/img/plus.jpg";
		}
	} else {
		if(cond){
			document.getElementById('plus_img_euro').src = "/SeamAmiciDelGas/img/minus_off.jpg";
		} else {		
			document.getElementById('plus_img_euro').src = "/SeamAmiciDelGas/img/minus.jpg";
		}
	}
}

function wall()
{
	
	if (current_walllevel == 0) {
		document.getElementById('wallpaper').style.display = '';
		document.getElementById('plus_img_wall').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		current_walllevel = 1;
	} else {
		document.getElementById('wallpaper').style.display = 'none';
		document.getElementById('plus_img_wall').src = "/SeamAmiciDelGas/img/minus_off.jpg";	
		current_walllevel = 0;
	}
}

function show_wall(cond)
{
	
	if (current_walllevel == 1) {
		
		if(cond){
			document.getElementById('plus_img_wall').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		} else {
			document.getElementById('plus_img_wall').src = "/SeamAmiciDelGas/img/plus.jpg";
		}
	} else {
		if(cond){
			document.getElementById('plus_img_wall').src = "/SeamAmiciDelGas/img/minus_off.jpg";
		} else {		
			document.getElementById('plus_img_wall').src = "/SeamAmiciDelGas/img/minus.jpg";
		}
	}
}

function dockplus()
{
	
	if (current_dockplus == 0) {
		document.getElementById('dock_plus').style.display = '';
		document.getElementById('dock_img').src = "/SeamAmiciDelGas/img/down.png";
		current_dockplus = 1;
	} else {
		document.getElementById('dock_plus').style.display = 'none';
		document.getElementById('dock_img').src = "/SeamAmiciDelGas/img/up.png";	
		current_dockplus = 0;
	}
}

function calendar()
{
	
	if (current_calendarlevel == 0) {
		document.getElementById('idi_1547474924').style.display = '';
		document.getElementById('plus_img_calendar').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		current_calendarlevel = 1;
	} else {
		document.getElementById('idi_1547474924').style.display = 'none';
		document.getElementById('plus_img_calendar').src = "/SeamAmiciDelGas/img/minus_off.jpg";	
		current_calendarlevel = 0;
	}
}

function show_calendar(cond)
{
	
	if (current_calendarlevel == 1) {
		
		if(cond){
			document.getElementById('plus_img_calendar').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		} else {
			document.getElementById('plus_img_calendar').src = "/SeamAmiciDelGas/img/plus.jpg";
		}
	} else {
		if(cond){
			document.getElementById('plus_img_calendar').src = "/SeamAmiciDelGas/img/minus_off.jpg";
		} else {		
			document.getElementById('plus_img_calendar').src = "/SeamAmiciDelGas/img/minus.jpg";
		}
	}
}
function groups()
{
	
	if (current_groupslevel == 0) {
		document.getElementById('1920ffgghh').style.display = '';
		document.getElementById('plus_img_groups').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		current_groupslevel = 1;
	} else {
		document.getElementById('1920ffgghh').style.display = 'none';
		document.getElementById('plus_img_groups').src = "/SeamAmiciDelGas/img/minus_off.jpg";	
		current_groupslevel = 0;
	}
}

function show_groups(cond)
{
	
	if (current_groupslevel == 1) {
		
		if(cond){
			document.getElementById('plus_img_groups').src = "/SeamAmiciDelGas/img/plus_off.jpg";
		} else {
			document.getElementById('plus_img_groups').src = "/SeamAmiciDelGas/img/plus.jpg";
		}
	} else {
		if(cond){
			document.getElementById('plus_img_groups').src = "/SeamAmiciDelGas/img/minus_off.jpg";
		} else {		
			document.getElementById('plus_img_groups').src = "/SeamAmiciDelGas/img/minus.jpg";
		}
	}
}