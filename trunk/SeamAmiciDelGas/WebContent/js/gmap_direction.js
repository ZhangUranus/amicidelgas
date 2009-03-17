var geocoder = null;
var point;
var gasIcon, contadiniIcon;
var markers = new Array();
var markers_contadini = new Array();
var to_place = new Array();
var from_place = new Array();
var map;
var gdir;
var addressMarker;
var toplaces = new Array();
var prima_cybercontadino = 0;
var solo_punti_di_consegna = 0;
// i punti di consegna ed i cybercontadini massimi sono 3
var count_punti_di_consegna = 0;
var count_contadini = 0;

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
   
   function pushContadini(nome_azienda, get_indirizzo, get_comune, get_provincia, get_id){
   		
		var indirizzo = new Indirizzo(get_indirizzo, get_comune, get_provincia);
		geocoder.getLocations(indirizzo.getAddress(), getCallBackForContadini(nome_azienda,indirizzo,0,get_id));				
   }
   
   function deletePoint(id){
		map.removeOverlay(markers[id]);
   }
   
   function getCallBackFor(indirizzo,codice, idmark){
	return (function(data, response){
		if(data.Status.code == "200"){
			point = data.Placemark[0].Point.coordinates;
			
			var point2 = new GLatLng(point[1],point[0]);
			var mark = new GMarker(point2, {title: "Punto di consegna: "+indirizzo.getAddress(), icon:gasIcon});
			map.addOverlay(mark);
			markers[idmark] = mark;
			
			map.panTo(mark.getPoint());
					
			GEvent.addListener(mark, "click", function() {
				if(prima_cybercontadino==0){
					alert("Seleziona prima uno o più cybercontadini, i punti di consegna vanno selezionati per ultimi");
				} else {
					solo_punti_di_consegna++;
					alert("count_punti");
				}
			});
		}
		else if(data.Status.code =="602" && codice == 0){
			new GClientGeocoder().getLocations(indirizzo.getComune(), getCallBackFor(indirizzo,1,idmark));
		}
	});
}
	function getCallBackForContadini(nome_azienda,indirizzo,codice, idmark){
	return (function(data, response){
		if(data.Status.code == "200"){
			point = data.Placemark[0].Point.coordinates;
			
			var point2 = new GLatLng(point[1],point[0]);
			var mark = new GMarker(point2, {title: nome_azienda+", "+indirizzo.getAddress(), icon:contadiniIcon});
			map.addOverlay(mark);
			markers_contadini[idmark] = mark;
			
			map.panTo(mark.getPoint());
					
			GEvent.addListener(mark, "click", function() {
				if(solo_punti_di_consegna==0){
					if(true){
					
					}
					count_contadini++;
					if(count_contadini==1){
						prima_cybercontadino++;
						alert("count 1");
					}
				} else {
					alert("Per poter aggiungere un cybercontadino deselezionare tutti i punti di consegna");
				}
				
			});
		}
		else if(data.Status.code =="602" && codice == 0){
			new GClientGeocoder().getLocations(indirizzo.getComune(), getCallBackForContadini(nome_azienda,indirizzo,1,idmark));
		}
	});
}	

function initialize() {
       
   gdir = new GDirections(map, document.getElementById("directions"));
   GEvent.addListener(gdir, "load", onGDirectionsLoad);
   GEvent.addListener(gdir, "error", handleErrors);
	
   //setDirections("via mellusi, Benevento (Benevento)","via delle puglie, Benevento (Benevento)", "via napoli, Benevento (Benevento)", "it_IT");
}

function setDirections(fromAddress, toAddress, toAddress2, locale) {
  gdir.load("from: " + fromAddress + " to: " + toAddress + " to: " + toAddress2,
            { "locale": locale });
}

function handleErrors(){
 if (gdir.getStatus().code == G_GEO_UNKNOWN_ADDRESS)
   alert("No corresponding geographic location could be found for one of the specified addresses. This may be due to the fact that the address is relatively new, or it may be incorrect.\nError code: " + gdir.getStatus().code);
 else if (gdir.getStatus().code == G_GEO_SERVER_ERROR)
   alert("A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known.\n Error code: " + gdir.getStatus().code);
 
 else if (gdir.getStatus().code == G_GEO_MISSING_QUERY)
   alert("The HTTP q parameter was either missing or had no value. For geocoder requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input.\n Error code: " + gdir.getStatus().code);

 else if (gdir.getStatus().code == G_GEO_BAD_KEY)
   alert("The given key is either invalid or does not match the domain for which it was given. \n Error code: " + gdir.getStatus().code);

 else if (gdir.getStatus().code == G_GEO_BAD_REQUEST)
   alert("A directions request could not be successfully parsed.\n Error code: " + gdir.getStatus().code);
  
 else alert("An unknown error occurred.");
}

function onGDirectionsLoad(){

}