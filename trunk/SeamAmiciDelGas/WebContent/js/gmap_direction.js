var gdir;
var addressMarker;

function initialize() {
       
   gdir = new GDirections(map, document.getElementById("directions"));
   GEvent.addListener(gdir, "load", onGDirectionsLoad);
   GEvent.addListener(gdir, "error", handleErrors);
	
   setDirections("viale mellusi, Benevento (Benevento)","via delle puglie, Benevento (Benevento)", "via napoli, Benevento (Benevento)", "it_IT");
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