var geocoder = null;
var point;
var gasIcon, contadiniIcon;
var markers = new Array();
var markers_contadini = new Array();
var map;

function Indirizzo(via,comune,provincia){
	this.via = via;
	this.comune = comune;
	this.provincia = provincia;
	
	this.getAddress = function (){
		return via+", "+ comune +" (" + provincia +")";	
	}
	this.getComune = function(){
		return comune +" (" + provincia +")";
	}
}
function load() {
	if(geocoder==null){
     if (GBrowserIsCompatible()) {

       	map = new GMap2(document.getElementById("map1"));
		map.enableDoubleClickZoom();
		map.enableContinuousZoom();
       	map.setCenter(new GLatLng(41.134193, 14.775925), 13);
		map.addControl(new GSmallMapControl());
       	map.addControl(new GMapTypeControl());
		gasIcon = new GIcon();
	     gasIcon.image= "/SeamAmiciDelGas/img/gmap_consegna.png";
	     gasIcon.shadow="/SeamAmiciDelGas/img/gmap_fiamma_ombra.png";
	     gasIcon.iconSize= new GSize(25 , 32);
	    gasIcon.iconAnchor = new GPoint(5, 34);
	    gasIcon.infoWindowAnchor = new GPoint(5, 2);
	    gasIcon.infoShadowAnchor = new GPoint(14, 25);
	    
	    contadiniIcon = new GIcon();
	    contadiniIcon.image= "/SeamAmiciDelGas/img/gmap_fiamma.png";
	    contadiniIcon.shadow="/SeamAmiciDelGas/img/gmap_fiamma_ombra.png";
	    contadiniIcon.iconSize= new GSize(18 , 28);
	    contadiniIcon.iconAnchor = new GPoint(5, 34);
	    contadiniIcon.infoWindowAnchor = new GPoint(5, 2);
	    contadiniIcon.infoShadowAnchor = new GPoint(14, 25);
		
		// Set up our GMarkerOptions object
		markerOptions = { 
	//	icon:blueIcon ,
			icon:gasIcon ,
			clickable:true
		};

       	var bounds = map.getBounds();
       	var southWest = bounds.getSouthWest();
       	var northEast = bounds.getNorthEast();
       	var lngSpan = northEast.lng() - southWest.lng();
       	var latSpan = northEast.lat() - southWest.lat();
		var addressPoint=null;
	
		geocoder = new GClientGeocoder();
   		}
   	}
   }
   
   function porcata(get_indirizzo, get_comune, get_provincia, get_id){
   		
		var indirizzo = new Indirizzo(get_indirizzo, get_comune, get_provincia);
		geocoder.getLocations(indirizzo.getAddress(), getCallBackFor(indirizzo,0,get_id));				
   }
   
   function pushContadini(get_indirizzo, get_comune, get_provincia, get_id){
   		
		var indirizzo = new Indirizzo(get_indirizzo, get_comune, get_provincia);
		geocoder.getLocations(indirizzo.getAddress(), getCallBackForContadini(indirizzo,0,get_id));				
   }
   
   function deletePoint(id){
		map.removeOverlay(markers[id]);
   }
   
   function getCallBackFor(indirizzo,codice, idmark){
	return (function(data, response){
		if(data.Status.code == "200"){
			point = data.Placemark[0].Point.coordinates;
			
			var point2 = new GLatLng(point[1],point[0]);
			var mark = new GMarker(point2, {title: indirizzo.getAddress(), icon:gasIcon});
			map.addOverlay(mark);
			markers[idmark] = mark;
			
			map.panTo(mark.getPoint());
			mark.bindInfoWindowHtml('<div align=\"center\"><b>Punto di Consegna</b></div><p><div align=\"center\" class=\"colore_1\">'+indirizzo.getAddress()+'</div></p>');
					
			GEvent.addListener(mark, "infowindowopen", function() {
				map.setZoom(16);
				map.panTo(mark.getPoint());
				map.panDirection(0, 1);
			});
			GEvent.addListener(mark, "infowindowclose", function() {map.setZoom(13); map.panTo(mark.getPoint());});
		}
		else if(data.Status.code =="602" && codice == 0){
			new GClientGeocoder().getLocations(indirizzo.getComune(), getCallBackFor(indirizzo,1,idmark));
		}
	});
}
	function getCallBackForContadini(indirizzo,codice, idmark){
	return (function(data, response){
		if(data.Status.code == "200"){
			point = data.Placemark[0].Point.coordinates;
			
			var point2 = new GLatLng(point[1],point[0]);
			var mark = new GMarker(point2, {title: indirizzo.getAddress(), icon:contadiniIcon});
			map.addOverlay(mark);
			markers_contadini[idmark] = mark;
			
			map.panTo(mark.getPoint());
			mark.bindInfoWindowHtml('<div align=\"center\"><b>Punto di Consegna</b></div><p><div align=\"center\" class=\"colore_1\">'+indirizzo.getAddress()+'</div></p>');
					
			GEvent.addListener(mark, "infowindowopen", function() {
				map.setZoom(16);
				map.panTo(mark.getPoint());
				map.panDirection(0, 1);
			});
			GEvent.addListener(mark, "infowindowclose", function() {map.setZoom(13); map.panTo(mark.getPoint());});
		}
		else if(data.Status.code =="602" && codice == 0){
			new GClientGeocoder().getLocations(indirizzo.getComune(), getCallBackFor(indirizzo,1,idmark));
		}
	});
}	
		