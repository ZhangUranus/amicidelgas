var geocoder = null;
var point;
var gasIcon, contadiniIcon;
var markers_place = new Array();
var markers_contadini = new Array();
var partenza = "via traiano, Benevento (Benevento)";
var locale = "it_IT";
var to_place = new Array();
var from_place = new Array();
var map;
var gdir;
var addressMarker;
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

function puntoItinerario(mark, titolo, id, indirizzo){
	this.mark = mark;
	this.titolo = titolo;
	this.id = id;
	this.indirizzo = indirizzo;
	
	this.getMark = function (){
		return mark;
	}
	this.getNomeAzienda = function (){
		return nomeAzienda;
	}
	this.getId = function (){
		return id;
	}
	this.getIndirizzo = function (){
		return indirizzo; 
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
		
		//G_START_ICON.image = "./doesntexist.png";
		//G_END_ICON.image = "./doesntexisteither.png"; 
		
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
			
			var place = new puntoItinerario(mark, 'Punto di consegna', idmark, indirizzo);
			
			markers_place[idmark] = place;
			
			map.panTo(mark.getPoint());
					
			GEvent.addListener(mark, "click", function() {
				if(prima_cybercontadino==0){
					alert("Seleziona prima uno o più cybercontadini, i punti di consegna vanno selezionati per ultimi");
				} else {
					
					if(to_place[idmark]==null){
						to_place[idmark] = place;
						count_punti_di_consegna++;
						
						solo_punti_di_consegna++;
						setDirections();
											
					} else {
						to_place[idmark] = null;
						count_punti_di_consegna--;
						
						setDirections();
						if(count_punti_di_consegna==0){
							solo_punti_di_consegna=0; //non si può aggiungere un punto di consegna
							alert("Nessun punto di consenga presente nell'itinerario.");
						}
						
					}
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
			
			var contadino = new puntoItinerario(mark, nome_azienda, idmark, indirizzo);
			
			markers_contadini[idmark] = contadino;
			
			map.panTo(mark.getPoint());
					
			GEvent.addListener(mark, "click", function() {
				if(solo_punti_di_consegna==0){
					if(from_place[idmark]==null){
						from_place[idmark] = contadino;
						count_contadini++;
						if(count_contadini==1){
							prima_cybercontadino=1; //consenta di aggiungere un punto di consegna
							setDirections();
							//alert("Primo contadino aggiunto, selezionare un altro contadino o un punto di consegna per creare un itinerario.");
						} else if(count_contadini <= 3){
							setDirections();
						} else {
							alert("Non puoi aggiungere altri cybercontadini allì'itinerario.");
						}					
					} else {
						from_place[idmark] = null;
						count_contadini--;
						setDirections();
						if(count_contadini==0){
							prima_cybercontadino=0; //non si può aggiungere un punto di consegna
							alert("Contadino rimosso dall'itinerario, nessun contadino presente nell'itinerario.");
						}
						
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
   GEvent.addListener(gdir, "addoverlay", onGDirectionsLoad);
   GEvent.addListener(gdir, "error", handleErrors);
}

function setDirections() {
	//il cuore di tutto.... che commento a ca***
	
	var query = "from: " + partenza;
	
	if(from_place.length > 0){
		for(z=0; z < from_place.length ;z++){
			if(from_place[z]!=null){
				query = query + " to: "+from_place[z].getIndirizzo().getAddress();
			}
		}
		for(t=0; t < to_place.length ;t++){
			if(to_place[t]!=null){
				query = query + " to: "+to_place[t].getIndirizzo().getAddress();
			}
		}
		gdir.load(query,{ "locale": locale , "preserveViewport": false });
	}
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
	var numero = gdir.getNumRoutes();
	for(i=1; i<= numero; i++){
		var mark = gdir.getMarker(i);
		map.removeOverlay(mark);
	}
}